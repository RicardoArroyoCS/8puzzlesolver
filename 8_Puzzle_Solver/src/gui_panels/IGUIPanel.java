package gui_panels;

import javax.swing.JPanel;

/***
 * An Interface Class any GUI Panel must inherit.
 * @author Ricardo Arroyo
 *
 */
public interface IGUIPanel {

	/***
	 * Generates a Panel based on the purpose of the IGUIPanel Class.
	 * @return A generated JPanel Object
	 */
	JPanel generatePanel();
	
}
