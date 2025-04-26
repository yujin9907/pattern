package frame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GShapeToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	private GDrawingPanel drawingPanel;
	
	public void initialize() {
	}
	
	public GShapeToolBar(GDrawingPanel gDrawingPanel) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT)); // 좌측정
		
		addToolbarButton();
	}

	// component : GToolbar 버튼 (rectangle, triangle, oval, polygon, textbox)
	private void addToolbarButton() {

		// enum = 배열 = n개의 원소를 가진 벡터
		ButtonGroup group = new ButtonGroup();

		for (GDrawingPanel.EShapeType eShapeType : GDrawingPanel.EShapeType.values()) {
			JRadioButton button = new JRadioButton(eShapeType.getName());
			ActionListener actionListener = new ActionHandler();
			button.addActionListener(actionListener);
			button.setActionCommand(eShapeType.toString()); // ActionHandler.actionPerformed() 에서 꺼내 쓸 거임
			group.add(button);
			this.add(button); // TODO : 내가 쓴 코든데 필요함?
		}
	}

	public void associate(GDrawingPanel gDrawingPanel) {
		this.drawingPanel = gDrawingPanel;
		
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String sShapeType = e.getActionCommand();
			GDrawingPanel.EShapeType eShapeType = GDrawingPanel.EShapeType.valueOf(sShapeType);
			drawingPanel.setEShapeType(eShapeType);
		}
	}

}
