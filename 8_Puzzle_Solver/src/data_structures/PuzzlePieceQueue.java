package data_structures;
import java.util.LinkedList;

import puzzle.PuzzleState;

public class PuzzlePieceQueue {
	private LinkedList<PuzzleState> list;
	
	public PuzzlePieceQueue(){
		list = new LinkedList<PuzzleState>();
	}
	
	public boolean isEmpty(){
		return(list.size() == 0);
	}
	public int size(){
		return list.size();
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