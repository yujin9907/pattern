package transformers;

import frame.GDrawingPanel;
import shapes.GRectangle;
import shapes.GShape;

import java.awt.*;


// 이제 더블버퍼링으로 그림
public class GDrawer extends GTransformer {
	protected GShape shape;


	public GDrawer(GShape shape) {
		super(shape);
		this.shape = shape;
	}

	@Override
	public boolean start(Graphics2D graphis2D, int x, int y) {
		shape.setPoint(x, y);
		return true;
	}

	@Override
	public void drag(Graphics2D graphis2D, int x, int y) {
		shape.dragPoint(x, y);
	}

	@Override
	public void finish(Graphics2D graphis2D, int x, int y) {}

	@Override
	public void addPoint(Graphics2D graphis2D, int x, int y) {
		shape.addPoint(x, y);
	}
}
