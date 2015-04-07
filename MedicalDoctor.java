import javax.swing.*;
import java.awt.*;

public class MedicalDoctor extends JFrame {

	Login login;
	JPanel panel;
	
	public static void main(String[] args){
		new MedicalDoctor();
	}
	
	public MedicalDoctor(){
		super("Medical Doctor");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		build();
		add(panel);
		setLocationRelativeTo(null);
		setVisible(true);
		login = new Login();
	}
	
	public void build(){
		panel = new JPanel(new BorderLayout());
	}
}
