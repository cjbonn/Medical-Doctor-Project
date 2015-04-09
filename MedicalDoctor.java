import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MedicalDoctor extends JFrame {

	Login login;
	JPanel panel;
	
	private int firstBuild = 0;
	
	public static void main(String[] args){
		new MedicalDoctor();
	}
	
	public MedicalDoctor(){
		super("Medical Doctor");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		login = new Login();
		login.addWindowListener(new ExitListener());
		build();
	}
	
	public void build(){
		firstBuild++;
		panel = new JPanel(new BorderLayout());
		if(firstBuild >= 2){
			if(login.isLoggedIn()) panel.add(new JLabel("Testing"), BorderLayout.NORTH);
		}
		add(panel);
		validate();
	}
	
	private class ExitListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			close();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			close();
		}
		
		private void close(){
			build();
		}
		
	}
}
