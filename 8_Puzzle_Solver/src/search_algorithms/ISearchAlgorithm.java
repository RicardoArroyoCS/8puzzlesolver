package search_algorithms;
import puzzle.PuzzleState;
import puzzle.PuzzleSolution;


/***
 * An interface for any class that wishes to implement a Search Algorithm. 
 * @author Ricardo Arroyo
 *
 */
public interface ISearchAlgorithm {
	
	
	/**
	 * Find the Goal State of a given puzzle solution
	 * @param root The starting state of a puzzle solution.
	 */
	void findGoal(PuzzleState root);
	
	/**
	 * Recursively find the Path taken to reach the solution node
	 * @param node
	 */
	void findParent(PuzzleState node);

	/***
	 * Returns a String giving the results of the search to be displayed on the GUI
	 * @return String representing the result of the search
	 */
	String getDisplayResult();
	
	/**
	 * Print the optimal steps to the solution
	 * @param node
	 */
	String getOptimalStep(PuzzleSolution optimal);
}
