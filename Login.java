import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Login extends JFrame{
	
	public ProjectDB ProjectDB = new ProjectDB();
	JPanel panel,loginPanel;
	JLabel title, username, password, error;
	JTextField user;
	JPasswordField pass;
	JButton loginBtn;
	
	private boolean isLoggedIn = false;
	
	public Login(){
		super("Medical Doctor - Login");
		setSize(300,150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		build();
		add(panel);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void build(){
		panel = new JPanel(new BorderLayout());
		title = new JLabel("Login");
		title.setFont(new Font("Cambria",Font.BOLD,18));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title, BorderLayout.NORTH);
		
		loginPanel = new JPanel(new GridLayout(0,2));
		username = new JLabel("Username:  ");
		username.setFont(new Font("Cambria",Font.PLAIN,14));
		username.setHorizontalAlignment(SwingConstants.RIGHT);
		loginPanel.add(username);
		
		user = new JTextField(12);
		user.setAlignmentX(LEFT_ALIGNMENT);
		loginPanel.add(user);
		
		password = new JLabel("Password:  ");
		password.setFont(new Font("Cambria",Font.PLAIN,14));
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		loginPanel.add(password);
		
		pass = new JPasswordField(12);
		pass.setAlignmentX(LEFT_ALIGNMENT);
		loginPanel.add(pass);
		
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(new LoginEvent());
		loginBtn.setAlignmentX(RIGHT_ALIGNMENT);
		loginPanel.add(loginBtn);
		
		error = new JLabel("Incorrect Login");
		error.setFont(new Font("Cambria",Font.PLAIN,12));
		error.setForeground(Color.RED);
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setVisible(false);
		loginPanel.add(error);
		panel.add(loginPanel,BorderLayout.CENTER);
	}
	
	public void login_success(){
		isLoggedIn = true;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(false);
		dispose();
	}
	
	public boolean isLoggedIn(){
		return isLoggedIn;
	}
	
	public int getUserID(){
		return ProjectDB.getUserID();
	}
	
	public int getAccountType(){
		return ProjectDB.getAccountType();
	}
	
	public String getFirstName(){
		return ProjectDB.getFirstName();
	}
	
	public String getLastName(){
		return ProjectDB.getLastName();
	}
	
	public String getFullName(){
		return ProjectDB.getFullName();
	}
	
	public void trigger_error(){
		error.setVisible(true);
	}
	
	private class LoginEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			error.setVisible(false);
			String u = user.getText();
			char[] p = pass.getPassword();
			if(u.isEmpty() || p.length <= 5){
				trigger_error();
				return;
			}
			System.out.println("Logging in...");
			if(ProjectDB.login(u, p)){
				System.out.println("Login Successful");
				login_success();
			}else{
				trigger_error();
				System.out.println("Bad Login");
				System.out.println(ProjectDB.getErrorInfo());
				return;
			}
		}
	}
}
