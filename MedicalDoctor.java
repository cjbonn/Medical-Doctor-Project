import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MedicalDoctor extends JFrame {

	Login login;
	JPanel panel, mainPanel;
	Footer footer;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem exitItem,logoutItem;

	private int firstBuild = 0;

	public static void main(String[] args){
		new MedicalDoctor();
	}

	public MedicalDoctor(){
		super("Medical Doctor");
		setSize(500,500);
		createMenu();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel(new BorderLayout());
		login = new Login();
		login.addWindowListener(new ExitListener());
		build();
		setLocationRelativeTo(null);
//		setResizable(false);
		setVisible(true);
	}

	public void build(){
		firstBuild++;
		if(firstBuild >= 2){
			if(login.isLoggedIn()){
				if(login.getAccountType() == 0){ // Doctor
					mainPanel = new DoctorPanel();
				}else if(login.getAccountType() == 1){ // Nurse
					mainPanel = new NursePanel();
				}else if(login.getAccountType() == 2){ // Secretary
					mainPanel = new SecretaryPanel();
				}else{ // Other
					mainPanel = new JPanel();
					mainPanel.add(new JLabel("Unknown account type."), BorderLayout.NORTH);
				}
				panel.add(mainPanel, BorderLayout.CENTER);
				
				// Footer
				footer = new Footer(login);
				panel.add(footer, BorderLayout.SOUTH);
				new Timer(1000,new ActionListener(){
					public void actionPerformed(ActionEvent e){
						footer.updateTime();
					}
				}).start();
			}
		}
		add(panel);
		validate();
	}

	public void createMenu(){
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");

		logoutItem = new JMenuItem("Logout");
		logoutItem.setActionCommand("logout");
		logoutItem.addActionListener(new MenuListener());
		fileMenu.add(logoutItem);

		exitItem = new JMenuItem("Exit");
		exitItem.setActionCommand("close");
		exitItem.addActionListener(new MenuListener());
		fileMenu.add(exitItem);

		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	/*
	 * All Menu events are triggered here
	 */
	private class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!login.isLoggedIn()) return;
			switch(e.getActionCommand()){
			case "close": // On File > Exit
				System.exit(0);
				break;

			case "logout": // On File > Logout
				panel.removeAll();
				login = new Login();
				login.addWindowListener(new ExitListener());
				break;

			default: break;
			}
		}
	}

	private class ExitListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			close();
		}

		public void windowClosed(WindowEvent e) {
			close();
		}

		private void close(){
			build();
		}

	}
}
