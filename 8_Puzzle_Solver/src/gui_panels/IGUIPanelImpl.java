package gui_panels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/***
 * An IGUIPanel Implementer Class. All implemented code for an IGUIPanel class can be reused in this class. 
 * @author Ricardo Arroyo
 *
 */
public class IGUIPanelImpl implements IGUIPanel {

	@Override
	public JPanel generatePanel() {
		// Unimplemented
		return null;
	}

	/***
	 * Create a TitledBoarder Object and return it given a parameter name.
	 * @param name The title the TitledBorder Object wishes to have
	 * @return A TitledBoarder Object given parameter name
	 */
	public static TitledBorder createTitledBorder(String name){
		TitledBorder title = BorderFactory.createTitledBorder("Title");
		title = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), name);
		title.setTitleJustification(TitledBorder.CENTER);
		
		return title;
	}
}
