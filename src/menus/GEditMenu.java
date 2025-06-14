package menus;


import global.GConstants;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GEditMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private JMenuItem propertyItem;
	private JMenuItem undoItem;
	private JMenuItem redoItem;

	public GEditMenu() {
		super("Edit");

		for (GConstants.EEditMenuItem item : GConstants.EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(item.getName());
			// menuItem.addActionListener(actionHandler); // TODO
			menuItem.setActionCommand(item.name());
			this.add(menuItem);
		}

	}

}
