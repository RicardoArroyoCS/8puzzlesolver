package puzzle;
import java.util.ArrayList;

/***
 * A Class used to create an Object that is a Solution to the Puzzle. A Puzzle can have multiple Solutions
 * and the optimal solution should always be chosen as the answer.
 * @author Ricardo Arroyo
 *
 */
public class PuzzleSolution {
	public int numberOfSteps;
	public ArrayList<String> steps = new ArrayList<String>();
	
	/***
	 * Default Constructor
	 */
	public PuzzleSolution(){
		numberOfSteps = -1;
	}
	
	/***
	 * Loaded Constructor
	 * @param numSteps Number of Steps of the Solution
	 * @param arr An ArrayList<String> of States from the root state to the goal state
	 */
	public PuzzleSolution(int numSteps, ArrayList<String> arr){
		numberOfSteps = numSteps;
		steps = arr;
	}
	
	/***
	 * Copy Constructor
	 * @param s PuzzleSolution 
	 */
	public PuzzleSolution(PuzzleSolution s){
		numberOfSteps = s.getNumberOfSteps();
		steps = new ArrayList<String>();
		for(String a : s.getSteps()){
			steps.add(a);
		}
	}
	
	/***
	 * Print the state steps (to the console) 
	 */
	public void printSteps(){
		for(String s:  steps){
			System.out.print(s + " " );
		}
		System.out.println();
	}
	
	/***
	 * Return a formatted String of all the states from root node to goal
	 * @return a formatted String from root node to goal
	 */
	public String getAllSteps(){
		String stepsToReturn = "";
		
		for(String s:  steps){
			stepsToReturn += s + " ";
		}
		stepsToReturn += "\n";
		
		return stepsToReturn;
	}
	
	/***
	 * Return the number of steps it took from the root state to the goal state
	 * @return Number of steps it took to reach the goal state from the root state
	 */
	public int getNumberOfSteps(){
		return numberOfSteps;
	}
	
	/***
	 * Return an ArrayList<String> of States it took from the root state node to the goal state
	 * @return ArrayList<String>s of States
	 */
	public ArrayList<String> getSteps(){
		return steps;
	}

}
