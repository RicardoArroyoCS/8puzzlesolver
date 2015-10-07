package puzzle;
import gui_menu.PuzzleMenu.PuzzleAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;

import search_algorithms.ISearchAlgorithm;
import search_algorithms.SearchFactory;
import data_structures.PuzzlePieceQueue;

public class Puzzle 
{

// Class Member Variables	
	// Strings for difficulty of the starting state
	public static final String easy =   "134862705";
	public static final String medium = "281043765";
	public static final String hard   = "567408321";
	
	public int currentDepth = 0;
	
	// Map that holds the possible movements from a certain state
	public HashMap<String, String[][]> state_patterns; //
	public HashMap<String, String> state_duplicates = new HashMap<String, String>();
	
	public PuzzlePieceQueue queue = new PuzzlePieceQueue();
	
	// Delegate
	private ISearchAlgorithm search;
	private PuzzleState root;
	private boolean isAStar;
	
// End Class Member Variables	
	
	
	public Puzzle(){
		queue = new PuzzlePieceQueue();
		state_duplicates = new HashMap<String, String>();
		currentDepth = 0;
		
		// Set up the HashMap for every possible puzzle state
		createHashMap();
	}
	
	// Creates the HashMap for all the possible movements of a certain empty tile location
	private void createHashMap(){
		// HashMap for Empty tile in the middle (1,1). Would return all possible movements
		state_patterns = new HashMap<String, String[][]>();
		state_patterns.put("11", new String[][]{ {"UP", "01"}, {"RIGHT", "12"}, {"DOWN", "21"}, {"LEFT", "10"} } ); 
		
		state_patterns.put("01", new String[][]{ {"RIGHT", "02"}, {"DOWN", "11"}, {"LEFT", "00"}  });
		state_patterns.put("10", new String[][]{ {"UP", "00"},    {"RIGHT", "11"}, {"DOWN", "20"}  });
		state_patterns.put("12", new String[][]{ {"UP", "02"},    {"DOWN", "22"}, {"LEFT", "11"}  });
		state_patterns.put("21", new String[][]{ {"UP", "11"},    {"RIGHT", "22"}, {"LEFT", "20"}  });
		
		state_patterns.put("00", new String[][]{ {"RIGHT", "01"}, {"DOWN",  "10"}, });
		state_patterns.put("02", new String[][]{ {"LEFT", "01"},  {"DOWN",  "12"}, });
		state_patterns.put("20", new String[][]{ {"UP",   "10"},  {"RIGHT", "21"}, });
		state_patterns.put("22", new String[][]{ {"UP",   "12"},  {"LEFT",  "21"}, });
	}
	
	// Swap a tile space with another location
	private String swap(PuzzleState parent, String newEmpty){
		StringBuilder newState = new StringBuilder();
		String emptyTile = parent.getEmptyTile();
		String[][] parentArray = new String[3][3];
		String[][] temp = parent.get2DArray();
		
		// Copies the parameter's 2d array into a local variety (Deep Copy)
		for(int i=0; i<=2; i++){
			for(int j=0; j<=2; j++){
				parentArray[i][j] = temp[i][j];
			}
		}
		
		// Temp
		String valueAtNewSpace  = parentArray[Integer.parseInt(newEmpty.substring(0, 1))] [Integer.parseInt(newEmpty.substring(1, 2)) ];
		
		// Swap
		parentArray[Integer.parseInt(newEmpty.substring(0, 1))] [Integer.parseInt(newEmpty.substring(1, 2)) ] = "0" ;
		parentArray[Integer.parseInt(emptyTile.substring(0, 1))] [Integer.parseInt(emptyTile.substring(1, 2)) ] = valueAtNewSpace;
		
		for(String[] s :parentArray){
			for(int i = 0; i< s.length; i++){
				newState.append(s[i]);
			}
		}
		
		return newState.toString();
	}
	
	// Returns an ArrayList of children for a given Node
	public ArrayList<PuzzleState> getPossibleMoves(PuzzleState node, boolean isAStarSearch){
		ArrayList<PuzzleState> temp = new ArrayList<PuzzleState>(); 
		String[][] arr = state_patterns.get(node.getEmptyTile());
		
		// Creates list of all the successor nodes found in a given state
		for(String[] i : arr){
			String tempDir = i[0];
			String tempNewState = swap(node, i[1]);
			int tempDepth = node.getDepth() + 1;
			PuzzleState child = new PuzzleState(tempNewState, node, tempDepth, tempDepth, tempDir, isAStarSearch);
			temp.add(child);
		}
		
		node.addChildren(temp);
		return temp;
		
	}
	
	// Creates search instances based on the search chosen by the user
	public String callSearch(PuzzleState r, PuzzleAlgorithm search){
		switch(search){
		case BFS:
			this.search = SearchFactory.createBreadthFirstSearch();
			break;
		case DFS:
			this.search = SearchFactory.createDepthFirstSearch();
			break;
		case GBFS:
			this.search = SearchFactory.createGreedyBFS();
			break;
		case AStar:
			this.search = SearchFactory.createAStartSearch();
			break;
		}
		this.search.findGoal(r);
		return this.search.getDisplayResult();
	}
	
	public void setPuzzleState(String state, PuzzleState parent, int depth, int path_cost, String action, boolean isA){
		isAStar = isA;
		root = new PuzzleState(state, parent, depth, path_cost, action, isA);
		
		state_duplicates.put(root.getState(), "0");
		queue.enqueue(root);
		getPossibleMoves(root, isA); //root now has child attributes
		queue.dequeue();
	}
	
	public String findSolution(PuzzleAlgorithm searchType){
		// For each Child in root, enqueue
		for(PuzzleState n : root.getChildren()){
			if(!state_duplicates.containsKey(n.getState()) ){ //Check for duplicates
				queue.enqueue(n);
				state_duplicates.put(n.getState(), "0");
			}
		}
		
		// Loop until the Queue is empty. Creates a tree of all the possible (unique) states of an 8-Puzzle
		while(!queue.isEmpty()){
			// Dequeue Children from queue and find their successors to add to the end of queue
			PuzzleState currNode = queue.dequeue();
			getPossibleMoves(currNode, isAStar);
			
			for(PuzzleState ns : currNode.getChildren()){
				if(ns.isGoal())
					System.out.println("Found a Goal State");
				if(!state_duplicates.containsKey(ns.getState())){// Check for duplicate states
					queue.enqueue(ns);
					state_duplicates.put(ns.getState(), "0");
				}
			}
		}
		// Call on search Function to find the goal:
		return callSearch(root, searchType);	
	}
	
	
}
