package search_algorithms;
import java.util.ArrayList;

import puzzle.PuzzleState;
import puzzle.PuzzleSolution;
import data_structures.Stack;
import data_structures.PuzzlePieceQueue;

/***
 * A Search Algorithm with a Breadth First Search Implementation 
 * @author Ricardo Arroyo
 *
 */
public class BreadthFirst implements ISearchAlgorithm{
	// Power Type
	public PuzzlePieceQueue queue = new PuzzlePieceQueue();
	public Stack sStack = new Stack();
	public PuzzleSolution optimal = new PuzzleSolution();
	public int maxInQueue = 0;
	
	// Delegate
	private SearchAlgorithmImpl delegate = new SearchAlgorithmImpl();	
	private String displayResults = "";
	
	// Package visible constructor. Must call on Search Factory to create an Object
	BreadthFirst(){}
	
	/***
	 * Used to "Search". Iterates through the Tree and finds the optimal solution
	 */
	public void findGoal(PuzzleState root){
		long start_time = System.nanoTime();
	
		// Check for Goal on root
		if (root.isGoal())
			findParent(root);
		
		// Enqueue children of root
		for(PuzzleState n : root.getChildren()){
			queue.enqueue(n);
		}
		
		// Iterate queue until empty
		while(!queue.isEmpty()){
			PuzzleState currNode = queue.dequeue();
			
			// Check if the current node has a goal state
			if(currNode.isGoal())
				findParent(currNode);
			
			// Enqueue the Current Node's children
			for(PuzzleState s : currNode.getChildren()){
				queue.enqueue(s);
			}
			// For finding the max items in queue 	
			int tempSize = queue.size();
			if (maxInQueue < tempSize)
				maxInQueue = tempSize;
		}
		long end_time = System.nanoTime();
		System.out.println("Breadth First Search took " + (end_time - start_time) + " miliseconds");
		displayResults += "\n" + "Breadth First Search took " + (end_time - start_time) + " miliseconds\n";
		displayResults += getOptimalStep(optimal);
	}
	
	/***
	 * Find the parent of the goal state
	 */
	public void findParent(PuzzleState node){
		ArrayList<String> answer = new ArrayList<String>();
		Stack stack = new Stack();
		int pathcost = 0;
		
		// Push nodes in stack until the parent has been reached
		stack.push(node);
		while(node.getParent() != null){
			node = node.getParent();
			pathcost ++;
			stack.push(node);
		}
			
		// Pop the nodes in the correct order to print the correct directions
		while(!stack.isEmpty()){
			node = stack.pop();
			if(node.getAction() != null){
				displayResults += "State:\n" + node.getFormattedStateString() + "\n";
				answer.add(node.getAction());
			}
		}
		// Create Solution object to later find the best solution
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
