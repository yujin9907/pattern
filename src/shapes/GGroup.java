
package shapes;

import global.GConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;


public class GGroup extends GShape {
    private List<GShape> children;
    private AffineTransform affineTransform;

    public GGroup() {
        super(new Rectangle()); // 그룹도 shape를 가지도록 설정
        this.children = new ArrayList<>();
        this.affineTransform = new AffineTransform();
    }

    public void add(GShape shape) {
        children.add(shape);
    }

    @Override
    public void setPoint(int x, int y) {

    }

    @Override
    public void addPoint(int x, int y) {

    }

    @Override
    public void dragPoint(int x, int y) {

    }

    @Override
    public void movePoint(int x, int y) {

    }

    @Override
    public void setMovePoint(int x, int y) {

    }
}