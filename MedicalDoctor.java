import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MedicalDoctor extends JFrame {

	Login login;
	JPanel panel;
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
				panel.add(new JLabel("Logged In."), BorderLayout.NORTH);
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
