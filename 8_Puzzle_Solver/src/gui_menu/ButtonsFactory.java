package gui_menu;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JRadioButton;

/**
 * Static ButtonFactory Class used to return JButton Objects for the GUI
 * and Search Algorithm
 * @author Ricardo Arroyo
 *
 */
public class ButtonsFactory{
	private ButtonsFactory(){}
	
	/***
	 * Retrieves the JRadioButtons necessary for the PuzzlePatternPanel 
	 * @return ArrayList of JRadioButtons 
	 */
	public static ArrayList<JRadioButton> getPuzzlePatternButtons(){
		ArrayList<JRadioButton> buttonList = new ArrayList<JRadioButton>();
	
		buttonList.add(new JRadioButton("Easy Problem", true));
		buttonList.add(new JRadioButton("Medium Problem"));
		buttonList.add(new JRadioButton("Hard Problem"));
		buttonList.add(new JRadioButton("Custom Problem"));
		
		return buttonList;
	}
	
	/***
	 * Retrieves the JButtons necessary for the PuzzleViewPanel
	 * @return ArrayList of JButtons
	 */
	public static ArrayList<JButton> getPuzzleViewButtons(){
		ArrayList<JButton> buttonList = new ArrayList<JButton>();
		String label = "";
		String[] easyPuzzle = {"1", "3", "4", "8", "6", "2", "7", "0", "5"};
		
		for(int i = 0;i < 9; i++)
		{
			if(!easyPuzzle[i].equals("0"))
				label = easyPuzzle[i];
			else
				label = "";
			
			buttonList.add(new JButton(label));
		}
		
		return buttonList;
	}
	
	/***
	 * Retrieves the buttons necessary for the PuzzleSearchPanel
	 * @return ArrayList of JButtons
	 */
	public static ArrayList<JButton> getPuzzleSearchButtons(){
		ArrayList<JButton> buttonList = new ArrayList<JButton>();
	
		buttonList.add(new JButton("A* Search"));
		buttonList.add(new JButton("Breadth First Search"));
		buttonList.add(new JButton("Depth First Search"));
		buttonList.add(new JButton("Greedy Breadth First Search"));
		
		return buttonList;
	}

}
