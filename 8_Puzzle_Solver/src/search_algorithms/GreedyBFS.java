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
 * A Search Algorithm with a Greedy Breadth First Search Implementation 
 * @author Ricardo Arroyo
 *
 */
public class GreedyBFS implements ISearchAlgorithm{
	// Power Type
	public PuzzlePieceQueue queue = new PuzzlePieceQueue();
	public Stack sStack = new Stack();
	public PuzzleSolution optimal = new PuzzleSolution();
	public int maxInQueue = 0;
	
	// Delegate
	SearchAlgorithmImpl delegate = new SearchAlgorithmImpl();
	
	private  HashMap<String, String> state_duplicates = new HashMap<String, String>();
	private PriorityQueue<PuzzleState> pq  = new PriorityQueue<PuzzleState>(10, Collections.reverseOrder());
	private String displayResults = "";
	
	// Package visible constructor. Must call on Search Factory to create an Object
	GreedyBFS(){}
	
	/***
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
		
		// Check if Root is goal
		if (root.isGoal())
			findParent(root);
		
		// Add Children to priority queue
		for(PuzzleState n : root.getChildren()){
			pq.offer(n);
		}
		
		// While queue is not empty
		while(!pq.isEmpty()){
			PuzzleState currNode = pq.poll();

			state_duplicates.put(currNode.getState(), "0"); // add node into tree map to avoid duplicates
			
			if(currNode.isGoal()){ //Check for Goal
				findParent(currNode);
				break;
			}
			
			// Check CurrNode's successors 
			for(PuzzleState n : currNode.getChildren()){
				if(!state_duplicates.containsKey(n.getState())){ //Check for state already used (They are not optimal)
					pq.offer(n);
				}
			}
			
			// For finding the max items in queue 	
			int tempSize = pq.size();
			if (maxInQueue < tempSize)
				maxInQueue = tempSize;
		}
		long end_time = System.nanoTime();
		System.out.println("Greedy BFS took " + (end_time - start_time) + " seconds");
		displayResults += "\nGreedy BFS took " + (end_time - start_time) + " seconds\n";
		displayResults += getOptimalStep(optimal);
	}
	
	/***
	 * Find the parent of the goal state
	 */
	public void findParent(PuzzleState node){
		ArrayList<String> answer = new ArrayList<String>();
		Stack stack = new Stack();
		int pathcost = 0;
		
		// Push node into stack (LIFO) to get the ordering	
		stack.push(node);
		while(node.getParent() != null){
			node = node.getParent();
			pathcost ++;
			stack.push(node);
		}
		
		// Pop from the stack to get the directions 	
		while(!stack.isEmpty()){
			node = stack.pop();
			if(node.getAction() != null){
				displayResults += "State:\n" + node.getFormattedStateString() + "\n";
				answer.add(node.getAction());
			}
		}
	 	// Create solution to later be compared if it is optimal 	
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
