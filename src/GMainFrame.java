import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;

import menu.GMenuBar;

public class GMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// 메뉴바
	private GMenuBar gMenubar;
	// 툴바
	private GToolBar gToolbar;
	// 드로잉 판넬
	private GDrawingPanel gDrawingPanel;
	

	public void initialize() {
		this.gMenubar.initialize();
		this.gToolbar.initialize();
		this.gDrawingPanel.initialize();
	}
	

	// 자식을 만들 때 1. 자식을 만듦 2. new 인스턴스화 3. add
	public GMainFrame() {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 닫을 때 프로그램 종료
		
		// attribute (속성) // 상수 사용 지양 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
		this.setLocation((int) (width*0.1), (int)(height*0.1));
		this.setSize((int) (width*0.4), (int)(height*0.4));
		
		// component (부품)
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout); 
		this.gMenubar = new GMenuBar();
		this.setJMenuBar(gMenubar);
		
		this.gDrawingPanel = new GDrawingPanel();
		this.add(gDrawingPanel, BorderLayout.CENTER);

		this.gToolbar = new GToolBar(gDrawingPanel);
		this.add(gToolbar, BorderLayout.NORTH);
		
		// associated attributes
		// 맨 마지막에 실행돼야 하는 로직
		this.setVisible(true);
	}
	
}
