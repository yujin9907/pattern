import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.Shape;

public class GDrawingPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private String shape; // 라디오 버튼 선택 도형
    private Rectangle bounds; // 도형 위치 및 크기
    private boolean dragging = false;
    private int offsetX, offsetY;
    private Point startPoint, endPoint;
    
    public void initialize() {
    	
    }
    
    public void initialize(String shape) {
        this.shape = shape;
        bounds = null;
        repaint();
    }
    
    

    /* 0331 */

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    private void draw(int x, int y, int w, int h) {
    	// 2차원 도형 전용 Graphics 확장 클래스
        Graphics2D g = (Graphics2D) this.getGraphics(); 
        g.setXORMode(getBackground()); // xor 모드 (일부 지우기)
        g.drawRect(x, y, w, h);
    }
     
    
    public GDrawingPanel() {
        this.setBackground(Color.WHITE);
        
        // 이벤트 핸들러 등록
        MouseEventHandler mouseHandler = new MouseEventHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }
    
    // os 가 아는 인터페이스를 구현해서 이벤트를 호출할 수도록
    // 인터페이스는 표준화하여 프로그래밍 하기 위함
   private class MouseEventHandler implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 마우스 클릭을 할 때, 이 이벤트만 타는 게 아니라 프레스 릴리즈 같이 동작함.
			System.out.println("클릭 ");
			// 클릭 시 그리기 
			draw(e.getX(), e.getY());
			
		}
		
		private int x1, y1;
		
		//region 이렇게 네개 함게 움직이는 이벤트임
		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("프레스");
			// 원점 (고정)
			this.x1 = e.getX();
			this.y1 = e.getY();
			
			// 지우기 위한 좌표
			this.x2 = x1;
			this.y2 = y2;
		}
		
		private int x2, y2;
		private int ox2, oy2; // oldX2
		
		// motion implement
		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("드래그 ");

			// 지우기 (xor)
			draw(x1, y1, x2-x1, y2-y1);
			
			this.x2 = e.getX();
			this.y2 = e.getY();
			
			// 그리
			draw(x1, y1, x2-x1, y2-y1);
			
		}

		// motion implement
		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("무브 ");
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("릴리즈");
		}
		//region

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("엔터");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("나가기");
		}

		
	   
   }
   
   
    
}