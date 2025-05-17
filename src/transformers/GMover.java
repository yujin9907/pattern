package transformers;

import shapes.GShape;

import java.awt.*;


// 이제 더블버퍼링으로 그림
public class GMover extends GTransformer {
	protected GShape shape;
	private int px, py;


	public GShape getShape() {
		return shape;
	}

	public GMover(GShape shape) {
		super(shape);
		this.shape = shape;
	}

	@Override
	public void start(Graphics2D graphis2D, int x, int y) {
		if (shape.isSelected()) {
			this.px = x;
			this.py = y;
		} else {
			this.shape.drawAnchor(graphis2D);
		}
	}

	@Override
	public void drag(Graphics2D graphis2D, int x, int y) {
		if (!this.shape.isSelected()) return;

		int dx = x - px;
		int dy = y - py;

		this.shape.translate(dx, dy, graphis2D);

		this.px = x;
		this.py = y;
	}

	@Override
	public void finish(Graphics2D graphis2D, int x, int y) {
		if (!this.shape.isSelected()) {
			this.shape.setSelected(true);
		}
	}

	@Override
	public void addPoint(Graphics2D graphis2D, int x, int y) {
	}
}
