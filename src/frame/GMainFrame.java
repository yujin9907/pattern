package frame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	// attribute
	private static final long serialVersionUID = 1L;
	
	// component
	private GMenuBar gMenubar; // 메뉴바
	private GShapeToolBar gToolbar; // 툴바
	private GDrawingPanel gDrawingPanel; // 드로잉 판넬
	
	// associate

	public void initialize() {
		// associate
		this.gMenubar.associate(this.gDrawingPanel);
		this.gToolbar.associate(this.gDrawingPanel);
		
		// associated attributes: 맨 마지막에 실행돼야 하는 로직
		this.setVisible(true);
		
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
		this.gToolbar = new GShapeToolBar(gDrawingPanel);
		this.add(gDrawingPanel, BorderLayout.CENTER);
		this.add(gToolbar, BorderLayout.NORTH);
		

	}
	
}
