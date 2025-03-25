package menu;
import javax.swing.JMenuBar;

import menu.GEditMenu;
import menu.GFileMenu;
import menu.GgraphicMenu;

public class GMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GgraphicMenu graphicMenu; 
	
	public GMenuBar() {
		this.fileMenu = new GFileMenu();
		this.editMenu = new GEditMenu();
		this.graphicMenu = new GgraphicMenu();

		this.add(this.fileMenu);
		this.add(this.editMenu);
		this.add(this.graphicMenu);
		
	}

	public void initialize() {
		// TODO Auto-generated method stub
	}
}
