package gui_panels;

import gui_menu.ButtonsFactory;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import puzzle.Puzzle;

/***
 * A IGUIPanel class used for the Pattern View of the JFrame. This Panel displays the intended Puzzle
 * Pattern to be used. Works in conjunction with PuzzlePatternPanel. Provides no other function.
 * @author Ricardo Arroyo
 *
 */
public class PuzzleViewPanel implements IGUIPanel {
	private static final int ROW = 3;
	private static final int COLUMN = 3;
	
	private TitledBorder title;		
	ArrayList<JButton> puzzlePieces;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel puzzleView;
	
	private String puzzleViewPattern = Puzzle.easy;
	
	public PuzzleViewPanel(int fillIn, int gridxIn, int gridyIn, int anchorIn){
		gbc.fill = fillIn;
		gbc.gridx = gridxIn;
		gbc.gridy = gridyIn;
		gbc.anchor = anchorIn;
		
		puzzleView = new JPanel(new GridLayout(ROW, COLUMN));
	}
	
	/***
	 * Returns the GridbagConstraints the Panel is utilizes. Used as a second parameter for adding *this 
	 * panel to the JFrame
	 * @return GridBagConstraints Object
	 */	
	public GridBagConstraints getGridBagConstraints(){
		return gbc;
	}
	
	/***
	 * Returns the pattern currently displayed on the Viewer Panel
	 * @return
	 */
	public String getPuzzleViewPattern(){
		return puzzleViewPattern;
	}
	
	/***
	 * Used to instantialize the buttons for this panel
	 */
	private void setButtons(){
		puzzlePieces = ButtonsFactory.getPuzzleViewButtons();
		
		for(JButton button : puzzlePieces){
			puzzleView.add(button);
		}
	}
	
	/***
	 * Sets the PuzzleView to a given pattern. 
	 * @param pattern A numeric string (0-9) exactly nine unique characters long.
	 */
	public void setPuzzleView(String pattern){
		String[] patternArray = pattern.split("");
		puzzleViewPattern = "";
		
		int index = 0;
		for(JButton button : puzzlePieces){
			if(!patternArray[index].equals("0"))
				button.setText(patternArray[index]);
			else
				button.setText("");
			
			puzzleViewPattern += patternArray[index];
			index++;
		}
	}
	
	public JPanel generatePanel(){
		// Create Titled Border
		title = new TitledBorder(createTitledBorder("Puzzle View"));
		puzzleView.setBorder(title);
		
		setButtons();

		return puzzleView;
	}
	

	
	/***
	 * Create a TitledBorder given a Name
	 * @param name The name of the titled displayed on the border
	 * @return a TitledBorder object 
	 */
	private TitledBorder createTitledBorder(String name){
		return IGUIPanelImpl.createTitledBorder(name);
	}
	
	
}
