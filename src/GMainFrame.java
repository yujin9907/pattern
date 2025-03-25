import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;

import menu.GMenuBar;

public class GMainFrame extends JFrame { // 제이프레임을 상속한 것 = 이게 특화

	private static final long serialVersionUID = 1L;
	
	// 메뉴바
	private GMenuBar gMenubar;
	// 툴바
	private GToolBar gToolbar;
	// 드로잉 판넬
	private GDrawingPanel gDrawingPanel;
	

	// 자식을 만들 때 1. 자식을 만듦 2. new 인스턴스화 3. add
	public GMainFrame() {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 닫을 때 프로그램 종료
		
		// attribute (속성) // 상수로 하지 마라
		this.setLocation(100, 200);
		this.setSize(700, 600);  
		
		// component (부품)
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout); 
		this.gMenubar = new GMenuBar();
		this.setJMenuBar(gMenubar);
		
		this.gDrawingPanel = new GDrawingPanel();

		this.gToolbar = new GToolBar(gDrawingPanel);
		
		this.add(gDrawingPanel, BorderLayout.CENTER);
		this.add(gToolbar, BorderLayout.NORTH);
		
		// associated attributes
		this.setVisible(true); // 메인프레임을 그려라
	}
	
	public void initialize() {
		this.gMenubar.initialize();
		this.gToolbar.initialize();
		this.gDrawingPanel.initialize();
		
	}
}
