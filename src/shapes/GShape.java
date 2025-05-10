package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;



public abstract class GShape {
	private Shape shape;

	public GShape(Shape shape) {
		this.shape = shape;
	}

	protected Shape getShape() {
		return shape;
	}

	public void draw(Graphics2D graphics2d) {
		graphics2d.draw(shape);
	}

	public abstract void setPoint(int x, int y);
	public abstract void addPoint(int x, int y);
	public abstract void dragPoint(int x, int y);
}
