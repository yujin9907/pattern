package menus;


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
		
		this.lineThicknessItem = new JMenuItem("Line Thickness");
		this.lineStyleItem = new JMenuItem("Line Style");
		this.fontStyleItem = new JMenuItem("Font Style");
		this.fontSizeItem = new JMenuItem("Font Size");
		
		this.add(lineThicknessItem);
		this.add(lineStyleItem);
		this.add(fontStyleItem);
		this.add(fontSizeItem);
	}
	
	
	

}
