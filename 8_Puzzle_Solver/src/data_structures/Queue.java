package data_structures;
import java.util.LinkedList;

import puzzle.PuzzleState;

public class Queue {
	private LinkedList<PuzzleState> list;
	
	public Queue(){
		list = new LinkedList<PuzzleState>();
	}
	
	public boolean isEmpty(){
		return(list.size() == 0);
	}
	
	public void enqueue(PuzzleState item){
		list.add(item);
	}
	
	public PuzzleState dequeue(){
		return (PuzzleState) list.remove();
	}
	
	public PuzzleState peek(){
		return (PuzzleState) list.get(1);
	}
}