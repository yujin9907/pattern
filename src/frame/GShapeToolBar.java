package frame;

import global.GConstants;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class GShapeToolBar extends JToolBar {
	
	private GConstants.EShapeTool eShapeTool;
	

	private static final long serialVersionUID = 1L;
	
	// component : GToolbar // 자식 : 내가 등록해야
	
	// associations // 친구 : 부모가 나에게 가르쳐줘야 
	private GDrawingPanel drawingPanel;
	
	public void initialize() {
		// select 버튼을 선택하도록 초기화
		// 왜 여기다 하나? association (드로잉판넬) 은 선택된 버튼에 영향을 받음 -> initialize() 에 해당 코드를 넣어 안전하게
		JRadioButton b = (JRadioButton) this.getComponent(GConstants.EShapeTool.eSelect.ordinal());
		b.doClick();
	}

	public void associate(GDrawingPanel gDrawingPanel) {
		this.drawingPanel = gDrawingPanel;
	}
	
	public GShapeToolBar(GDrawingPanel gDrawingPanel) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT)); // 좌측정렬
		
		addToolbarButton();
	}

	private void addToolbarButton() {
		ButtonGroup group = new ButtonGroup();

		for (GConstants.EShapeTool tool : GConstants.EShapeTool.values()) {
			// 라디오 버튼 생성
			JRadioButton button = new JRadioButton(tool.getName());

			String imagePath = "src/rsc/" + tool.getImageFileName() + ".png"; // 이미지 경로 설정
			ImageIcon icon = new ImageIcon(imagePath);
			icon.setImage(icon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
			button.setIcon(icon);
			System.out.println("path :" + imagePath);

			// 액션 리스너 추가
			ActionListener actionListener = new ActionHandler();
			button.addActionListener(actionListener);
			button.setActionCommand(tool.toString());
			group.add(button);
			this.add(button);
		}
	}



	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String sShapeType = e.getActionCommand();
			GConstants.EShapeTool eShapeTool = GConstants.EShapeTool.valueOf(sShapeType); // 이벤트에서 넘겨준 string 을 통해 valueOf() = 메모리값을 찾을 수 있다.
			drawingPanel.setEShapeType(eShapeTool);
		}
	}

}
