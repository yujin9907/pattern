package menus;


import global.GConstants;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GgraphicMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	private JMenuItem lineThicknessItem;
	private JMenuItem lineStyleItem;
	private JMenuItem fontStyleItem;
	private JMenuItem fontSizeItem;

	public GgraphicMenu() {
		super("Graphic");
		

		for (GConstants.EGraphicsMenuItem item : GConstants.EGraphicsMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(item.getName());
			// menuItem.addActionListener(actionHandler); // TODO
			menuItem.setActionCommand(item.name());
			this.add(menuItem);
		}
	}
	
	
	

}
