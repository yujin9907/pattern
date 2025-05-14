/* 04.07 이벤트를 추상화시키는 용도

drawingpanel 에 event 를 넣은 이유? drawing panel 용 이벤트라서.
이벤트에 필요한 모든 정보가 컴포지션 (트리) 구조 내 전부 가지고 있기 때문에 직접 붙임.
이벤트 대상인 도형은 다시 이벤트 원인인 drawingPanel 에 그려져야 함 -> Graphics (그리기도구) 가 모든 정보를 가지고 있음.

-> 즉, 이러한 이벤트를 transformer 한테 추상화하려면 그림을 그릴 수 잇는 정보를 일반화하여 전달해야함
1) x, y 위치
2) Graphics

 */
package transformers;



import java.awt.Graphics2D;

import shapes.GRectangle;
import shapes.GShape;

public abstract class GTransformer {


	protected GShape gShape;

	public GTransformer(GShape gShape) {
		this.gShape = gShape;
	}

	public abstract void start(Graphics2D graphis2D, int x, int y);

	public abstract void drag(Graphics2D graphis2D, int x, int y) ;

	public abstract void finish(Graphics2D graphis2D, int x, int y) ;

	public abstract void addPoint(Graphics2D graphis2D, int x, int y) ;

	

}
