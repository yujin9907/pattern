// repaint() 시 저장 용도 
package shapes;



import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

public class GRectangle extends GShape {


	private Rectangle2D rectangle; // x1, x2,.. 와 같은 역할
	
	
	public GRectangle() {
		super(new Rectangle2D.Float(0,0,0,0));
		this.rectangle = (Rectangle2D) this.getShape();
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
	@Override
	public void addPoint(int x, int y) {

	}

	private int px, py;
	@Override
	public void movePoint(int x, int y) {
		int dx = x - px;
		int dy = y - py;

		this.rectangle.setFrame(rectangle.getX()+dx, rectangle.getY()+dy, rectangle.getWidth(), rectangle.getHeight());

		this.px = x;
		this.py = y;
	}

	@Override
	public void setMovePoint(int x, int y) {
		this.px = x;
		this.py = y;
	}
}
