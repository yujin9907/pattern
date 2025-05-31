package shapes;

import global.GConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

// TODO 5.26 저장을 위해 파일스트림으로 저장하기 위해 시리얼라이즈에이블 해야 함
public abstract class GShape implements Serializable {
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
        this.anchors = new Ellipse2D[GConstants.EAnchor.values().length - 1];
        this.affineTransform = new AffineTransform();


        // 앵커 동그라미 8개 생성
        for (int i = 0; i < this.anchors.length; i++) {
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
    public void translate(int x, int y) {
        this.getAffineTransform().translate(x, y);
    }

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
			drawAnchors(graphics2d);
		}
	}
	public void drawAnchors(Graphics2D graphics2d) {
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
			if (this.isSelected()) {
				this.eSelectedAnchor = GConstants.EAnchor.eMM; // move
			} else {
				this.eSelectedAnchor = GConstants.EAnchor.eDEFAULT;
			}
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

//    public void translate(int tx, int ty, Graphics2D graphics2D) {
//        if (eSelectedAnchor != GConstants.EAnchor.eRR && eSelectedAnchor != GConstants.EAnchor.eMM) {
//            resize(tx, ty);
//        } else {
//            AffineTransform t = new AffineTransform();
//            t.translate(tx, ty);
//            this.shape = t.createTransformedShape(this.shape); // 실제 데이터 이동
//
//            this.affineTransform.setToIdentity(); // 렌더링 변환 초기화 (또 적용 안 되게)
//        }
//		drawAnchors(graphics2D);
//    }

    public void resize(int dx, int dy) {
        Rectangle bounds = this.shape.getBounds();

        double w = bounds.getWidth();
        double h = bounds.getHeight();

        if (w == 0 || h == 0) return;

        double sx = 1.0;
        double sy = 1.0;
        double pivotX = bounds.getX();
        double pivotY = bounds.getY();

        // 앵커 기준점 및 축소/확대 방향 설정
        switch (this.eSelectedAnchor) {
            case eSE:
                sx = (w + dx) / w;
                sy = (h + dy) / h;
                pivotX = bounds.getX();
                pivotY = bounds.getY();
                break;

            case eSW:
                sx = (w - dx) / w;
                sy = (h + dy) / h;
                pivotX = bounds.getX() + w;
                pivotY = bounds.getY();
                break;

            case eNE:
                sx = (w + dx) / w;
                sy = (h - dy) / h;
                pivotX = bounds.getX();
                pivotY = bounds.getY() + h;
                break;

            case eNW:
                sx = (w - dx) / w;
                sy = (h - dy) / h;
                pivotX = bounds.getX() + w;
                pivotY = bounds.getY() + h;
                break;

            case eEE:
                sx = (w + dx) / w;
                sy = 1.0;
                pivotX = bounds.getX();
                pivotY = bounds.getY();
                break;

            case eWW:
                sx = (w - dx) / w;
                sy = 1.0;
                pivotX = bounds.getX() + w;
                pivotY = bounds.getY();
                break;

            case eSS:
                sx = 1.0;
                sy = (h + dy) / h;
                pivotX = bounds.getX();
                pivotY = bounds.getY();
                break;

            case eNN:
                sx = 1.0;
                sy = (h - dy) / h;
                pivotX = bounds.getX();
                pivotY = bounds.getY() + h;
                break;

            default:
                return; // 회전 등 무관 앵커
        }

        AffineTransform transform = new AffineTransform();
        transform.translate(pivotX, pivotY);    // 기준점으로 이동
        transform.scale(sx, sy);                // 확대/축소
        transform.translate(-pivotX, -pivotY);  // 다시 원상복귀

        this.shape = transform.createTransformedShape(this.shape);  // 실제 shape에 적용
    }

	public void transform(AffineTransform transform) {
		// 기본 도형에서 필요시 override
	}
}
