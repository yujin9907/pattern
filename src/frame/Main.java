package frame;

import javax.swing.JFrame;

public class Main {
	
	// 4.21 중간고사 직후 리뷰 롤백하기

	public static void main(String[] args) {
		// create aggregation hierarchy
		GMainFrame main = new GMainFrame();
		
		// tree traverse (DFS) : 자식 핸들러에게까지 분산됨 (최종 이벤트핸들러가 달린 자식이 호출되도록 함)
		main.initialize();
//		while(true) {
//			Event e = getEvent();
//			for (JCompoenet frame: this.components) { // (JFrame f : this.jframe) 최상단은 결국 JComponent 로 되게 됨
//				if (frame.contains(e.getPoint())) {
//					frame.processEvent(e);
//				}
//			}
//		}
	}
}
