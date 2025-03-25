import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import util.Shape;

public class GDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String shape;
	
	public GDrawingPanel() {
		 this.setBackground(Color.WHITE);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("버튼 클릭 시 해당 도형이 그려집니다.", 30, this.getSize().height-30);
		
		if (shape == null || shape.equals("")) return;
		
		Shape s = Shape.of(this.shape);
        switch (s) {
	        case Rect -> g.drawRect(50, 50, 50, 50);
	        case Oval -> g.drawOval(50, 50, 100, 70);
	        case Tri -> {
	            int[] xPoints = new int[]{100, 150, 50};
	            int[] yPoints = new int[]{50, 150, 150};
	            g.drawPolygon(xPoints, yPoints, 3);
	        }
	        case Poly -> {
	            int[] xPoly = new int[]{50, 100, 150, 100, 50};
	            int[] yPoly = new int[]{50, 30, 50, 100, 100};
	            g.drawPolygon(xPoly, yPoly, 5);
	        }
	        case Text -> g.drawString("Text Box", 60, 80);
	        default -> throw new IllegalArgumentException("Unexpected value: " + shape);
	    }
	}
	
	public void initialize(String shape) {
		this.shape = shape;
		repaint();
	}
	
	public void initialize() {}
	
	/*
	// 이벤트 담당 에이전트(이벤트 헨들러)
	public void draw(String shape) {
		Graphics g = this.getGraphics(); //기존 스타일을 그대로 유지하려면 os 의 그래픽스를 그대로 가져와야함
				
	}
	*/

	
	// 3.24, 이걸로 설명
	// 행위를 확장하는 것
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 부모 오버라이드니까 먼저 호출 함 해줘야 됨. 공식이다.
		this.draw(g);
	}
	private void draw(Graphics g) {
		g.drawRect(10, 10, 50, 50);
	}
}
