// repaint() 시 저장 용도 
package shapes;



import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

public class GPolygon extends GShape {


	private Polygon polygon;


	public GPolygon() {
		super(new Polygon());
		this.polygon = (Polygon) this.getShape();
	}

	// 폴리곤 시작은 2개의 점을 추가함
	public void setPoint(int x, int y) {
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
	}

	public void dragPoint(int x, int y) {
		this.polygon.xpoints[this.polygon.npoints-1] = x; // npoints = 현재 점의 개수
		this.polygon.ypoints[this.polygon.npoints-1] = y;
	}

	@Override
	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);
	}
}
