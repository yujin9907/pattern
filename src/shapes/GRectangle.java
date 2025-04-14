// repaint() 시 저장 용도 
package shapes;



import java.awt.Graphics2D;

public class GRectangle {
	private int x1, y1, x2, y2;
	
	public void setPoint1(int x, int y) {
		this.x1=x;
		this.y1=y;
	}
	
	public void setPoint2(int x, int y) {
		this.x2=x;
		this.y2=y;
		
	}
	
	public void draw(Graphics2D graphics2D) {
		graphics2D.drawRect(x1, y1, x2-x1, y2-y1);
		
	}

}
