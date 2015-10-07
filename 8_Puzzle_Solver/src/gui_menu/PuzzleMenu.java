package gui_menu;

import gui_panels.PuzzlePatternPanel;
import gui_panels.PuzzleResultsPanel;
import gui_panels.PuzzleSearchesPanel;
import gui_panels.PuzzleViewPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




import puzzle.Puzzle;

/**
 * Static Puzzle Menu Class with Static Methods to allow user to choose their 8-Puzzle Pattern 
 * and Search Algorithm
 * @author Ricardo Arroyo
 *
 */
public class PuzzleMenu implements ActionListener{
	private JFrame contentPane = new JFrame("8-Puzzle Solver");
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public static enum PuzzlePatternType {
		EASY, MEDIUM, HARD, CUSTOM
	}
	
	public static enum PuzzleAlgorithm {
		AStar, BFS, DFS, GBFS
	}
	private PuzzleViewPanel puzzleViewPnl;
	private PuzzlePatternPanel puzzlePatternPnl;
	private PuzzleSearchesPanel puzzleSearchPnl;
	private PuzzleResultsPanel puzzleResultPnl;
	
	public PuzzleMenu(){
		contentPane.setLayout(new GridBagLayout());
		
		// Only set Size if layout is NOT GridBag
		//contentPane.setSize(675, 450);
		contentPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setVisible(true);

	}
	
	/***
	 * Loads the GUI for the Puzzle Menu
	 */
	public void loadPuzzleMenu(){
		// Load Sub-Menus
		loadPuzzleViewMenu();
		loadPuzzleSelectionMenu();
		loadPuzzleResultSection();
		loadPuzzleSearchesnMenu();
		
		createSolveButton();
		
		// Finished loading Sub-Menus
		contentPane.revalidate();
		contentPane.repaint();
		contentPane.pack();
	}
	
	/***
	 * Loads the JPanel for the Puzzle Selection Menu
	 */
	private void loadPuzzleSelectionMenu(){
		puzzlePatternPnl = new PuzzlePatternPanel(GridBagConstraints.HORIZONTAL,
				0, 0, GridBagConstraints.FIRST_LINE_END);

		puzzlePatternPnl.setPuzzleViewPnl(puzzleViewPnl);
		
		JPanel generatedPanel = puzzlePatternPnl.generatePanel();
		GridBagConstraints constraints = puzzlePatternPnl.getGridBagConstraints();
		
		contentPane.add(generatedPanel, constraints);
	}
	
	/***
	 * Loads the JPanel for the Puzzle View Menu
	 */
	private void loadPuzzleViewMenu(){
		puzzleViewPnl = new PuzzleViewPanel(GridBagConstraints.HORIZONTAL,
				0, 1, GridBagConstraints.CENTER);

		JPanel generatedPanel = puzzleViewPnl.generatePanel();
		GridBagConstraints constraints = puzzleViewPnl.getGridBagConstraints();
		
		contentPane.add(generatedPanel, constraints);
	}
	
	/***
	 * Loads the JPanel for the Puzzle Search Menu
	 */
	private void loadPuzzleSearchesnMenu(){		
		puzzleSearchPnl = new PuzzleSearchesPanel(GridBagConstraints.HORIZONTAL,
				0, 2, GridBagConstraints.CENTER);

		JPanel generatedPanel = puzzleSearchPnl.generatePanel();
		GridBagConstraints constraints = puzzleSearchPnl.getGridBagConstraints();
		
		contentPane.add(generatedPanel, constraints);
	}
	
	/***
	 * Loads the JPanel for the Result Instruction
	 */
	private void loadPuzzleResultSection(){		
		puzzleResultPnl = new PuzzleResultsPanel(GridBagConstraints.HORIZONTAL,
				2, 0, GridBagConstraints.FIRST_LINE_END);

		JPanel generatedPanel = puzzleResultPnl.generatePanel();
		
		puzzleResultPnl.setGridHeight(3);
		puzzleResultPnl.setWeighty(1.0);
		puzzleResultPnl.setInsets(new Insets(0, 10, 0, 0));
		GridBagConstraints constraints = puzzleResultPnl.getGridBagConstraints();
		
		contentPane.add(generatedPanel, constraints);
	}
	
	/***
	 * Creates the "Solve" Button on the bottom of the Puzzle Menu.  
	 */
	private void createSolveButton(){
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		JButton solveBtn = new JButton("Solve");
		
		solveBtn.addActionListener(this);
		contentPane.add(solveBtn, gbc);
	}
	
	/***
	 * ActionListeners for the Solve Button. Can be treated as a "Submit" to solve the problem.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String toDisplay = String.format(
				"\nPuzzle Algorithm: %s "
				+ "\nPuzzle Search: %s", 
				puzzlePatternPnl.getPuzzlePatternTypeSelected().toString(), 
				puzzleSearchPnl.getSearchSelected().toString()
				);
		
		if(!puzzlePatternPnl.isValidPuzzlePattern()){
			toDisplay += "\nPuzzle Pattern is not valid. Pattern must contain the unique integers 0-8."
					+ "\n\nPuzzle will not be solved.";
			
			puzzleResultPnl.setText(toDisplay);
		}
		else{
			String puzzlePattern = puzzlePatternPnl.getPuzzleViewPattern();
			toDisplay += "\nPuzzle Pattern: " +  puzzlePattern + "\nSearching...\n";
			
			puzzleResultPnl.setText(toDisplay);
			
			Puzzle puzzle = new Puzzle();
			boolean isAStarSearch = false; 
			
			if(puzzleSearchPnl.getSearchSelected() == PuzzleAlgorithm.AStar)// if A*
				isAStarSearch = true;
			
			// Instantiate the root node of the tree
			// Enqueue the root, find the successors and dequeue 
			puzzle.setPuzzleState(puzzlePattern, null, 0, 0, null, isAStarSearch);
			toDisplay += puzzle.findSolution(puzzleSearchPnl.getSearchSelected());
			
			puzzleResultPnl.setText(toDisplay);
		}
		
	}

}
