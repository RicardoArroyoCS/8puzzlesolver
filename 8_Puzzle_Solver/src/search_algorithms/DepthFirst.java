package search_algorithms;
import java.util.ArrayList;

import puzzle.PuzzleState;
import puzzle.PuzzleSolution;
import data_structures.Stack;
import data_structures.PuzzlePieceQueue;

/***
 * A Search Algorithm with a Depth First Search Implementation 
 * @author Ricardo Arroyo
 *
 */
public class DepthFirst implements ISearchAlgorithm{
	// Power Type
	public PuzzlePieceQueue queue = new PuzzlePieceQueue();
	public Stack sStack = new Stack();
	public PuzzleSolution optimal = new PuzzleSolution();
	public int maxInQueue = 0;
	
	// Delegate
	SearchAlgorithmImpl delegate = new SearchAlgorithmImpl();	
	private String displayResults = "";
	
	// Package visible constructor. Must call on Search Factory to create an Object
	DepthFirst() {}
	
	/***
	 * Used to "Search". Iterates through the Tree and finds the optimal solution
	 */
	public void findGoal(PuzzleState root){
		long start_time = System.nanoTime();
		
		// Check if parent has goal state
		if (root.isGoal())
			findParent(root);
		
		// Get the parent's children and push them into the stack
		for(PuzzleState n : root.getChildren()){
			sStack.push(n);
		}
		
		// Iterate until stack has no elements
		while(!sStack.isEmpty()){
			PuzzleState currNode = sStack.pop();
			if(currNode.isGoal()) //Check current node for goal state
				findParent(currNode);
			
			// Push current node's children into the stack 
			for(PuzzleState s : currNode.getChildren()){
				sStack.push(s);
			}
			
			// For finding the max items in queue 	
			int tempSize = sStack.size();
			if (maxInQueue < tempSize)
				maxInQueue = tempSize;
		}
		long end_time = System.nanoTime();
		System.out.println("Depth First Search took " + (end_time - start_time) + " seconds");
		displayResults += "\nDepth First Search took " + (end_time - start_time) + " seconds\n";
		displayResults += getOptimalStep(optimal);
	}
	
	/***
	 * Find the parent of the goal state
	 */
	public void findParent(PuzzleState node){
		ArrayList<String> answer = new ArrayList<String>();
		Stack stack = new Stack();
		int pathcost = 0;
		
		// Push GoalState node until hits the root
		stack.push(node);
		while(node.getParent() != null){
			node = node.getParent();
			pathcost ++;
			stack.push(node);
		}
		
		// Pop the stack to get the directions in the correct order
		while(!stack.isEmpty()){
			node = stack.pop();
			if(node.getAction() != null){
				displayResults += "State:\n" + node.getFormattedStateString() + "\n";
				answer.add(node.getAction());
			}
		}
		// Create a solution Object to later compare with other solutions
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
