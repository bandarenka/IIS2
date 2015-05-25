import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecognizerFrame extends JFrame {

	public RecognizerFrame() {
		super("Binary attributes recognizer");

		// Location and size of frame

		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		center.x -= DEFAULT_WIDTH / 2;
		center.y -= DEFAULT_HEIGHT / 2;
		setLocation(center);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		execute = new JButton("Execute");
		terminate = new JButton("Terninate");

		initTerminateButtonListener();
		initExecuteButtonListener();

		setLayout(new BorderLayout());

		initX();
		initPanel();
		initMenu();

	}

	private void initTerminateButtonListener() {
		terminate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				printX();
			}
		});
	}

	private void initExecuteButtonListener() {
		execute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Main.readData();
				Main.learn();
				Main.recognize(X);
				//Main.printArray(Main.muV);
				printLog();
				String className = Main.getClassOfElement();
				JOptionPane.showMessageDialog(null, className);

			}
		});
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		openScript = new JMenuItem("Open...");
		exit = new JMenuItem("Exit");
		menu.add(openScript);
		menu.add(exit);
		menuBar.add(menu);
		setJMenuBar(menuBar);

		initExitButtonListener();

		initOpenScriptButtonListener();
	}

	private void initOpenScriptButtonListener() {
		openScript.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JFileChooser fileopen = new JFileChooser(
						"E:\\New folder\\Chilli\\WEB\\Resources\\Scripts");
				fileopen.setMultiSelectionEnabled(true);
				int ret = fileopen.showDialog(null, "Open File...");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File files[] = fileopen.getSelectedFiles();
					if (files.length > 0)
						folderPath = files[0].getParent();
					DefaultListModel<String> listModel = new DefaultListModel<String>();
					for (File f : files) {
						listModel.addElement(f.getName());

					}

					scriptList.setModel(listModel);
				}
			}
		});
	}

	private void initExitButtonListener() {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
	}

	private void initPanel() {
		radioButtonPanel = new JPanel(new GridLayout(3, 2));
		panel = new JPanel();
		panel.setLayout(new GridLayout(6, 1));
		for (int i = 0; i < Main.attributes.length; ++i) {
			panel.add(getRadioButtonPanel(Main.attributes[i], i));
		}

		JPanel buttonPanel = new JPanel();

		buttonPanel.add(execute);
		
		//buttonPanel.add(terminate);
		panel.add(buttonPanel);

		add(panel, BorderLayout.CENTER);
	}

	private JPanel getRadioButtonPanel(String str, int i) {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(new JLabel(str));
		JRadioButton yesRadioButton = new JRadioButton("Yes");
		JRadioButton noRadioButton = new JRadioButton("No");
		yesRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				X[i] = 1;

			}
		});
		noRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				X[i] = 0;

			}
		});
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(yesRadioButton);
		bGroup.add(noRadioButton);
		bGroup.setSelected(noRadioButton.getModel(), true);
		panel.add(yesRadioButton);
		panel.add(noRadioButton);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return panel;
	}

	public void initX() {
		for (int i = 0; i < X.length; ++i)
			X[i] = 0;
	}

	public void printX() {
		for (Integer x : X)
			System.out.print(x + " ");
		System.out.println();
	}

	
	private void printLog(){
		System.out.println("Vector of similiarity:");
		for(int i=0;i<Main.I;++i){
			String logString = Main.classNames.get(i) + " " +Main.muV[i];
			System.out.println(logString);
		}
		System.out.println();
	}
	private Integer[] X = new Integer[Main.n];
	private String folderPath;
	private JPanel radioButtonPanel;
	private JMenu menu;
	private JMenuItem openScript;
	private JMenuItem exit;
	private JPanel panel;
	private JButton execute;
	private JButton terminate;
	private JList scriptList;

	public static final int DEFAULT_HEIGHT = 500;
	public static final int DEFAULT_WIDTH = 1150;

}
