package frame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import frame.GDrawingPanel.EDrawingType;
import shapes.GRectangle;
import shapes.GShape;

public class GShapeToolBar extends JToolBar {
	
	private EShapeType eShapeType;
	
	public enum EShapeType { // 툴바에 넣을지 드로잉판넬에 넣을지 아주 고민해봐야 함
		// enum : 상수, 심볼(값), 순서(어래이라서)을 모두 포함하고 있음
		// 상수 코드에다 쓰지말고 나중에 파일 따로 빼라 (constant 나 resource 로)
		eSelect("select", EDrawingType.e2P, GRectangle.class),
		eRectangle("rectangle", EDrawingType.e2P, GRectangle.class),
		eEllipse("ellipse", EDrawingType.e2P, GRectangle.class),
		eLine("line", EDrawingType.e2P, GRectangle.class),
		ePolygon("polygon", EDrawingType.eNP, GRectangle.class);

		private String name;
		private EDrawingType drawingType;
		private Class<? extends GShape> classShape;
		EShapeType(String name, EDrawingType drawingType, Class<? extends GShape> gShape) {
			this.name = name;
			this.drawingType = drawingType;
			this.classShape = gShape;
		}
		public String getName() {
			return this.name;
		}

		public EDrawingType getDrawingType() {
			return this.drawingType;
		}

		// 익셉션처리 추후 aspect 로 처리할 예정 : 익셉션처리 아주 중요하다 마구잡이로 던지면 안 된다 이렇게
		public GShape newShape() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            return classShape.getConstructor().newInstance();
		}
	}

	
	

	private static final long serialVersionUID = 1L;
	
	// component : GToolbar // 자식 : 내가 등록해야 
//	private JRadioButton rectangleButton;
//	private JRadioButton traiangleButton;
//	private JRadioButton ovalButton;
//	private JRadioButton polygonButton;
//	private JRadioButton textButton;
	
	// associations // 친구 : 부모가 나에게 가르쳐줘야 
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

		for (EShapeType eShapeType : EShapeType.values()) {
			JRadioButton button = new JRadioButton(eShapeType.getName());
			ActionListener actionListener = new ActionHandler();
			button.addActionListener(actionListener);
			button.setActionCommand(eShapeType.toString()); // ActionHandler.actionPerformed() 에서 꺼내 쓸 거임
			group.add(button);
			this.add(button); // TODO : 내가 쓴 코든데 필요함?
		}
	}
	
	// GToolbar 이벤트
//	private void addShapeListener(GDrawingPanel gDrawingPanel) {
//        ActionListener actionListener = e -> {
//            gDrawingPanel.initialize(e.getActionCommand());
//        };
//	}

	
	public void associate(GDrawingPanel gDrawingPanel) {
		this.drawingPanel = gDrawingPanel;
		
	}

	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String sShapeType = e.getActionCommand();
			EShapeType eShapeType = EShapeType.valueOf(sShapeType); // 이벤트에서 넘겨준 string 을 통해 valueOf() = 메모리값을 찾을 수 있다.
			drawingPanel.setEShapeType(eShapeType);
		}
	}

}
