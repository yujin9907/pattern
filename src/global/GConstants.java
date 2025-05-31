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


    // TODO 5.26 이거 mehtod name string 으로 하지 말고 .class 한 거 처럼 함수 이름으로 고쳐 써라
    public enum EFileMenuItem {
        eNew("새파일", "newPanel"),
        eOpen("열기", "open"),
        eSave("저장", "save"),
        eSaveAs("다른 이름으로 저장", "saveAs"),
        ePrint("프린트", "print"),
        eQuit("종료", "quit");

        private String name;
        private String methodName;
        EFileMenuItem(String name, String methodName) {
            this.name = name;
            this.methodName = methodName;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getName() {
            return name;
        }
    }

}
