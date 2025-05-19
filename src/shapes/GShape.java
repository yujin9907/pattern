package shapes;

import global.GConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;


public abstract class GShape {
	private final static int ANCHOR_W = 10;
	private final static int ANCHOR_H = 10;
	public enum EPoints {
		e2P,
		eNP,
	}



	private Shape shape;
	private Ellipse2D anchors[];
	private boolean bSelected;
	private GConstants.EAnchor eSelectedAnchor; // 선택된(contains) 앵커
	private AffineTransform affineTransform;
	 private Shape transformedAnchor;

	public GShape(Shape shape) {
		this.shape = shape;
		this.anchors =  new Ellipse2D[GConstants.EAnchor.values().length-1];
		this.affineTransform = new AffineTransform();


		// 앵커 동그라미 8개 생성
		for (int i=0; i<this.anchors.length; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}

		this.bSelected = false;
		this.eSelectedAnchor = null;
	}

	// getter, setter
	protected Shape getShape() {
		return shape;
	}

	public AffineTransform getAffineTransform() {
		return affineTransform;
	}
	public Rectangle getBounds() {
		return this.shape.getBounds();
	}
	public Shape getTransformedShape() {
		return this.affineTransform.createTransformedShape(this.shape);
	}


	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public GConstants.EAnchor getESelectedAnchor() {
		return eSelectedAnchor;
	}

	// method
	private void setAnchors() {
		// 선택된 도형을 둘러싼 사각형
		Rectangle bounds = this.shape.getBounds();
		// 앵커 동그라미 좌표
		int bx = bounds.x;
		int by = bounds.y;
		int bw = bounds.width;
		int bh = bounds.height;

		int cx=0;
		int cy=0; // ㄹㅇ 원점

		// 앵커의 좌표 그리기 (앵커 동그라미의 중심점)
		for (int i=0; i<this.anchors.length; i++) {
			switch (GConstants.EAnchor.values()[i]) {
				case eSS: cx = bx+bw/2; cy = by+bh; break;
				case eSE: cx = bx+bw;   cy = by+bh; break;
				case eSW: cx = bx;      cy = by+bh; break;

				case eNN: cx = bx+bw/2; cy = by;break;
				case eNE: cx = bx+bw;   cy = by; break;
				case eNW: cx = bx;      cy = by; break;

				case eEE: cx = bx+bw;   cy = by+bh/2;break;
				case eWW: cx = bx;      cy = by+bh/2; break;
				case eRR: cx = bx+bw/2; cy = by - 30; break;
				default: break;
			}
			anchors[i].setFrame(cx-ANCHOR_W/2, cy-ANCHOR_H/2, ANCHOR_W, ANCHOR_H);
		}
	}
	public void draw(Graphics2D graphics2d) {
		Shape transformedShape = this.affineTransform.createTransformedShape(shape); // affine transform 을 적용 한 새 도형을
		graphics2d.draw(transformedShape);

		// draw Anchor
		if (bSelected) {
			this.setAnchors();
			for (int i=0; i<this.anchors.length; i++) {
				Shape transformedAnchor = this.affineTransform.createTransformedShape(anchors[i]);
				Color penColor = graphics2d.getColor();
				graphics2d.setColor(graphics2d.getBackground());
				graphics2d.fill(transformedAnchor);
				graphics2d.setColor(penColor); // 원위치
				graphics2d.draw(transformedAnchor);
			}
		}
	}

	// anchor는 shape 이 선택되었는지
	public boolean contains(int x, int y) {
		// 앵커 선택
		if (bSelected) {
			for (int i=0; i<this.anchors.length; i++) {
				Shape transformedAnchor = this.affineTransform.createTransformedShape(anchors[i]);
				if (transformedAnchor.contains(x, y)) {
					this.eSelectedAnchor = GConstants.EAnchor.values()[i];
					return true;
				}
			}
		}
		// 무브
		Shape transformedAnchor = this.affineTransform.createTransformedShape(shape);
		if (transformedAnchor.contains(x, y)) {
			this.eSelectedAnchor = GConstants.EAnchor.eMM; // move
			return true;
		}
		// 아니면 false
		return false;
	}
	public boolean contains(GShape shape) {
		return this.shape.contains(shape.getShape().getBounds());
	}


	public abstract void setPoint(int x, int y);
	public abstract void addPoint(int x, int y);
	public abstract void dragPoint(int x, int y);


	public abstract void movePoint(int x, int y);
	public abstract void setMovePoint(int x, int y);

}
