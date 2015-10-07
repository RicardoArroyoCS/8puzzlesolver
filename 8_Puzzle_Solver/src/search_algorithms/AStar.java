package search_algorithms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

import puzzle.PuzzleState;
import puzzle.PuzzleSolution;
import data_structures.Stack;
import data_structures.PuzzlePieceQueue;

/***
 * A Search Algorithm with an A* Search Implementation 
 * @author Ricardo Arroyo
 *
 */
public class AStar implements ISearchAlgorithm {
	// Power Type
	public PuzzlePieceQueue queue = new PuzzlePieceQueue();
	public Stack sStack = new Stack();
	public PuzzleSolution optimal = new PuzzleSolution();
	public int maxInQueue = 0;
	
	// Delegate
	SearchAlgorithmImpl delegate = new SearchAlgorithmImpl();
	
	private HashMap<String, String> state_duplicates = new HashMap<String, String>();
	private PriorityQueue<PuzzleState> pq  = new PriorityQueue<PuzzleState>(10, Collections.reverseOrder());
	private String displayResults = "";
	
	// Package visible constructor. Must call on Search Factory to create an Object
	AStar(){}
	
	/**
	 * Prints the current priority queue. Mainly for debugging
	 * @param p
	 */
	public void print(PriorityQueue<PuzzleState> p){
		ArrayList<PuzzleState> a = new ArrayList<PuzzleState>();
		System.out.println("Currently in the PQ: ");
		for(int i = 0 ; i < p.size();i++){
			a.add(p.poll());
			System.out.print(a.get(i).getH1Val()+ " ");
		}
		System.out.println();
		
		for(int j = 0; j<a.size();j++){
			pq.offer(a.get(j));
		}
		
	}
	
	/***
	 * Used to "Search". Iterates through the Tree and finds the optimal solution
	 */
	public void findGoal(PuzzleState root){
		long start_time = System.nanoTime();
		if (root.isGoal())
			findParent(root);
		
		for(PuzzleState n : root.getChildren()){
			pq.offer(n);
		}
		
		// While queue is not empty
		while(!pq.isEmpty()){
			PuzzleState currNode = pq.poll();
			
			// Pull from Queue until unique state has been found
			while(state_duplicates.containsKey(currNode.getState())){
				currNode = pq.poll();
			}
			
			state_duplicates.put(currNode.getState(), "0"); // add node into tree map to avoid duplicates
			
			if(currNode.isGoal()){ // Check for Goal
				findParent(currNode);
				System.out.println("Found Goal State " + currNode.getState() + " Depth: " + currNode.getDepth());
				break;
			}
			
			// Check CurrNode's successors 
			for(PuzzleState n : currNode.getChildren()){
				
				if(!state_duplicates.containsKey(n.getState())){ // Check for duplicates
					pq.offer(n);
				}
			}
			
			// For finding the max items in queue 	
			int tempSize = pq.size();
			if (maxInQueue < tempSize)
				maxInQueue = tempSize;
		}
		long end_time = System.nanoTime();
		System.out.println("A* Search took " + (end_time - start_time) + " seconds");
		displayResults += "\nA* Search took " + (end_time - start_time) + " seconds\n";
		displayResults += getOptimalStep(optimal);
	}
	
	/***
	 * Finds parent of the current node
	 */
	public void findParent(PuzzleState node){
		ArrayList<String> answer = new ArrayList<String>();
		Stack stack = new Stack();
		int pathcost = 0;
		
		// Push the nodes parents until it hits the root
		stack.push(node);
		while(node.getParent() != null){
			node = node.getParent();
			pathcost ++;
			stack.push(node);
		}
			
		// Print the directions of the path in the correct order by popping the stack
		while(!stack.isEmpty()){
			node = stack.pop();
			if(node.getAction() != null){
				displayResults += "State:\n" + node.getFormattedStateString() + "\n";
				answer.add(node.getAction());	
			}
		}
		// Create a solution Object to later find optimal solution (Wont need to do this for A*)
		PuzzleSolution s = new PuzzleSolution(pathcost, answer);
		if(optimal.getNumberOfSteps() == -1)
			optimal = new PuzzleSolution(s);
		else if(optimal.getNumberOfSteps() > s.getNumberOfSteps())
			optimal = new PuzzleSolution(s);
		System.out.println("\n\n\n");
	}

	@Override
	public String getOptimalStep(PuzzleSolution optimal) {
		return delegate.getOptimalStep(optimal);
	}

	@Override
	public String getDisplayResult() {
		// TODO Auto-generated method stub
		return displayResults;
	}	
}
