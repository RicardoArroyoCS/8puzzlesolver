package data_structures;
import java.util.Comparator;

import puzzle.PuzzleState;

public class Priority_Queue implements Comparator<PuzzleState>  {

	public int compare(PuzzleState a, PuzzleState b){
		return a.getH1Val() - b.getH1Val();
	}
}
