package data_structures;
import puzzle.PuzzleState;

public class Stack {
   private int maxSize;
   private PuzzleState[] stackArray;
   private int top;
   
   public Stack() {
      maxSize = 50;
      stackArray = new PuzzleState[maxSize];
      top = -1;
   }
   public int size(){
	   return stackArray.length;
   }
   public void push(PuzzleState j) {
      stackArray[++top] = j;
   }
   public PuzzleState pop() {
      return stackArray[top--];
   }
   public PuzzleState peek() {
      return stackArray[top];
   }
   public boolean isEmpty() {
      return (top == -1);
   }
   public boolean isFull() {
      return (top == maxSize - 1);
   }
}