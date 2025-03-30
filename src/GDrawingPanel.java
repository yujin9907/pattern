import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.Shape;

public class GDrawingPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private String shape; // 라디오 버튼 선택 도형
    private Rectangle bounds; // 도형 위치 및 크기
    private boolean dragging = false;
    private int offsetX, offsetY;
    private Point startPoint, endPoint;
    
    public void initialize() {
    	
    }
    
    public void initialize(String shape) {
        this.shape = shape;
        bounds = null;
        repaint();
    }
    
    public GDrawingPanel() {
        this.setBackground(Color.WHITE);
        
        // 이벤트 추가
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (bounds != null && bounds.contains(e.getPoint())) {
                    // 도형 내부 -> 이동
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        dragging = true;
                        offsetX = e.getX() - bounds.x;
                        offsetY = e.getY() - bounds.y;
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        // 우클릭
                        showPopupMenu(e);
                    }
                } else {
                    // 도형 외부 -> 새로운 도형 그리기
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        startPoint = e.getPoint();
                        endPoint = startPoint;
                    }
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
            	if (SwingUtilities.isRightMouseButton(e)) return;
            	
                if (!dragging && startPoint != null) {
                    endPoint = e.getPoint();
                    updateBounds(); // 도형 범위 설정
                }
                dragging = false;
                repaint();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && bounds != null) {
                    // 이동
                    bounds.setLocation(e.getX() - offsetX, e.getY() - offsetY);
                } else if (startPoint != null) {
                    // 새 도형 그리기
                    endPoint = e.getPoint();
                    updateBounds();
                }
                repaint();
            }
        });
    }
    

    private void updateBounds() {
        if (startPoint != null && endPoint != null) {
            int x = Math.min(startPoint.x, endPoint.x);
            int y = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(startPoint.x - endPoint.x);
            int height = Math.abs(startPoint.y - endPoint.y);
            bounds = new Rectangle(x, y, width, height);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bounds != null) {
            draw(g);
        }
    }
    
    private void draw(Graphics g) {
        if (shape == null || shape.equals("")) return;

        Shape s = Shape.of(this.shape);
        switch (s) {
            case Rect -> g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
            case Oval -> g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
            case Tri -> {
                int[] xPoints = {
                    bounds.x + bounds.width / 2, 
                    bounds.x + bounds.width, 
                    bounds.x
                };
                int[] yPoints = {
                    bounds.y, 
                    bounds.y + bounds.height, 
                    bounds.y + bounds.height
                };
                g.drawPolygon(xPoints, yPoints, 3);
            }
            case Poly -> {
                int[] xPoly = {
                    bounds.x + bounds.width / 4, 
                    bounds.x + bounds.width * 3 / 4, 
                    bounds.x + bounds.width, 
                    bounds.x + bounds.width / 2, 
                    bounds.x
                };
                int[] yPoly = {
                    bounds.y, 
                    bounds.y, 
                    bounds.y + bounds.height / 2, 
                    bounds.y + bounds.height, 
                    bounds.y + bounds.height / 2
                };
                g.drawPolygon(xPoly, yPoly, 5);
            }
            case Text -> g.drawString("Text Box", bounds.x + 10, bounds.y + 20);
            default -> throw new IllegalArgumentException("Unexpected value: " + shape);
        }
    }

    // Move / Resize / Rotate 팝업
    private void showPopupMenu(MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem rotate = new JMenuItem("회전");
        JMenuItem resize = new JMenuItem("리사이즈");
        
        rotate.addActionListener(event -> rotateShape());

        
        popup.add(rotate);
        popup.add(resize);

        popup.show(this, e.getX(), e.getY());
    }
    
    // 90도 회전 처리
    private void rotateShape() {
        if (bounds != null) {
            int temp = bounds.width;
            bounds.width = bounds.height;
            bounds.height = temp;
            repaint();
        }
    }
    
}