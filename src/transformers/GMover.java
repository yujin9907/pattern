package transformers;

import shapes.GShape;

import java.awt.*;


// 이제 더블버퍼링으로 그림
public class GMover extends GTransformer {
	protected GShape shape;


	public GMover(GShape shape) {
		super(shape);
		this.shape = shape;
	}

	@Override
	public void start(Graphics2D graphis2D, int x, int y) {
		shape.setMovePoint(x, y);
	}

	@Override
	public void drag(Graphics2D graphis2D, int x, int y) {
		shape.movePoint(x, y);
	}

	@Override
	public void finish(Graphics2D graphis2D, int x, int y) {}

	@Override
	public void addPoint(Graphics2D graphis2D, int x, int y) {
	}
}
