// repaint() 시 저장 용도 
package shapes;



import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

public class GRectangle extends GShape {


	private Rectangle2D.Float rectangle; // x1, x2,.. 와 같은 역할

	public Float getRectangle() {
		return rectangle;
	}

	public GRectangle() {
		
		this.rectangle = new Rectangle2D.Float(0,0,0,0); // Float() defualt 가 0000 이지만 명시적으로 작성하는 게 좋다 
		super.shape = this.rectangle;
	}

	public void setPoint(int x, int y) {
		this.rectangle.setFrame(x, y, 0, 0); 
	}
	
	public void dragPoint(int x, int y) {
		double ox = rectangle.getX(); // original x
		double oy = rectangle.getY(); // original y
		double w = x - ox;
		double h = y - oy;
		
		this.rectangle.setFrame(ox, oy, w, h); 
	}


	public void move(int x, int y) {
		System.out.println("길이 : " + rectangle.width);
		this.rectangle.setFrame(x, y, rectangle.width, rectangle.height);
	}

	public void resize(int x, int y) {
		double ox = rectangle.getX(); // original x
		double oy = rectangle.getY(); // original y
		double w = x - ox;
		double h = y - oy;
		this.rectangle.setFrame(rectangle.getX(), rectangle.getY(), w, h);
	}
}
