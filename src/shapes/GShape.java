package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;


public abstract class GShape {
	protected Shape shape;

	public GShape() {}

	public void draw(Graphics2D graphics2d) {
		graphics2d.draw(shape);
	}

	public abstract void move(int x, int y);
	public abstract void resize(int x, int y);
}
