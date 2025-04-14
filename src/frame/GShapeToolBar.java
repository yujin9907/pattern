package frame;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GShapeToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	// component : GToolbar // 자식 : 내가 등록해야 
	private JRadioButton rectangleButton;
	private JRadioButton traiangleButton;
	private JRadioButton ovalButton;
	private JRadioButton polygonButton;
	private JRadioButton textButton;
	
	// associations // 친구 : 부모가 나에게 가르쳐줘야 
	private GDrawingPanel drawingPanel;
	
	public void initialize() {
		
	}
	
	public GShapeToolBar(GDrawingPanel gDrawingPanel) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT)); // 좌측정
		
		addToolbarButton();
		addShapeListener(gDrawingPanel);
	}

	// component : GToolbar 버튼 (rectangle, triangle, oval, polygon, textbox)
	private void addToolbarButton() {
		
		this.rectangleButton = new JRadioButton("rectangle");
		this.traiangleButton = new JRadioButton("Triangle");
		this.ovalButton = new JRadioButton("Oval");
		this.polygonButton = new JRadioButton("Polygon");
		this.textButton = new JRadioButton("TextBox");
		
		ButtonGroup group = new ButtonGroup();
		group.add(rectangleButton);
		group.add(traiangleButton);
		group.add(ovalButton);
		group.add(polygonButton);
		group.add(textButton);

		this.add(rectangleButton);
		this.add(traiangleButton);
		this.add(ovalButton);
		this.add(polygonButton);
		this.add(textButton);
		

	}
	
	// GToolbar 이벤트
	private void addShapeListener(GDrawingPanel gDrawingPanel) {

        ActionListener actionListener = e -> {
            gDrawingPanel.initialize(e.getActionCommand()); 
            
        };
        rectangleButton.addActionListener(actionListener);
        traiangleButton.addActionListener(actionListener);
        ovalButton.addActionListener(actionListener);
        polygonButton.addActionListener(actionListener);
        textButton.addActionListener(actionListener);
	}

	
	public void associate(GDrawingPanel gDrawingPanel) {
		this.drawingPanel = gDrawingPanel;
		
	}

}
