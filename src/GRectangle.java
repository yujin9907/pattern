

// repaint() 시 저장 용도 

import util.Shape;

import java.awt.Graphics2D;

public class GRectangle {
	private int x1, y1, x2, y2;
	private Shape shape;
	
	public void initialize(Shape shape) {
		this.shape = shape;
		this.x1 = 0;
		this.x2 = 0;
		this.y1 = 0;
		this.y2 = 0;
	}
	
	public void setPoint1(int x, int y) {
		this.x1=x;
		this.y1=y;
	}
	
	public void setPoint2(int x, int y) {
		this.x2=x;
		this.y2=y;
		
	}
	
	public void draw(Graphics2D graphics2D) {
		// graphics2D.drawRect(x1, y1, x2-x1, y2-y1);

		if (shape == null) return;
//		if (x2-x1 <= 0 || y2-y1 <=0) return;

		switch (shape) {
			case Rect -> graphics2D.drawRect(x1, y1, x2-x1, y2-y1);
			case Oval -> graphics2D.drawOval(x1, y1, x2-x1, y2-y1);
			case Tri -> {
				int[] xPoints = {
						x1 + (x2-x1) / 2,
						x1 + (x2-x1),
						x1
				};
				int[] yPoints = {
						y1,
						y1 + y2-y1,
						y1 + y2-y1
				};
				graphics2D.drawPolygon(xPoints, yPoints, 3);
			}
			case Poly -> {
				int[] xPoly = {
						x1 + (x2-x1) / 4,
						x1 + (x2-x1) * 3 / 4,
						x1 + x2-x1,
						x1 + (x2-x1) / 2,
						x1
				};
				int[] yPoly = {
						y1,
						y1,
						y1 + (y2-y1) / 2,
						y1 + y2-y1,
						y1 + (y2-y1) / 2
				};
				graphics2D.drawPolygon(xPoly, yPoly, 5);
			}
			case Text -> graphics2D.drawString("Text Box", x1 + 10, y1 + 20);
			default -> throw new IllegalArgumentException("Unexpected value: " + shape);
		}
		
	}
}
