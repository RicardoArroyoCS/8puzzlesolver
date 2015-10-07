package puzzle;
import java.util.ArrayList;
import java.util.Comparator;

/***
 * A Class representing a Puzzle State. This is meta information about a puzzle consisting of the state,
 * possible different states, and traversal depth.
 * @author Ricardo Arroyo
 *
 */
public class PuzzleState implements Comparator<PuzzleState>, Comparable<PuzzleState>
{
	private String state; // Puzzle State
	private PuzzleState parent; // Puzzle Parent
	private int depth = 0; 
	private int path_cost; // Current path cost
	private String action; // Action i.e. UP, DOWN, LEFT, RIGHT
	private String empty_tile; // Location of empty tile
	private ArrayList<PuzzleState> children = new ArrayList<PuzzleState>();
	private int numOfChildren = 0; 
	public int h1val = 9; // h(n)1 value
	private int fn = 0;
	private int gn;
	private boolean isAStar = false; // If A* is being used. Different Comparable standards than GBFS
	
	private String[][] visual_state_2d;	
	private final String goal   = "123804765"; // Goal State string
			

	/***
	 *  Constructor
	 * @param state A String State
	 * @param parent The Parent of the State. If null, then PuzzleState is Root
	 * @param depth The depth of the Node.
	 * @param path_cost The Path Cost of the node. Used for Heuristics. Only for Greedy searches.
	 * @param action The action the puzzle state took to reach the current state (i.e UP, DOWN, LEFT, RIGHT)
	 */
	public PuzzleState (String state, PuzzleState parent, int depth, int path_cost, String action){
		this.state = state;
		this.parent = parent;
		this.depth = depth;
		this.path_cost = path_cost;
		this.action = action;
		create2D(this.state);
		findh1(this.state);
		gn = depth;
		findFn();
	}
	
	/***
	 *  Constructor
	 * @param state A String State
	 * @param parent The Parent of the State. If null, then PuzzleState is Root
	 * @param depth The depth of the Node.
	 * @param path_cost The Path Cost of the node. Used for Heuristics. Only for Greedy searches.
	 * @param action The action the puzzle state took to reach the current state (i.e UP, DOWN, LEFT, RIGHT)
	 * @param isA True if the search is A*, False if any other search.
	 */
	public PuzzleState (String state, PuzzleState parent, int depth, int path_cost, String action, boolean isA){
		this.state = state;
		this.parent = parent;
		this.depth = depth;
		this.path_cost = path_cost;
		this.action = action;
		create2D(this.state);
		findh1(this.state);
		gn = depth;
		findFn();
		isAStar = isA;
	}
	
	/***
	 *  Creates a 2d Array of the current Node state
	 * @param state
	 */
	public void create2D(String state){
		visual_state_2d = new String[3][3];
		String temp = state;
		for(int i=0; i<=2; i++){
			for(int j=0; j<=2; j++){
				visual_state_2d[i][j] = temp.substring(0, 1);
				if(visual_state_2d[i][j].equals("0") )
					empty_tile = Integer.toString(i) + Integer.toString(j); 
				temp = temp.substring(1);
			}
		}
		System.out.println();
	}
	
	/***
	 *  Print the state of the object's 2D array
	 */
	public void printState(){
	
		for(String[] s :visual_state_2d ){
			for(int i = 0; i< s.length; i++){
				System.out.print(s[i] + " " );
			}
			System.out.println();
		}
	}
	
	/***
	 *  Add children to the current object
	 * @param children
	 */
	public void addChildren(ArrayList<PuzzleState> children){
		for(PuzzleState s : children){
			this.children.add(s);
		}
		numOfChildren = children.size();
	}

	
	/***
	 *  Returns True if the current state has reached the goal
	 * @return
	 */
	public boolean isGoal(){
		if(state.equals(this.goal))
			return true;
		else
			return false;
	}
	
	/**
	 *  Finds the number of matches to the goal state  Goal: "123804765"
	 * @param state
	 */
	public void findh1(String state){
		String tempGoal = goal;

		for(int i =0; i<state.length();i++){
			if( state.substring(i, i+1).equals(tempGoal.substring(i, i+1)) ) {
					h1val--;
			}
		}
	}
	
	/***
	 * Assign F(n) state given a g(n) and heuristic value
	 */
	public void findFn(){
		this.fn = gn + h1val;
	}
	
	
	// Getters
	
	public int getFn(){
		return fn;
	}
	
	public int getH1Val(){
		return h1val;
	}
	
	public int getGn(){
		return gn;
	}
	
	/***
	 *  Return the ArrayList containing the Children
	 * @return
	 */
	public ArrayList<PuzzleState> getChildren(){
		return children;
	}
	
	/***
	 *  Return number of children the Node has
	 * @return
	 */
	public int getNumOfChildren(){
		return numOfChildren;
	}
	
	/***
	 *  Return 2d array
	 * @return
	 */
	public String[][] get2DArray(){
		return visual_state_2d;
	}
	
	/***
	 *  Returns the Object's empty tile location
	 * @return
	 */
	public String getEmptyTile(){
		return empty_tile;
	}	
	
	/***
	 * Return the current String state
	 * @return
	 */
	public String getState(){
		return state;
	}
	
	/***
	 *  Return the Node this current node points to
	 * @return
	 */
	public PuzzleState getParent(){
		return parent;
	}
	
	/***
	 *  Return the depth of this Node
	 * @return
	 */
	public int getDepth(){
		return depth;
	}
	
	/***
	 *  Return the total path cost this Node has
	 * @return
	 */
	public int getPathCost(){
		return path_cost;
	}
	
	/***
	 *  Return the action this Node took
	 * @return
	 */
	public String getAction(){
		return action;
	}
	
	/***
	 * Get a formatted String value of the Puzzle State
	 * @return
	 */
	public String getFormattedStateString(){
		String toReturn = "";
		for(String[] s :visual_state_2d ){
			for(int i = 0; i< s.length; i++){
				toReturn += "|  " + s[i] + "  ";
			}
			toReturn += "|";
			toReturn += "\n";
		}
		return toReturn;
	}
	
	public int compare(PuzzleState a, PuzzleState b){
		if(this.isAStar){
			if(a.getFn() < b.getFn())
				return 1;
			else if(a.getFn() > b.getFn())
				return -1;
			return 0; //A*
		}
		
		else
			return b.getH1Val() - a.getH1Val(); //For Greedy
	}

	/***
	 *  Object is less than, equal to, or greater than the specified object. 
	 */
	public int compareTo(PuzzleState other){
		if(this.isAStar){
			if(this.getFn() < other.getFn())
				return 1;
			else if(this.getFn() > other.getFn())
				return -1;
			return 0;  //A*
		}
		
		else
			return other.getH1Val() - this.getH1Val(); //For Greedy 
	}
		
}
