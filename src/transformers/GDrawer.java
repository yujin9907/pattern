package transformers;

import shapes.GRectangle;
import shapes.GShape;

import java.awt.*;


// 이제 더블버퍼링으로 그림
public class GDrawer extends GTransformer {
	protected GRectangle rectangle;


	public GDrawer(GShape shape) {
		super(shape);
		this.rectangle = (GRectangle) shape;
	}

	@Override
	public void start(Graphics2D graphis2D, int x, int y) {
		rectangle.setPoint(x, y);
		rectangle.dragPoint(x, y);
	}

	@Override
	public void drag(Graphics2D graphis2D, int x, int y) {
		rectangle.dragPoint(x, y);
	}

	@Override
	public void finish(Graphics2D graphis2D, int x, int y) {}
}
