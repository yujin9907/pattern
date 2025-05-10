package frame;

public class Main {
	
	public static void main(String[] args) {
		// create aggregation hierarchy
		GMainFrame main = new GMainFrame();
		
		// tree traverse (DFS) : 자식 핸들러에게까지 분산됨 (최종 이벤트핸들러가 달린 자식이 호출되도록 함)
		main.initialize();
	}
}
