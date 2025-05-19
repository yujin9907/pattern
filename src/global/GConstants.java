package global;

import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GConstants {

    public final class GMainFrame {
        public static final int x = 100;
        public static final int y = 200;
        public static final int w = 600;
        public static final int h = 400;
    }

    public enum EAnchor { // 8개
        eNN(new Cursor(Cursor.N_RESIZE_CURSOR)), // 남북
        eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)), // 남서
        eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
        eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
        eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
        eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
        eEE(new Cursor(Cursor.E_RESIZE_CURSOR)), // 정동
        eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
        eRR(new Cursor(Cursor.HAND_CURSOR)), // TODO 추후 로테이트 커서 커스텀하기
        eMM(new Cursor(Cursor.MOVE_CURSOR));

        private Cursor cursor;
        private EAnchor(Cursor cursor) {
            this.cursor = cursor;
        }

        public Cursor getCursor() {
            return cursor;
        }
    }

    public enum EShapeTool {
        // TODO : 상수 코드에다 쓰지말고 나중에 파일 따로 빼라 (constant 나 resource 로)
        eSelect("select", GShape.EPoints.e2P, GRectangle.class),
        eRectangle("rectangle", GShape.EPoints.e2P, GRectangle.class),
        eEllipse("ellipse", GShape.EPoints.e2P, GRectangle.class),
        eLine("line", GShape.EPoints.e2P, GRectangle.class),
        ePolygon("polygon", GShape.EPoints.eNP, GPolygon.class);

        private String name;
        private GShape.EPoints drawingType;
        private Class<? extends GShape> classShape;
        EShapeTool(String name, GShape.EPoints drawingType, Class<? extends GShape> gShape) {
            this.name = name;
            this.drawingType = drawingType;
            this.classShape = gShape;
        }
        public String getName() {
            return this.name;
        }

        public GShape.EPoints getDrawingType() {
            return this.drawingType;
        }

        // TODO 익셉션처리 추후 aspect 로 처리할 예정 : 익셉션처리 아주 중요하다 마구잡이로 던지면 안 된다 이렇게
        public GShape newShape() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            return classShape.getConstructor().newInstance();
        }
    }
}
