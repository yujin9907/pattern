package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;


// 인..

public class GShape {
	protected Shape shape;

	public GShape() {}

	public void draw(Graphics2D graphics2d) {
		graphics2d.draw(shape);
	}

//	public void move() {}
//	public void resize() {}
}
