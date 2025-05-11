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


	// TODO  이런 코드는 원래 다 transform 에 있어야 함 추후 변경할 것
	private int px, py;
	@Override
	public void movePoint(int x, int y) {
		int dx = x - px;
		int dy = y - py;

		for (int i=0; i<this.polygon.npoints; i++) {
			this.polygon.xpoints[i] += dx;
			this.polygon.ypoints[i] += dy;
		}

		this.px = x;
		this.py = y;
	}

	@Override
	public void setMovePoint(int x, int y) {

		this.px = x;
		this.py = y;
	}

}
