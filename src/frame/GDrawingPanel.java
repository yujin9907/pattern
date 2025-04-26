package frame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JPanel;

import shapes.GRectangle;
import shapes.GShape;
import transformers.GTransformer;

public class GDrawingPanel extends JPanel {

	public enum EShapeType { // 툴바에 넣을지 드로잉판넬에 넣을지 아주 고민해봐야 함
		// enum : 상수, 심볼(값), 순서(어래이라서)을 모두 포함하고 있음
		// 상수 코드에다 쓰지말고 나중에 파일 따로 빼라 (constant 나 resource 로)
		eRectangle("rectangle", EDrawingType.e2P, GRectangle.class),
		eEllipse("ellipse", EDrawingType.e2P, GRectangle.class),
		eLine("line", EDrawingType.e2P, GRectangle.class),
		ePolygon("polygon", EDrawingType.eNP, GRectangle.class),
		eMove("move", EDrawingType.eNP, GRectangle.class),
		eResize("resize", EDrawingType.eNP, GRectangle.class);

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
		public GShape getClassShape() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            return classShape.getConstructor().newInstance();
		}
	}
	public enum EDrawingType { // 기하 기초 지식 : 모든 2d도형은 n개의 점으로 표현된다
		e2P,
		eNP
	}
    
    private static final long serialVersionUID = 1L;
    private Vector<GRectangle> rectangles; // 초기화 되지 않도록 저장 용도
	private EShapeType eShapeType;
	public void setEShapeType(EShapeType eShapeType) {
		this.eShapeType = eShapeType;
	}

    
    public void initialize() {
    	
    }
    
    public void initialize(String shape) {
        repaint();
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        // 그린 도형 저장
        for(GRectangle r : rectangles) {
        	r.draw((Graphics2D) g);
        }
    }
     
    
    public GDrawingPanel() {
        this.setBackground(Color.WHITE);
        
        MouseEventHandler mouseHandler = new MouseEventHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        
        this.rectangles = new Vector<GRectangle>();
    }
    
    
    
    
    
   private class MouseEventHandler implements MouseListener, MouseMotionListener {

		private GTransformer transformer;

		@Override
		public void mouseClicked(MouseEvent e) {

		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			switch (eShapeType) {
				case eMove, eResize -> {
					if (rectangles == null) return;
					for(GRectangle r : rectangles) {
						if (r.getRectangle().contains(e.getX(), e.getY())) {
							transformer.start(r);
							break;
						}
					}
					break;
				}
				default -> {
					transformer = new GTransformer();

					Graphics2D graphics2D = (Graphics2D) getGraphics();
					graphics2D.setXORMode(getBackground());
					transformer.start(graphics2D,e.getX(), e.getY());
					break;
				}
			}
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e) {
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());

			switch (eShapeType) {
				case eMove -> {
					transformer.move(graphics2D, e.getX(), e.getY());
					break;
				}
				case eResize -> {
					transformer.resize(graphics2D, e.getX(), e.getY());
					break;
				}
				default -> {
					transformer.drag(graphics2D, e.getX(), e.getY());
					break;
				}
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch (eShapeType) {
				case eMove -> {
					break;
				}
				case eResize -> {
					break;
				}
				default -> {
					Graphics2D graphics2D = (Graphics2D) getGraphics();
					graphics2D.setXORMode(getBackground());
					GRectangle g = transformer.finish(graphics2D, e.getX(), e.getY());
					rectangles.add(g);
					break;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
   }
}