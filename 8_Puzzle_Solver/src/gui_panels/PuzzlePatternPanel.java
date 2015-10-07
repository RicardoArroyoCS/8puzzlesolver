package gui_panels;

import gui_menu.ButtonsFactory;
import gui_menu.PuzzleMenu;
import gui_menu.PuzzleMenu.PuzzlePatternType;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import puzzle.Puzzle;

/***
 * A IGUIPanel class used for the Pattern Panel of the JFrame. Generally displays Radio Buttons
 * for the type of puzzle pattern used in the Puzzle State (EASY, MEDIUM, HARD, CUSTOM).
 * @author Ricardo Arroyo
 *
 */
public class PuzzlePatternPanel implements IGUIPanel, ItemListener, KeyListener {
	// Constant Members
	private static final int ROW = 0;
	private static final int COLUMN = 1;
	
	private TitledBorder title;	
	private ArrayList<JRadioButton> puzzlePatterns;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel puzzleSelectionPnl;
	private JTextField customSearch;
	private PuzzleViewPanel puzzleViewpnl;
	
	private PuzzlePatternType puzzlePatternSelected = PuzzleMenu.PuzzlePatternType.EASY;
			
	public PuzzlePatternPanel(int fillIn, int gridxIn, int gridyIn, int anchorIn){
		gbc.fill = fillIn;
		gbc.gridx = gridxIn;
		gbc.gridy = gridyIn;
		gbc.anchor = anchorIn;
		
		puzzleSelectionPnl = new JPanel(new GridLayout(ROW, COLUMN));
	}
	
	/***
	 * Sets the PuzzleViewPanel attribute. This must be a direct link to the instantiated PuzzleViewPanel. 
	 * @param panel Panel of the current instantiated PuzzleViewPanel on the JFrame
	 */
	public void setPuzzleViewPnl(PuzzleViewPanel panel){
		puzzleViewpnl = panel;
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
	 * Returns the puzzle pattern type being used in the GUI. (i.e. EASY, MEDIUM, HARD, CUSTOM)
	 * @return The PuzzlPattern enum
	 */
	public PuzzlePatternType getPuzzlePatternTypeSelected(){
		return puzzlePatternSelected;
	}
	
	/***
	 * Returns the String of the puzzle pattern intended to be solved (i.e. "134862705")
	 * @return A String representing the puzzle pattern intended to be solved
	 */
	public String getPuzzleViewPattern(){
		if(puzzlePatternSelected == PuzzleMenu.PuzzlePatternType.CUSTOM)
			puzzleViewpnl.setPuzzleView(customSearch.getText());
		
		return puzzleViewpnl.getPuzzleViewPattern();
	}
	
	/***
	 * Returns a boolean value if the current puzzle pattern is valid. A valid puzzle pattern
	 * contains a set of unique integer values 0-8
	 * @return True if the puzzle pattern is valid
	 */
	public boolean isValidPuzzlePattern(){
		// Preset puzzles are automatically valid
		if(puzzlePatternSelected != PuzzleMenu.PuzzlePatternType.CUSTOM)
			return true;
		
		String customPattern = customSearch.getText();
		HashSet<Integer> duplicates = new HashSet<Integer>();
		
		// custom puzzle pattern is under 9 panels
		if(customPattern.length() < 9)
			return false;
		
			try {
				for(String strChar : customPattern.split("")){
					int castedChar = Integer.parseInt(strChar);
					
					// Duplicate number
					if(duplicates.contains(castedChar))
						return false;
					duplicates.add(castedChar);
				}
			} catch (NumberFormatException e) {
				// Contains a non-int value
				return false;
			}
		
		
		return true;
	}
	
	public JPanel generatePanel(){
		// Create Titled Border
		title = new TitledBorder(createTitledBorder("Select an 8-Puzzle Pattern"));
		puzzleSelectionPnl.setBorder(title);
		
		setButtons();
		
		customSearch = new JTextField("Custom Puzzle (i.e 134862705)", 0);
		puzzleSelectionPnl.add(customSearch);
		customSearch.addKeyListener(this);

		return puzzleSelectionPnl;
	}
	
	/***
	 * Set the Buttons (i.e. EASY, MEDIUM, HARD, CUSTOM) to the panel.
	 */
	private void setButtons(){
		puzzlePatterns = ButtonsFactory.getPuzzlePatternButtons();
		ButtonGroup puzzlePatternsGroup = new ButtonGroup();
		
		for(JRadioButton button : puzzlePatterns){
			puzzlePatternsGroup.add(button);
			puzzleSelectionPnl.add(button);
			button.addItemListener(this);
		}
	}
	
	/***
	 * Create a TitledBorder given a Name
	 * @param name The name of the titled displayed on the border
	 * @return a TitledBorder object 
	 */
	private TitledBorder createTitledBorder(String name){
		return IGUIPanelImpl.createTitledBorder(name);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if(event.getStateChange() == ItemEvent.DESELECTED)
			return;
		
		if(event.getItem() == puzzlePatterns.get(0)){
			System.out.println("Easy Puzzle");
			puzzlePatternSelected = PuzzleMenu.PuzzlePatternType.EASY;
		}
		else if(event.getItem() == puzzlePatterns.get(1)){
			System.out.println("Medium Puzzle");	
			puzzlePatternSelected = PuzzleMenu.PuzzlePatternType.MEDIUM;
		}
		else if(event.getItem() == puzzlePatterns.get(2)){
			System.out.println("Hard Puzzle");
			puzzlePatternSelected = PuzzleMenu.PuzzlePatternType.HARD;
		}
		else if(event.getItem() == puzzlePatterns.get(3)){
			System.out.println("Custom Puzzle");
			puzzlePatternSelected = PuzzleMenu.PuzzlePatternType.CUSTOM;
		}
		
		switch(puzzlePatternSelected){
		case CUSTOM:
			puzzleViewpnl.setPuzzleView("         ");
			break;
		case EASY:
			puzzleViewpnl.setPuzzleView(Puzzle.easy);
			break;
		case HARD:
			puzzleViewpnl.setPuzzleView(Puzzle.hard);
			break;
		case MEDIUM:
			puzzleViewpnl.setPuzzleView(Puzzle.medium);
			break;
		default:
			puzzleViewpnl.setPuzzleView("INCORRECT!");
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(puzzlePatternSelected != PuzzleMenu.PuzzlePatternType.CUSTOM){
			System.out.println("Custom Puzzle");
			puzzlePatternSelected = PuzzleMenu.PuzzlePatternType.CUSTOM;
			puzzlePatterns.get(3).setSelected(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}
}
