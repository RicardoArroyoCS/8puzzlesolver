package search_algorithms;

import puzzle.PuzzleSolution;
import puzzle.PuzzleState;

/***
 * This class is an Implementation of the Interface ISearchAlgorithm. It is used to call on
 * implemented interface methods for any shared Search Algorithm
 * @author Ricardo Arroyo
 *
 */
public class SearchAlgorithmImpl implements ISearchAlgorithm{

	@Override
	public void findGoal(PuzzleState root) {
		// UnimplementedW
	}

	@Override
	public void findParent(PuzzleState node) {
		// Unimplemented
	}

	@Override
	public String getOptimalStep(PuzzleSolution optimal) {
		String toReturn = "";
		
		if(optimal.getNumberOfSteps() > -1){
			toReturn += "\n" + "====================================\nThe Optimal Step:"
				+ "There are " + optimal.getNumberOfSteps() + " number of steps in the optimal Solution\n"
				+ optimal.getAllSteps();
		}
		else
			toReturn = "The Puzzle Solver did not find a solution.";
		
		return toReturn;
	}

	@Override
	public String getDisplayResult() {
		// Unimplemented
		return null;
	}

}
