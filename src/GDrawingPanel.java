import util.Shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

public class GDrawingPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private Vector<GRectangle> rectangles; // 초기화 되지 않도록 저장 용도
	private Shape shape; // toolbar 선택 도형
	private GRectangle rectangle; // 공유 객체
	private GTransformer transformer; // 공유
    
    public void initialize() {
    	
    }
    
    public void initialize(String shapeStr) {
		shape = Shape.of(shapeStr);
		rectangle.initialize(shape);
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

		rectangle = new GRectangle();
		transformer = new GTransformer(rectangle);

		MouseEventHandler mouseHandler = new MouseEventHandler(transformer);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
        
        this.rectangles = new Vector<GRectangle>();
    }
    
    
    
    
    
   private class MouseEventHandler implements MouseListener, MouseMotionListener {

		private final GTransformer transformer;

	   public MouseEventHandler(GTransformer transformer) {
           this.transformer = transformer;
	   }

	   @Override
		public void mouseClicked(MouseEvent e) {

		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// transformer = new GTransformer(rectangle);
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());
			transformer.start(graphics2D,e.getX(), e.getY());
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e) {

			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());
			transformer.drag(graphics2D, e.getX(), e.getY());
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			Graphics2D graphics2D = (Graphics2D) getGraphics();
			graphics2D.setXORMode(getBackground());
			GRectangle g = transformer.finish(graphics2D, e.getX(), e.getY());
			rectangles.add(g);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		
	   
   }
}