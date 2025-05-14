package shapes;

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

	public enum EAnchor { // 8개
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)), // 남북
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)), // 남서
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)), // 정동
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eRR(new Cursor(Cursor.HAND_CURSOR)), // TODO 추후 로테이트 커서 커스텀하기
		eMM(new Cursor(Cursor.MOVE_CURSOR));

		private Cursor cursor;
		private EAnchor(Cursor cursor) {
			this.cursor = cursor;
		}

		public Cursor getCursor() {
			return cursor;
		}
	}

	private Shape shape;
	private Ellipse2D anchors[];
	private boolean bSelected;
	private EAnchor eSelectedAnchor; // 선택된(contains) 앵커
	private AffineTransform affineTransform;



	public GShape(Shape shape) {
		this.shape = shape;
		this.anchors =  new Ellipse2D[EAnchor.values().length-1];
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
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public EAnchor getESelectedAnchor() {
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
			switch (EAnchor.values()[i]) {
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
				Color penColor = graphics2d.getColor();
				graphics2d.setColor(graphics2d.getBackground());
				graphics2d.fill(anchors[i]);
				graphics2d.setColor(penColor); // 원위치
				graphics2d.draw(anchors[i]);
			}
		}
	}


	// anchor는 shape 이 선택되었는지
	public boolean contains(int x, int y) {
		// 앵커 선택
		if (bSelected) {
			for (int i=0; i<this.anchors.length; i++) {
				if (anchors[i].contains(x, y)) {
					this.eSelectedAnchor = EAnchor.values()[i];

					if (this.shape.getBounds().getMaxY() < anchors[i].getMinY()) {
						this.eSelectedAnchor = EAnchor.eRR; // lotate
					}

					return true;
				}
			}
		}
		// 무브
		if (this.shape.contains(x, y)) {
			this.eSelectedAnchor = EAnchor.eMM; // move
			return true;
		}
		// 아니면 false
		return false;
	}

	// TODO : 추후 resize, move 합칠거임

	public abstract void setPoint(int x, int y);
	public abstract void addPoint(int x, int y);
	public abstract void dragPoint(int x, int y);


//	public abstract void movePoint(int x, int y);
//	public abstract void setMovePoint(int x, int y);
	public void movePoint(int x, int y) {

	}

	public void setMovePoint(int x, int y) {

	}

	public void translate(int tx, int ty) {
		if (eSelectedAnchor != EAnchor.eRR) {
			resize(tx, ty);
		} else {
			this.affineTransform.translate(tx, ty);
		}
	}

//	public abstract void resize(int dx, int dy);

	public void resize(int dx, int dy) {
		Rectangle bounds = this.shape.getBounds();

		// 기존 크기가 0인 경우 나눗셈 방지
		double originalWidth = bounds.getWidth();
		double originalHeight = bounds.getHeight();

		if (originalWidth == 0 || originalHeight == 0) return;

		// 비율 계산
		double sx = (originalWidth + dx) / originalWidth;
		double sy = (originalHeight + dy) / originalHeight;

		// 중심점 기준으로 스케일
		AffineTransform transform = new AffineTransform();
		transform.translate(bounds.getX(), bounds.getY());
		transform.scale(sx, sy);
		transform.translate(-bounds.getX(), -bounds.getY());

		this.affineTransform.concatenate(transform);	}
}
