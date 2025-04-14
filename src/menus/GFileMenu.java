package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GFileMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;
	
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem quitItem;
	
	
	public GFileMenu() {
		super("File");
		
		this.newItem = new JMenuItem("New");
		this.openItem = new JMenuItem("Open");
		this.saveItem = new JMenuItem("Save");
		this.saveAsItem = new JMenuItem("Save As");
		this.quitItem = new JMenuItem("Quit");

		this.add(this.newItem);
		this.add(this.openItem);
		this.add(this.saveItem);
		this.add(this.saveAsItem);
		this.add(this.quitItem);
	}

}
