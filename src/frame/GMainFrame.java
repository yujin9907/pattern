package frame;
import global.GConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.*;

public class GMainFrame extends JFrame {
	// attribute
	private static final long serialVersionUID = 1L;
	
	// component
	private GMenuBar gMenubar; // 메뉴바
	private GShapeToolBar gToolbar; // 툴바
	private GDrawingPanel gDrawingPanel; // 드로잉 판넬
	private JTabbedPane tabbedPane; // 탭
	
	// associate

	public void initialize() {
		// associate
		this.gMenubar.associate(this.gDrawingPanel, this.tabbedPane);
		this.gToolbar.associate(this.gDrawingPanel);
		
		// associated attributes: 맨 마지막에 실행돼야 하는 로직
		this.setVisible(true);

		// init
		this.gMenubar.initialize();
		this.gToolbar.initialize();
		this.gDrawingPanel.initialize();
	}
	

	public GMainFrame() {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 닫을 때 프로그램 종료
		GConstants constants = new GConstants();
		constants.readFromFile("src/config/config.xml");
		
		// attribute (속성) // 상수 사용 지양 // TODO
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
//		this.setSize((int) (width*0.4), (int)(height*0.4));
		this.setSize(GConstants.EMainFrame.eW.getValue(), GConstants.EMainFrame.eH.getValue());
//		this.setLocation((int) (width*0.1), (int)(height*0.1));
		this.setLocation(GConstants.EMainFrame.eX.getValue(), GConstants.EMainFrame.eY.getValue());


		// component (부품)

		LayoutManager layout = new BorderLayout();
		this.setLayout(layout);
		this.gMenubar = new GMenuBar();
		this.setJMenuBar(gMenubar);

		this.tabbedPane = new JTabbedPane();
		this.gDrawingPanel = new GDrawingPanel();
		this.gToolbar = new GShapeToolBar(gDrawingPanel);
//		this.add(gDrawingPanel, BorderLayout.CENTER);
		tabbedPane.addTab("default", gDrawingPanel);

		this.add(gToolbar, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);

	}
	
}
