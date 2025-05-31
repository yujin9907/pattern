package shapes;

import global.GConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class GGroup extends GShape {
    private List<GShape> children;
    private AffineTransform affineTransform;

    public GGroup() {
        super(new Rectangle()); // 더미 shape (getBounds 기반 처리)
        this.children = new ArrayList<>();
        this.affineTransform = new AffineTransform();
    }

    public void add(GShape shape) {
        this.children.add(shape);
        shape.setSelected(false); // 내부 도형은 선택 해제
    }

    public List<GShape> getChildren() {
        return this.children;
    }

    @Override
    public void draw(Graphics2D g2) {
        AffineTransform save = g2.getTransform();
        g2.transform(this.affineTransform);
        for (GShape shape : children) {
            shape.draw(g2);
        }
        g2.setTransform(save);

        if (this.isSelected()) {
            drawAnchors(g2); // 그룹 외부 앵커 표시
        }
    }

    @Override
    public void drawAnchors(Graphics2D g2) {
        if (!this.isSelected()) return;

        Rectangle bounds = this.getBounds();  // 그룹 전체 바운딩 박스
        int bx = bounds.x;
        int by = bounds.y;
        int bw = bounds.width;
        int bh = bounds.height;

        int cx = 0, cy = 0;

        for (int i = 0; i < GConstants.EAnchor.values().length - 1; i++) {
            GConstants.EAnchor anchor = GConstants.EAnchor.values()[i];

            switch (anchor) {
                case eNN: cx = bx + bw / 2; cy = by; break;
                case eNE: cx = bx + bw;     cy = by; break;
                case eNW: cx = bx;          cy = by; break;

                case eSS: cx = bx + bw / 2; cy = by + bh; break;
                case eSE: cx = bx + bw;     cy = by + bh; break;
                case eSW: cx = bx;          cy = by + bh; break;

                case eEE: cx = bx + bw;     cy = by + bh / 2; break;
                case eWW: cx = bx;          cy = by + bh / 2; break;

                case eRR: cx = bx + bw / 2; cy = by - 30; break; // 회전용 점
            }

            Ellipse2D anchorCircle = new Ellipse2D.Double(cx - 5, cy - 5, 10, 10);
            g2.setColor(Color.WHITE);
            g2.fill(anchorCircle);
            g2.setColor(Color.BLACK);
            g2.draw(anchorCircle);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        try {
            AffineTransform inverse = this.affineTransform.createInverse();
            Point p = (Point) inverse.transform(new Point(x, y), new Point());
            for (GShape shape : children) {
                if (shape.contains(p.x, p.y)) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Rectangle getBounds() {
//        if (children.isEmpty()) {
//            return new Rectangle();
//        }
        Rectangle bounds = children.getFirst().getBounds();
        for (int i = 1; i < children.size(); i++) {
            bounds = bounds.union(children.get(i).getBounds());
        }
        return affineTransform.createTransformedShape(bounds).getBounds();
    }

    @Override
    public Shape getShape() {
        return new Rectangle(getBounds());
    }

    @Override
    public void movePoint(int dx, int dy) {
        this.affineTransform.translate(dx, dy);
    }

    @Override
    public void setMovePoint(int x, int y) {

    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        for (GShape shape : children) {
            shape.setSelected(false); // 내부 도형은 선택 상태 유지하지 않음
        }
    }

    @Override
    public GConstants.EAnchor getESelectedAnchor() {
        if (!isSelected()) return GConstants.EAnchor.eDEFAULT;
        return GConstants.EAnchor.eMM; // 항상 전체 기준 앵커만 사용
    }

    @Override
    public AffineTransform getAffineTransform() {
        return this.affineTransform;
    }

    // 불필요한 추상 메서드 구현 (사용되지 않음)
    @Override public void setPoint(int x, int y) {}
    @Override public void addPoint(int x, int y) {}
    @Override public void dragPoint(int x, int y) {}
}