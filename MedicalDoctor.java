import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MedicalDoctor extends JFrame {

	Login login;
	JPanel panel, mainPanel;
	Footer footer;
	
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
