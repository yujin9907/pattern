package panel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.Shape;

public class GDrawingPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    
    /* 
     * 이벤트 핸들러를 별도 파일로 분리했습니다.
     */
    
    private String shape; // 라디오 버튼 선택 도형
    private MouseEventHandler mouseHandler;

    
    public GDrawingPanel() {
        this.setBackground(Color.WHITE);
        
        // 이벤트 핸들러 등록
        mouseHandler = new MouseEventHandler(this);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }
    
    
    public void initialize() {
    	mouseHandler.initialize();
    }
    
    public void initialize(String shape) {
        this.shape = shape;
        repaint();
    	mouseHandler.initialize();
    }
    

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    public void draw(int x, int y, int w, int h) {
        Graphics2D g = (Graphics2D) this.getGraphics(); // 2차원 도형 전용 Graphics 확장 클래스
        g.setXORMode(getBackground()); // xor 모드 (일부 지우기)
        
        
        // 이전 과제 코드
        if (shape == null || shape.isEmpty()) return;

        Shape s = Shape.of(this.shape);
        switch (s) {
            case Rect -> g.drawRect(x, y, w, h);
            case Oval -> g.drawOval(x, y, w, h);
            case Tri -> {
                int[] xPoints = {
                    x + w / 2, 
                    x + w, 
                    x
                };
                int[] yPoints = {
                    y, 
                    y + h,
                    y + h
                };
                g.drawPolygon(xPoints, yPoints, 3);
            }
            case Poly -> {
                int[] xPoly = {
                    x + w / 4, 
                    x + w * 3 / 4, 
                    x + w, 
                    x + w / 2, 
                    x
                };
                int[] yPoly = {
                    y, 
                    y, 
                    y + h / 2, 
                    y + h, 
                    y + h / 2
                };
                g.drawPolygon(xPoly, yPoly, 5);
            }
            case Text -> g.drawString("Text Box", x + 10, y + 20);
            default -> throw new IllegalArgumentException("Unexpected value: " + shape);
        }
    }
}