package gui_panels;

import gui_menu.ButtonsFactory;
import gui_menu.PuzzleMenu;
import gui_menu.PuzzleMenu.PuzzleAlgorithm;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
/***
 * A IGUIPanel class used for the Pattern Searches of the JFrame. Generally displays  Buttons
 * for the type of Puzzle Search to be used. (A*, BFS, DFS, GDFS).
 * @author Ricardo Arroyo
 *
 */
public class PuzzleSearchesPanel implements IGUIPanel, ActionListener {
	private static final int ROW = 0;
	private static final int COLUMN = 1;
	
	private TitledBorder title;		
	private ArrayList<JButton> searches;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel searchPnl;
	
	private PuzzleAlgorithm searchSelected = PuzzleAlgorithm.AStar;
	
	public PuzzleSearchesPanel(int fillIn, int gridxIn, int gridyIn, int anchorIn){
		gbc.fill = fillIn;
		gbc.gridx = gridxIn;
		gbc.gridy = gridyIn;
		gbc.anchor = anchorIn;
		
		searchPnl = new JPanel(new GridLayout(ROW, COLUMN));
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
	 * Returns the enum PuzzleAlgorithm of the search the Panel currently has selected (A*, BFS,...)
	 * @return The enum PuzzleAlgorithm of the search selected
	 */
	public PuzzleAlgorithm getSearchSelected(){
		return searchSelected;
	}
	
	public JPanel generatePanel(){
		// Create Titled Border
		title = new TitledBorder(createTitledBorder("Select a Search"));
		searchPnl.setBorder(title);
		
		setWidget();

		return searchPnl;
	}
	
	/***
	 * Set the Buttons (A* Search, Breadth First Search,...) of the PuzzleSearchesPanel
	 */
	private void setWidget(){
		searches = ButtonsFactory.getPuzzleSearchButtons();
		
		for(JButton button : searches){
			searchPnl.add(button);
			button.addActionListener(this);
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
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == searches.get(0)){
			System.out.println("A* Search");
			searchSelected =  PuzzleMenu.PuzzleAlgorithm.AStar;
		}
		else if(event.getSource() == searches.get(1)){
			System.out.println("Breadth First Search");
			searchSelected =  PuzzleMenu.PuzzleAlgorithm.BFS;
		}
		else if(event.getSource() == searches.get(2)){
			System.out.println("Depth First Search");
			searchSelected =  PuzzleMenu.PuzzleAlgorithm.DFS;
		}
		else if(event.getSource() == searches.get(3)){
			System.out.println("Greedy Breadth First Search");
			searchSelected =  PuzzleMenu.PuzzleAlgorithm.GBFS;
		}
		
	}
	
}
