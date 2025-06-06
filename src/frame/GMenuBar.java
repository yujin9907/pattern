package frame;

import javax.swing.*;

import menus.GEditMenu;
import menus.GFileMenu;
import menus.GgraphicMenu;

public class GMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	// component 
	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GgraphicMenu graphicMenu; 
	
	// associations
	private GDrawingPanel drawingPanel;
	private JTabbedPane jTabbedPane;

	
	public GMenuBar() {
		this.fileMenu = new GFileMenu();
		this.editMenu = new GEditMenu();
		this.graphicMenu = new GgraphicMenu();

		this.add(this.fileMenu);
		this.add(this.editMenu);
		this.add(this.graphicMenu);
		
	}

	public void initialize() {
		this.fileMenu.associate(drawingPanel, jTabbedPane);
		this.fileMenu.initialize();

	}

	public void associate(GDrawingPanel gDrawingPanel, JTabbedPane jTabbedPane) {
		this.drawingPanel = gDrawingPanel;
		this.jTabbedPane = jTabbedPane;
	}
}
