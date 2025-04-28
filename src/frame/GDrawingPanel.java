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

import frame.GShapeToolBar.EShapeTool;
import shapes.GShape;
import transformers.GDrawer;
import transformers.GTransformer;

public class GDrawingPanel extends JPanel {


    
    private static final long serialVersionUID = 1L;
	// 드로잉 상태
    private Vector<GShape> shapes; // 초기화 되지 않도록 저장 용도
	private GTransformer transformer;
	// 제약조건
	private EShapeTool eShapeTool;
	private EDrawingState eDrawingState;
	private GShape currentShape;

	
	
	public enum EDrawingState {
		eIdle,
		e2P,
		eNP
	}
	

	public enum EDrawingType { // 기하 기초 지식 : 모든 2d도형은 n개의 점으로 표현된다
		e2P,
		eNP
	}
	
	public void setEShapeType(EShapeTool eShapeTool) {
		this.eShapeTool = eShapeTool;
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
        for(GShape r : shapes) {
        	r.draw((Graphics2D) g);
        }
    }
    
    
    // 2 Point 의 경우
    private void startDrawing(int x, int y) {
//    	(예외처리 newShape() 안으로 옮기기)
    	try {
			currentShape = eShapeTool.newShape();
			this.shapes.add(currentShape);
			transformer = new GDrawer(currentShape);
			transformer.start((Graphics2D) getGraphics(),x, y);
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException
				| IllegalAccessException e1) {
			e1.printStackTrace();
		}
    }
    private void keepDrawing(int x, int y) {
    	transformer.drag((Graphics2D) getGraphics(),x, y);
		this.repaint();
    }
    private void finishDrawing(int x, int y) {
    	transformer.finish((Graphics2D) getGraphics(),x, y);
    }
    // N point 의 경우 추가 
    private void addPoint(int x, int y) {}

    public GDrawingPanel() {
        this.setBackground(Color.WHITE);
        
        MouseEventHandler mouseHandler = new MouseEventHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        
        this.shapes = new Vector<GShape>();
        this.eShapeTool = null;
        this.eDrawingState = EDrawingState.eIdle;
    }
    
    
    
    
    
   private class MouseEventHandler implements MouseListener, MouseMotionListener {


		@Override
		public void mouseClicked(MouseEvent e) {

		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				// constraint : set Transformer (locate, scale, ... 구분하기 위함)
				if (eShapeTool.getDrawingType() == EDrawingType.e2P) {
					startDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2P;
				}
			}
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e) {

			if (eDrawingState == EDrawingState.e2P) {
				keepDrawing(e.getX(), e.getY());
			}

		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			if (eDrawingState == EDrawingState.e2P) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
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