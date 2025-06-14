package global;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import shapes.GGroup;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GConstants {

    public GConstants() {
    }

    public void readFromFile(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 빌더를 만들 수 있는 팩토리 생성(디자인패턴-팩토리패턴) TODO 읽어보기
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File(fileName);
            Document document = builder.parse(file);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                // xml 파일 기준
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (node.getNodeName().equals(EMainFrame.class.getSimpleName())) {
                        EMainFrame.setValues(node);
                    } else if (node.getNodeName().equals(EMenu.class.getSimpleName())) {
                        EMenu.setValue(node);
                    } else if (node.getNodeName().equals(EFileMenuItem.class.getSimpleName())) {
                        EFileMenuItem.setValues(node);
                    } else if (node.getNodeName().equals(EEditMenuItem.class.getSimpleName())) {
                        EEditMenuItem.setValues(node);
                    } else if (node.getNodeName().equals(EGraphicsMenuItem.class.getSimpleName())) {
                        EGraphicsMenuItem.setValues(node);
                    }
                    else if (node.getNodeName().equals("EToolBarButton")) {
                        EShapeTool.setValues(node);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public enum EMenu {
        eFile(""),
        eEdit(""),
        eGraphics("");

        private String name;

        EMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static void setValue(Node node) {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                String nodeName = child.getNodeName(); // eFileMenu, eEditMenu, eGraphicsMenu

                for (EMenu menu : EMenu.values()) {
                    if (nodeName.equals(menu.getName())) {
                        menu.name = child.getAttributes().getNamedItem("label").getNodeValue();
                    }
                }
            }
        }
    }

    public enum EMainFrame {
        eX(0),
        eY(0),
        eW(0),
        eH(0);

        private int value;

        EMainFrame(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static void setValues(Node node) {
            for (EMainFrame m : EMainFrame.values()) {
                Node attribute = node.getAttributes().getNamedItem(m.name()); // 이게 eX, eY, ... 을 가져온 것
                m.value = Integer.parseInt(attribute.getNodeValue());
            }
        }
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
        eMM(new Cursor(Cursor.MOVE_CURSOR)),
        eDEFAULT(new Cursor(Cursor.DEFAULT_CURSOR));

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
        ePolygon("polygon", GShape.EPoints.eNP, GPolygon.class),
        eGroup("group", GShape.EPoints.e2P, GGroup.class);

        private String name; // TODO 6.9 : 이걸 xml로 eSelect 와 eSelect 라는 태그를 가진 노드를 불러와서 넣을 수 있음!! (메서드가 있음)
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
        public static void setValues(Node node) {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                String nodeName = child.getNodeName();

                for (EShapeTool edit : EShapeTool.values()) {
                    if (nodeName.equals(edit.toString())) {
                        edit.name = child.getAttributes().getNamedItem("label").getNodeValue();
//                        edit.methodName = child.getAttributes().getNamedItem("method").getNodeValue();
                    }
                }
            }
        }
    }


    // TODO 5.26 이거 mehtod name string 으로 하지 말고 .class 한 거 처럼 함수 이름으로 고쳐 써라
    public enum EFileMenuItem {
        eNew("", "newPanel", ""),
        eOpen("", "open", ""),
        eSave("", "save", ""),
        eSaveAs("", "saveAs", ""),
        ePrint("", "print", ""),
        eClose("", "close", ""),
        eQuit("", "quit", "");

        private String name;
        private String methodName;
        private String toolTip;

        EFileMenuItem(String name, String methodName, String s) {
            this.name = name;
            this.methodName = methodName;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getName() {
            return name;
        }

        public static void setValues(Node node) {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                String nodeName = child.getNodeName();

                for (EFileMenuItem edit : EFileMenuItem.values()) {
                    if (nodeName.equals(edit.toString())) {
                        edit.name = child.getAttributes().getNamedItem("label").getNodeValue();
//                        edit.methodName = child.getAttributes().getNamedItem("method").getNodeValue();
                        edit.toolTip = child.getAttributes().getNamedItem("toolTipText").getNodeValue();
                    }
                }
            }
        }
    }

    public enum EEditMenuItem {
        eReDo("", "", ""),
        eUnDo("", "", ""),
        eCut("", "", ""),
        eCopy("", "", ""),
        ePaste("", "", ""),
        eDelete("", "", ""),
        eGroup("", "", ""),
        eUnGroup("", "", "");

        private String name;
        private String methodName;
        private String toolTip;

        EEditMenuItem(String name, String methodName, String s) {
            this.name = name;
            this.methodName = methodName;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getName() {
            return name;
        }

        public static void setValues(Node node) {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                String nodeName = child.getNodeName();

                for (EEditMenuItem edit : EEditMenuItem.values()) {
                    if (nodeName.equals(edit.toString())) {
                        edit.name = child.getAttributes().getNamedItem("label").getNodeValue();
//                        edit.methodName = child.getAttributes().getNamedItem("method").getNodeValue(); // TODO
                        edit.toolTip = child.getAttributes().getNamedItem("toolTipText").getNodeValue();
                    }
                }
            }
        }
    }

    public enum EGraphicsMenuItem {
        eFontStyle("", "", ""),
        elineStyle("", "", ""),
        eLineColor("", "", ""),
        eFillColor("", "", "");

        private String name;
        private String methodName;
        private String toolTip;

        EGraphicsMenuItem(String name, String methodName, String s) {
            this.name = name;
            this.methodName = methodName;
        }

        public String getName() {
            return name;
        }

        public String getMethodName() {
            return methodName;
        }

        public static void setValues(Node node) {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                String nodeName = child.getNodeName();

                for (EGraphicsMenuItem edit : EGraphicsMenuItem.values()) {
                    if (nodeName.equals(edit.toString())) {
                        edit.name = child.getAttributes().getNamedItem("label").getNodeValue();
//                        edit.methodName = child.getAttributes().getNamedItem("method").getNodeValue();
                        edit.toolTip = child.getAttributes().getNamedItem("toolTipText").getNodeValue();
                    }
                }
            }
        }
    }
}
