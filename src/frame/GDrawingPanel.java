package frame;

import global.GConstants;
import shapes.GGroup;
import shapes.GShape;
import transformers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serial;
import java.util.Vector;

public class GDrawingPanel extends JPanel {


    @Serial
    private static final long serialVersionUID = 1L;

    // 드로잉 상태
    private Vector<GShape> shapes;
    private GTransformer transformer;
    // 제약조건
    private GConstants.EShapeTool eShapeTool;
    private EDrawingState eDrawingState;
    private GShape currentShape;
    private GShape selectedShape;
    // 저장상태 확인
    private boolean bUpdated;






    public GDrawingPanel() {
        this.setBackground(Color.WHITE);

        MouseEventHandler mouseHandler = new MouseEventHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        this.currentShape = null; // default (명시적으로)
        this.selectedShape = null;
        this.shapes = new Vector<GShape>();
        this.eShapeTool = null;
        this.eDrawingState = EDrawingState.eIdle;
        this.bUpdated = false;
    }

    public enum EDrawingState {
        eIdle,
        e2P,
        eNP
    }
    public void initialize() {
        this.shapes.clear();
        this.repaint();
    }





    // getter, setter
    public Vector<GShape> getShapes() {
        return shapes;
    }
    public boolean isUpdated() {
        return this.bUpdated;
    }

    public void setbUpdated(boolean bUpdated) {
        this.bUpdated = bUpdated;
    }
    public void seteShapeTool(GConstants.EShapeTool eShapeTool) {
        this.eShapeTool = eShapeTool;
    }
    public void setEShapeType(GConstants.EShapeTool eShapeTool) {
        this.eShapeTool = eShapeTool;
    }
    public void setShapes(Vector<GShape> shapes) {
        this.shapes = shapes;
    }
    public void setShapes(Object shapes) {
        // TODO object 를 getclass() 해서 타입에 맞는지 검증하거나 아니면 추론해서 더할 수 있음
        // 워닝을 없애도록 하면 추가 점수
        Vector<GShape> vector = (Vector<GShape>) shapes;
        this.shapes = vector;
        this.repaint();
    }









    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 그린 도형 저장
        for (GShape r : shapes) {
            r.draw((Graphics2D) g);
        }
    }

    private GShape onShape(int x, int y) {
        for (GShape g : shapes) {
            if (g.contains(x, y)) {
                return g;
            }
        }
        return null;
    }

    // TODO (예외처리 newShape() 안으로 옮기기, 아니면 처리하기)
    private boolean startTransform(int x, int y) throws Exception {
        currentShape = eShapeTool.newShape();
        this.shapes.add(currentShape);

        if (this.eShapeTool == GConstants.EShapeTool.eSelect) {
            this.selectedShape = onShape(x, y);
            if (selectedShape == null) {
                this.transformer = new GDrawer(this.currentShape); // 현재 (그려지기전) 도형
            } else if (this.selectedShape.getESelectedAnchor() == GConstants.EAnchor.eMM) {
                this.transformer = new GMover(this.selectedShape); // 현재 (선택된) 도형
            } else if (this.selectedShape.getESelectedAnchor() == GConstants.EAnchor.eRR) {
                this.transformer = new GRotate(this.selectedShape); // 현재 (선택된) 도형
            } else {
                this.transformer = new GResizer(this.selectedShape); // 현재 (선택된) 도형
            }
        } else {
            transformer = new GDrawer(currentShape);
        }

        return transformer.start((Graphics2D) getGraphics(), x, y);
    }

    private void keepTransform(int x, int y) {
        transformer.drag((Graphics2D) getGraphics(), x, y);
        this.repaint();
    }


    private void finishTransform(int x, int y) {
        transformer.finish((Graphics2D) getGraphics(), x, y);
        selectedShape(this.currentShape);

        if (this.eShapeTool == GConstants.EShapeTool.eSelect) {
            this.shapes.removeLast();
            for (GShape shape : this.shapes) {
                if (this.currentShape.contains(shape)) {
                    shape.setSelected(true);
                } else {
                    shape.setSelected(false);
                }
            }
        }
        this.bUpdated = true;
        this.repaint();

        // TODO 좌표까지 확인하면서 좀 더 섬세하게 할 수도 있음 (transform 안에서 구현해야 되는데, 각 구현체마다 로직이 다르겠지)
//        this.bUpdated = this.transformer.isUpdate();
//        if (bUpdated) {
//            this.repaint();
//        }
    }

    // 현재빼고 다 선택 취소
    private void selectedShape(GShape gShape) {
        for (GShape s : this.shapes) {
            s.setSelected(false);
        }
        this.currentShape.setSelected(true);
    }

    private void addPoint(int x, int y) {
        transformer.addPoint((Graphics2D) getGraphics(), x, y);
    }

    private void changeCursor(int x, int y) {
        if (this.eShapeTool != GConstants.EShapeTool.eSelect) return;

        this.selectedShape = onShape(x, y);

        if (this.selectedShape == null) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            GConstants.EAnchor eAnchor = this.selectedShape.getESelectedAnchor();
            this.setCursor(eAnchor.getCursor());
        }
    }

    public void groupSelectedShapes() {
        java.util.List<GShape> selected = new java.util.ArrayList<>();
        for (GShape shape : this.shapes) {
            if (shape.isSelected()) {
                selected.add(shape);
            }
        }
        if (selected.size() <= 1) return;

        GGroup group = new GGroup();
        for (GShape shape : selected) {
            group.add(shape);
        }

        this.shapes.removeAll(selected);
        this.shapes.add(group);
        group.setSelected(true);
        this.repaint();
    }


    private class MouseEventHandler implements MouseListener, MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                if (e.getClickCount() == 1) {
                    this.mouse1Click(e);
                } else if (e.getClickCount() == 2) {
                    this.mouse2Click(e);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("에러임 어디서부터인지 ㅅㄱ");
            }
        }

        // TODO : 클릭하면 하나 선택되게 니네드리 알아서 해라...
        private void mouse1Click(MouseEvent e) throws Exception {
            if (SwingUtilities.isRightMouseButton(e)) {
                // 선택된 도형 수가 2개 이상일 때만 그룹화
                int selectedCount = 0;
                for (GShape shape : shapes) {
                    if (shape.isSelected()) selectedCount++;
                }
                if (selectedCount >= 2) {
                    groupSelectedShapes();
                    return; // 더 이상 다른 동작 없음
                }
            }

            // 안 그리는 상태
            if (eDrawingState == EDrawingState.eIdle) {
                // 점 두개인 경우
                if (eShapeTool.getDrawingType() == GShape.EPoints.e2P) { // constraint : set Transformer (locate, scale, ... 구분하기 위함)
                    // 선택인 경우
                    if (startTransform(e.getX(), e.getY())) {
                        eDrawingState = EDrawingState.e2P;
                    } else {
                        eDrawingState = EDrawingState.eIdle;
                    }

                    // 점 n개인 경우
                } else if (eShapeTool.getDrawingType() == GShape.EPoints.eNP) {
                    if (startTransform(e.getX(), e.getY())) {
                        eDrawingState = EDrawingState.eNP;
                    } else {
                        eDrawingState = EDrawingState.eIdle;
                    }
                }
                // 그리는 상태 - 점 2개(끝)
            } else if (eDrawingState == EDrawingState.e2P) {
                finishTransform(e.getX(), e.getY());
                eDrawingState = EDrawingState.eIdle;
                // 그리는 상태 - 점 n개(점추가)
            } else if (eDrawingState == EDrawingState.eNP) {
                addPoint(e.getX(), e.getY());
            }
        }

        private void mouse2Click(MouseEvent e) {
            if (eDrawingState == EDrawingState.eNP) {
                finishTransform(e.getX(), e.getY());
                eDrawingState = EDrawingState.eIdle;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (eDrawingState == EDrawingState.e2P) {
                keepTransform(e.getX(), e.getY());
            } else if (eDrawingState == EDrawingState.eNP) {
                keepTransform(e.getX(), e.getY());
            } else if (eDrawingState == EDrawingState.eIdle) { // 그냥 else 라고 해도 되는데, 명시적으로
                changeCursor(e.getX(), e.getY());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}