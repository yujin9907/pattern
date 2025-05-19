package transformers;

import shapes.GShape;

import java.awt.*;


// 이제 더블버퍼링으로 그림
public class GMover extends GTransformer {
	protected GShape shape;
	private int px, py;



	public GMover(GShape shape) {
		super(shape);
		this.shape = shape;
	}

	@Override
	public void start(Graphics2D graphis2D, int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void drag(Graphics2D graphis2D, int x, int y) {
		int dx = x - px;
		int dy = y - py;

		this.shape.getAffineTransform().translate(dx, dy);

		this.px = x;
		this.py = y;
	}

	@Override
	public void finish(Graphics2D graphis2D, int x, int y) {}

	@Override
	public void addPoint(Graphics2D graphis2D, int x, int y) {
	}
}
