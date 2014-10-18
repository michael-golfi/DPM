package orientation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 *         Provides abstraction layer for orientation algorithm.
 * 
 *         Carl Patenaude Poulin was consulted for suggestions of a valid approach
 *         to this problem.
 */
public class DefaultOrienteer implements Orienteer {

	enum Action {
		ADVANCE, TURNLEFT, TURNRIGHT
	}

	Stack<Action> actions = new Stack<>();
	Field field;
	ArrayList<Position> potentialPositions;
	ArrayList<Position> validPositions;
	private Position startingPosition;

	public DefaultOrienteer(Field field) {
		this.field = field;
		this.potentialPositions = field.getPotentialPositions();
		this.validPositions = field.getPotentialPositions();
	}

	/**
	 * Gets the current position
	 * 
	 * @return current position
	 * @throws IllegalStateException
	 *             if there are more than one possible current location
	 */
	public Position getCurrentPosition() {
		if (potentialPositions.size() != 1) {
			throw new IllegalStateException(
					"Position is either ambiguous or impossible");
		} else {
			return potentialPositions.iterator().next();
		}
	}

	/**
	 * Gets the starting position
	 * 
	 * @return starting position
	 * @throws IllegalStateException
	 *             if there are more than one possible starting location
	 */
	public Position getStartingPosition() {
		if (startingPosition == null) {
			if (potentialPositions.size() != 1) {
				throw new IllegalStateException(
						"Position is either ambiguous or impossible");
			} else {
				// clone current position and work backwards
				Position crt = potentialPositions.iterator().next();
				Position p = new Position(crt.xTile, crt.yTile, crt.direction);

				// u-turn
				p.turnLeft();
				p.turnLeft();

				// backtrack
				while (!actions.isEmpty()) {
					Action a = actions.pop();
					switch (a) {
					case ADVANCE:
						p.advance();
						break;
					case TURNRIGHT:
						p.turnLeft();
						break;
					case TURNLEFT:
						p.turnRight();
						break;
					}
				}

				// u-turn again
				p.turnLeft();
				p.turnLeft();

				startingPosition = p;
			}
		}
		return startingPosition;
	}

	/**
	 * Add in distance measurement to reduce possible ambiguity
	 */
	public void observeLineOfSight(int actualLineOfSight) {
		List<Position> toDelete = new ArrayList<>();

		for (Position p : potentialPositions) {
			int expectedLineOfSight = field.linesOfSight[p.xTile][p.yTile][p.direction
					.ordinal()];
			if (expectedLineOfSight != actualLineOfSight) {
				toDelete.add(p);
			}
		}

		potentialPositions.removeAll(toDelete);

		// Resilience: start over if no possibility is found
		if (potentialPositions.size() == 0) {
			potentialPositions = this.validPositions;
		}
	}

	/**
	 * Check if current and starting position are found
	 * 
	 * @return true if there is more than one possible location
	 */
	public boolean isPositionAmbiguous() {
		return this.potentialPositions.size() != 1;
	}

	/**
	 * Add an advance action to criteria
	 */
	public void advance() {
		actions.push(Action.ADVANCE);

		List<Position> toDelete = new ArrayList<>();

		for (Position p : potentialPositions) {
			p.advance();
			// filter out of bound positions
			boolean valid = false;
			for (Position q : validPositions) {
				if (p.xTile == q.xTile && p.yTile == q.yTile
						&& p.direction == q.direction) {
					valid = true;
				}
			}
			if (!valid) {
				toDelete.add(p);
			}
		}

		potentialPositions.removeAll(toDelete);

		// Resilience: start over if no possibility is found
		if (potentialPositions.size() == 0) {
			potentialPositions = this.validPositions;
		}
	}

	/**
	 * Add a right turn action to criteria
	 */
	public void turnRight() {
		actions.push(Action.TURNRIGHT);
		for (Position p : potentialPositions)
			p.turnRight();
	}

	/**
	 * Add a left turn action to criteria
	 */
	public void turnLeft() {
		actions.push(Action.TURNLEFT);
		for (Position p : potentialPositions)
			p.turnLeft();
	}
}