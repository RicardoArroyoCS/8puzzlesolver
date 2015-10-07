package gui_panels;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/***
 * A IGUIPanel class used for the Results Panel of the JFrame. Displays a TextBox
 * used to show the results of a given search.
 * @author Ricardo Arroyo
 *
 */
public class PuzzleResultsPanel implements IGUIPanel {
	private TitledBorder title;		
	private JTextArea txtArea;
	private JScrollPane pane;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel resultPnl;
	
	public PuzzleResultsPanel(int fillIn, int gridxIn, int gridyIn, int anchorIn){
		gbc.fill = fillIn;
		gbc.gridx = gridxIn;
		gbc.gridy = gridyIn;
		gbc.anchor = anchorIn;
		
		resultPnl = new JPanel();
	}
	
	/***
	 * Get the TextArea of of the Results Panel
	 * @return Currently used TextArea
	 */
	public JTextArea getTextArea(){
		return txtArea;
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
	 * Set the Grid Height of the Object
	 * @param heightIn Height
	 */
	public void setGridHeight(int heightIn){
		gbc.gridheight = heightIn;
	}
	
	/***
	 * Set the Grid Width of the Object
	 * @param widthIn Width
	 */
	public void setGridWidth(int widthIn){
		gbc.gridwidth = widthIn;
	}
	
	/***
	 * Set the Y Weight of the Object
	 * @param weightyIn weighty
	 */
	public void setWeighty(double weightyIn){
		gbc.weighty = weightyIn;
	}
	
	/***
	 * Set the X Weight of the Object
	 * @param weightxIn weightx
	 */
	public void setWeightx(double weightxIn){
		gbc.weightx = weightxIn;
	}
	
	/***
	 * Set the Object's Insets
	 * @param insetIn Insets Object
	 */
	public void setInsets(Insets insetIn){
		gbc.insets = insetIn;
	}
		
	public JPanel generatePanel(){
		// Create Titled Border
		title = new TitledBorder(createTitledBorder("Result"));
		resultPnl.setBorder(title);
		
		setWidget();

		return resultPnl;
	}
	
	/***
	 * Set the TextArea of the Panel
	 */
	private void setWidget(){
		txtArea = new JTextArea(20, 25);
		pane = new JScrollPane(txtArea);
		
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);

		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		resultPnl.add(pane);
		resultPnl.setBorder(title);
	}
	
	/***
	 * Set the TextArea text of the PuzzleResultsPanel
	 * @param text The text to appear in the ResultsPanel
	 */
	public void setText(String text){
		txtArea.setText(text);
		txtArea.update(txtArea.getGraphics());
		txtArea.repaint();
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
