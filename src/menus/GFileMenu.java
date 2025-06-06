package menus;

import frame.GDrawingPanel;
import global.GConstants;
import shapes.GShape;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

public class GFileMenu extends JMenu {

    private File dlr;
    private File file;

    @Serial
    private static final long serialVersionUID = 1L;
    private GDrawingPanel drawingPanel;
    private JTabbedPane jTabbedPane;

    public GFileMenu() {
        super("File");

        ActionHandler actionHandler = new ActionHandler();
        for (GConstants.EFileMenuItem item : GConstants.EFileMenuItem.values()) {
            JMenuItem menuItem = new JMenuItem(item.getName());
            menuItem.addActionListener(actionHandler);
            menuItem.setActionCommand(item.name());
            this.add(menuItem);
        }
    }

    public void initialize() {
        // TODO 6.2 과제
        // TODO 수업코드에선 xml 에 상수 정의하고 불러옴 (다음시간에 할 수도 있음)
        // TODO 저장 테스트하고 디폴트 디렉토리 잘 들어가는지 확인
        // TODO 대충 저장해도 .gvs 로 저장되도록
        this.dlr = new File("/Users/heo/Desktop/univ/패턴중심사고와프로그래밍/pattern-workspace/classes.gvs");
    }

    public void associate(GDrawingPanel drawingPanel, JTabbedPane jTabbedPane) {
        this.drawingPanel = drawingPanel;
        this.jTabbedPane = jTabbedPane;
    }


    public void newPanel() throws IOException {
        System.out.println("new");

        // TODO cancel 이면 동작 안 하도록 해야 됨
        if (!this.close()) return;

        this.drawingPanel.initialize();

//        GDrawingPanel newPanel = new GDrawingPanel();
//        newPanel.seteShapeTool(GConstants.EShapeTool.eSelect);
//        String tabName = "Untitled " + (jTabbedPane.getTabCount() + 1);
//        jTabbedPane.addTab(tabName, newPanel);
//        jTabbedPane.setSelectedComponent(newPanel); // 새 탭으로 전환
//        jTabbedPane.repaint();
    }

    public void open() throws IOException, ClassNotFoundException {
        System.out.println("open");
        FileInputStream fileInputStream = new FileInputStream("file");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream); // 자바 형태의 버퍼에서 오브젝트로
        // TODO 이거 select 된 판넬을 가리키도록 수정(여러 판넬 있으면)
        this.drawingPanel.setShapes(objectInputStream.readObject()); // TODO 타입 캐스트 안에 드가서 화긴
        objectInputStream.close();
    }

    // TODO 5.26 try-catch 여기다가 넣어야 됨
    public boolean save() throws IOException {
        if (this.file == null) {
            boolean returnValue = this.saveAs();
            if (returnValue) return true;
        }

        System.out.println("save");
        Vector<GShape> shapes = this.drawingPanel.getShapes();

        FileOutputStream fileOutputStream = new FileOutputStream(this.file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeObject(shapes);
        objectOutputStream.close();
        this.drawingPanel.setbUpdated(false);
        return false;
    }

    public boolean saveAs() throws IOException {
        boolean bCancel = false;
        JFileChooser chooser = new JFileChooser(this.dlr);
        chooser.setSelectedFile(file);
        // TODO 맥은 확장자 다를 수 있음
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("gvs", "gvs");
        chooser.setFileFilter(fileNameExtensionFilter);

        int returnVal = chooser.showSaveDialog(this.drawingPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.dlr = chooser.getCurrentDirectory();
            this.file = chooser.getSelectedFile();
        } else {
            bCancel = true;
        }
        return bCancel;
    }

    public void print() {
        System.out.println("print");

    }

    public boolean close() throws IOException {
        System.out.println("close");
        boolean bCancel = false;

        if (this.drawingPanel.isUpdated()) {
            int reply = JOptionPane.NO_OPTION;
            reply = JOptionPane.showConfirmDialog(this.drawingPanel, "변경내용 저장?");
            if (reply == JOptionPane.CANCEL_OPTION) {
                bCancel = true;
            } else if (reply == JOptionPane.OK_OPTION) {
                // TODO 만일, 파일이 처음 저장되는 거면, 이름이 없기 때문에 창이 뜨는데
                // 이때 또 취소가 날 수 있으므로 주석 해제하고 리펙토링 해야 함
                bCancel = this.save(); // TODO : save 도 불린 돼야 함
            }
        }
        return !bCancel;
    }

    public void quit() throws IOException {
        System.out.println("quit");
        if (this.close()) {
            System.exit(0); // TODO 6.2 과제 : 이거 뭔지 찾아보기
        }
    }

    public void invokeMethod(String name) {
        try {
            Method method = this.getClass().getMethod(name);
            method.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GConstants.EFileMenuItem item = GConstants.EFileMenuItem.valueOf(e.getActionCommand());
            invokeMethod(item.getMethodName());
        }
    }
}
