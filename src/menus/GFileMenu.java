package menus;

import frame.GDrawingPanel;
import global.GConstants;
import shapes.GShape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

public class GFileMenu extends JMenu {
	
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

	}
	public void associate(GDrawingPanel drawingPanel, JTabbedPane jTabbedPane) {
		this.drawingPanel = drawingPanel;
		this.jTabbedPane = jTabbedPane;
	}



	public void newPanel() {
		System.out.println("new");
		GDrawingPanel newPanel = new GDrawingPanel();
		String tabName = "Untitled " + (jTabbedPane.getTabCount() + 1);
		jTabbedPane.addTab(tabName, newPanel);
		jTabbedPane.setSelectedComponent(newPanel); // 새 탭으로 전환
		jTabbedPane.repaint();
	}

	public void open() {
		System.out.println("open");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("파일 열기");

		int userSelection = fileChooser.showOpenDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();

			try (ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(fileToOpen)))) {

				// 도형 복원
				Vector<GShape> loadedShapes = (Vector<GShape>) ois.readObject();

				// 새로운 드로잉 패널 생성
				GDrawingPanel newPanel = new GDrawingPanel();
				newPanel.setShapes(loadedShapes); // 도형 설정 (setShapes는 별도 구현 필요)
				newPanel.repaint();

				// 탭에 추가
				jTabbedPane.addTab(fileToOpen.getName(), newPanel);

			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "파일 열기 중 오류가 발생했습니다", "오류", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// TODO 5.26 try-catch 여기다가 넣어야 됨
	// 강의자료 참고 [ 이 단계별로 강의자료에서 설명한 내용 이해 (헀으면 갠적으로 좋겟음)]
	// 메모리 내 데이터를 파일에 써서 key-value 혇태로 object 를 serialize (어레이로 쭉 뽑아내는 것?) 해서 저장할 수 있도록 해주는 것
	// 스트림을 연결하면 다른 일을 몬함. 그래서 또 다른 메모리에다가 쓰도록 함.
	// dma? cpu가 프로세스 실행할 때 관여하지 않고 메모리가 전부 읽어온 후 처리해서 속도가 느려지지 않도록 함
	public void save() throws IOException {
		System.out.println("save");
		Vector<GShape> shapes = this.drawingPanel.getShapes();

		FileOutputStream fileOutputStream = new FileOutputStream("file");
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
		objectOutputStream.writeObject(shapes);
		objectOutputStream.close();
	}

	public void saveAs() throws IOException {
		System.out.println("save as");
		Vector<GShape> shapes = this.drawingPanel.getShapes();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("파일 저장");

		int userSelection = fileChooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();

			FileOutputStream fos = new FileOutputStream(fileToSave);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(shapes);
			oos.close();
		}

	}
	public void print() {
		System.out.println("print");

	}
	public void quit() {
		System.out.println("quit");

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
