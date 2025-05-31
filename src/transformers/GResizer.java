package transformers;

import global.GConstants;
import shapes.GShape;

import java.awt.*;

public class GResizer extends GTransformer {
    protected GShape shape;
    private Rectangle bounds;
    private int px, py;
    private int cx, cy;
    GConstants.EAnchor eResizedAnchor;


    public GResizer(GShape shape) {
        super(shape);
        this.shape = shape;
        this.eResizedAnchor = null;
        px = -1;
        py = -1;
    }

    @Override
    public boolean start(Graphics2D graphis2D, int x, int y) {
        if (!shape.isSelected()) {
            shape.drawAnchors(graphis2D);
            shape.setSelected(true);
            return false;
        }

        this.px = x;
        this.py = y;

        Rectangle r = this.shape.getBounds();

        GConstants.EAnchor eSelectedAnchor = this.shape.getESelectedAnchor();
        switch (eSelectedAnchor) {
            case eNW: eResizedAnchor = GConstants.EAnchor.eSE; cx=r.x+r.width; cy = r.y+r.height; break;
            case eWW: eResizedAnchor = GConstants.EAnchor.eEE; cx=r.x+r.width; cy = r.y+r.height; break;
            case eSW: eResizedAnchor = GConstants.EAnchor.eNE; cx=r.x+r.width; cy = r.y; break;
            case eSS: eResizedAnchor = GConstants.EAnchor.eNW; cx=r.x+r.width/2; cy = r.y; break;
            case eSE: eResizedAnchor = GConstants.EAnchor.eNW; cx=r.x; cy = r.y; break;
            case eEE: eResizedAnchor = GConstants.EAnchor.eWW; cx=r.x; cy = r.y+r.height; break;
            case eNN: eResizedAnchor = GConstants.EAnchor.eWW; cx=r.x+r.width/2; cy = r.y+r.height; break;
            default: break;
        }
        return true;
    }

    // 이걸(그래픽스) 이해하려면, 선형대수부터 이해해야 된다
    // 다만 내부 논리를 모른다 할지라도 라이브러리를 가지고 만들 수 있어야 함
    @Override
    public void drag(Graphics2D graphis2D, int x, int y) {
        if (px == -1 && py == -1) {
            return;
        }

        double dx = 0;
        double dy = 0;

        // TODO 마우스의 움직임 계산
        switch (eResizedAnchor) {
            case eNW: dx = (x-px); dy = (y-py); break;
            case eWW: dx = (x-px); dy = 0; break;
            case eSW: dx = (x-px); dy = -(y-py); break;
            case eSS: dx = 0; dy = -(y-py); break;
            case eSE: dx = -(x-px); dy = -(y-py); break;
            case eEE: dx = -(x-px); dy = 0; break;
            case eNE: dx = -(x-px); dy = (y-py); break;
            case eNN: dx = 0; dy = (y-py); break;
            default: break;
        }

        // 변형된 도형 구하여, 그로부터 계산
        Shape transformedShape = this.shape.getTransformedShape();
        double w1 = transformedShape.getBounds().width;
        double w2 = dx+w1;
        double h1 = transformedShape.getBounds().height;
        double h2 = dy+h1;

        double xScale = w2/w1;
        double yScale = h2/h1;

        this.shape.getAffineTransform().translate(cx, cy);
        this.shape.getAffineTransform().scale(xScale, yScale);
        this.shape.getAffineTransform().translate(-cx, -cy);

        this.px = x;
        this.py = y;
    }

    @Override
    public void finish(Graphics2D graphis2D, int x, int y) {}

    @Override
    public void addPoint(Graphics2D graphis2D, int x, int y) {
    }
}
