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
//        this.file = new File("test.gvs");
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


//
//		JFileChooser fileChooser = new JFileChooser();
//		fileChooser.setDialogTitle("파일 열기");
//
//		int userSelection = fileChooser.showOpenDialog(null);
//		if (userSelection == JFileChooser.APPROVE_OPTION) {
//			File fileToOpen = fileChooser.getSelectedFile();
//
//			try (ObjectInputStream ois = new ObjectInputStream(
//					new BufferedInputStream(new FileInputStream(fileToOpen)))) {
//
//				// 도형 복원
//				Vector<GShape> loadedShapes = (Vector<GShape>) ois.readObject();
//
//				// 새로운 드로잉 패널 생성
//				GDrawingPanel newPanel = new GDrawingPanel();
//				newPanel.setShapes(loadedShapes); // 도형 설정 (setShapes는 별도 구현 필요)
//				newPanel.repaint();
//
//				// 탭에 추가
//				jTabbedPane.addTab(fileToOpen.getName(), newPanel);
//
//			} catch (IOException | ClassNotFoundException ex) {
//				ex.printStackTrace();
//				JOptionPane.showMessageDialog(null, "파일 열기 중 오류가 발생했습니다", "오류", JOptionPane.ERROR_MESSAGE);
//			}
//		}
    }

    // TODO 5.26 try-catch 여기다가 넣어야 됨
    // 강의자료 참고 [ 이 단계별로 강의자료에서 설명한 내용 이해 (헀으면 갠적으로 좋겟음)]
    // 메모리 내 데이터를 파일에 써서 key-value 혇태로 object 를 serialize (어레이로 쭉 뽑아내는 것?) 해서 저장할 수 있도록 해주는 것
    // 스트림을 연결하면 다른 일을 몬함. 그래서 또 다른 메모리에다가 쓰도록 함.
    // dma? cpu가 프로세스 실행할 때 관여하지 않고 메모리가 전부 읽어온 후 처리해서 속도가 느려지지 않도록 함
    public void save() throws IOException {
        if (this.file == null) {
            if (this.saveAs()) return;
        }

        System.out.println("save");
        Vector<GShape> shapes = this.drawingPanel.getShapes();

        FileOutputStream fileOutputStream = new FileOutputStream(this.file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeObject(shapes);
        objectOutputStream.close();
        this.drawingPanel.setbUpdated(false);
    }

    public boolean saveAs() throws IOException {
        boolean bCancel = false;
        JFileChooser chooser = new JFileChooser(this.dlr);
        chooser.setSelectedFile(file);
        // TODO 맥은 확장자 다를 수 있음
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Graphics", "gvs");
        chooser.setFileFilter(fileNameExtensionFilter);

        int returnVal = chooser.showSaveDialog(this.drawingPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.dlr = chooser.getCurrentDirectory();
            this.file = chooser.getSelectedFile();
        } else {
            bCancel = true;
        }
        return bCancel;

//        System.out.println("save as");
//        Vector<GShape> shapes = this.drawingPanel.getShapes();
//
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("파일 저장");
//
//        int userSelection = fileChooser.showSaveDialog(null);
//        if (userSelection == JFileChooser.APPROVE_OPTION) {
//            File fileToSave = fileChooser.getSelectedFile();
//
//            FileOutputStream fos = new FileOutputStream(fileToSave);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(shapes);
//            oos.close();
//        }

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
				// bCancel = this.save(); // TODO : save 도 불린 돼야 함
//                this.save();
            }
        }
        return !bCancel;
    }
    // TODO 여기서 뜯어서 위에 거 작성함.
    // this.drawing... 이거를 getDrawing() 으로 가져온 거 참고하기
    //	private boolean checkShape() {
//		boolean bCancel = false;
//		int reply = ON_OPTION;
//
//		if (this.drawingPanel.isUpdated()) {
//			reply = Pram.showConfironDolaog(this.getDrewingPanel, "내용");
//		}
//	}

    public void quit() throws IOException {
        System.out.println("quit");
        if (!this.close()) {
            System.exit(0); // TODO 6.2 이거 뭔지 찾아보기
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
