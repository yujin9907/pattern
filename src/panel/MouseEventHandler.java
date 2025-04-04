package panel;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

public class MouseEventHandler implements MouseListener, MouseMotionListener {

	private int x1, y1; // 원점 (고정)
	private int x2, y2; // xor지우기 위한 좌표 
	
	// 추가 코드
	private Rectangle boundingBox; // 도형위치 알아내기
	private boolean isDrawing;
	
	int startX, startY;
	int width, height;
	
	
	private final GDrawingPanel drawingPanel;
	

	public MouseEventHandler(GDrawingPanel drawingPanel) {
		super();
		this.drawingPanel = drawingPanel;
		initialize();
	}
	
	public void initialize() {
		this.isDrawing = true;
		this.boundingBox = null;
		this.startX = 0;
		this.startY = 0;
		this.width = 0;
		this.height = 0;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	
	//region 이렇게 네개 함게 움직이는 이벤트임
	@Override
	public void mousePressed(MouseEvent e) {
		
        if (boundingBox != null && boundingBox.contains(e.getPoint())) {
        	// 도형 내부 -> 이동
            if (SwingUtilities.isLeftMouseButton(e)) {
            	isDrawing = false;
            	
            	// 도형 상대 좌표값 계산 (이동 자연스러운 애니메이션을 위해)
                x1 = e.getX() - boundingBox.x;
                y1 = e.getY() - boundingBox.y;
            } 
        } else {
            // 도형 외부 -> 새로운 도형 그리기
            if (SwingUtilities.isLeftMouseButton(e)) {
            	this.isDrawing = true;
            	
        		this.x1 = e.getX();
        		this.y1 = e.getY();
        		
        		this.x2 = x1;
        		this.y2 = y1;
            }
        }
	}
	
	
	// motion implement
	@Override
	public void mouseDragged(MouseEvent e) {

		drawingPanel.draw(startX, startY, width, height);


		if (!isDrawing) {
			// 이동
	        int newX = e.getX() - x1;
	        int newY = e.getY() - y1;
	        boundingBox.setLocation(newX, newY);

	        startX = newX;
	        startY = newY;
			
			boundingBox.setLocation(e.getPoint());
		} else {
			// 그리기
		    startX = Math.min(x1, e.getX());
		    startY = Math.min(y1, e.getY());
			
			this.x2 = e.getX();
			this.y2 = e.getY();
			
			
			width = Math.abs(e.getX()-x1);
			height = Math.abs(e.getY()-y1);
		}
		
		
		drawingPanel.draw(startX, startY, width, height); // 그리기 (xor)
	}

	// motion implement
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isDrawing) {
			boundingBox = new Rectangle(startX, startY, width, height);
		}
	}
	//region

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
