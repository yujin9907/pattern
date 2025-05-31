package transformers;

import global.GConstants;
import shapes.GShape;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GRotate extends GTransformer {
    protected GShape shape;
    private Rectangle bounds;
    private int px, py;
    private int cx, cy;
    GConstants.EAnchor eResizedAnchor;


    public GRotate(GShape shape) {
        super(shape);
        this.shape = shape;
        this.bounds = shape.getBounds();
        this.cx = bounds.x + bounds.width / 2;
        this.cy = bounds.y + bounds.height / 2;
    }

    @Override
    public boolean start(Graphics2D graphis2D, int x, int y) {
        this.px = x;
        this.py = y;
        return true;
    }

    @Override
    public void drag(Graphics2D graphis2D, int x, int y) {
        double angle1 = Math.atan2(py - cy, px - cx);
        double angle2 = Math.atan2(y - cy, x - cx);
        double rotationAngle = angle2 - angle1;

        shape.getAffineTransform().rotate(rotationAngle, cx, cy);

        this.px = x;
        this.py = y;
    }

    @Override
    public void finish(Graphics2D graphis2D, int x, int y) {}

    @Override
    public void addPoint(Graphics2D graphis2D, int x, int y) {
    }
}
