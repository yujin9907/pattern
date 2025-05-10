package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;



public abstract class GShape {
	public enum EPoints {
		e2P,
		eNP
	}

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

	public boolean contains(int x, int y) {
		return this.shape.contains(x, y);
	}

	// TODO : 추후 resize, move 합칠거임

	public abstract void setPoint(int x, int y);
	public abstract void addPoint(int x, int y);
	public abstract void dragPoint(int x, int y);


	public abstract void movePoint(int x, int y);
	public abstract void setMovePoint(int x, int y);
}
