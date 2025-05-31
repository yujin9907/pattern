package menus;


import frame.GDrawingPanel;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GEditMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	private JMenuItem propertyItem;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JMenuItem groupItem;




	// associate
	private GDrawingPanel pannel;
	public void associate(GDrawingPanel gDrawingPanel) {
		this.pannel = gDrawingPanel;
	}

	public GEditMenu() {
		super("Edit");
		this.pannel = pannel;

		this.groupItem = new JMenuItem("Group");
		this.propertyItem = new JMenuItem("Property");
		this.undoItem = new JMenuItem("Undo");
		this.redoItem = new JMenuItem("Redo");

		this.add(this.groupItem);
		this.add(this.propertyItem);
		this.add(this.undoItem);
		this.add(this.redoItem);

		this.groupItem.addActionListener(e -> {
			addGrouping();
		});
	}

	private void addGrouping() {
		this.pannel.groupingShapes();
	}

}
