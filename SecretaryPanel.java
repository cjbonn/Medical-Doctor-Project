import javax.swing.*;
import java.awt.*;

public class SecretaryPanel extends JPanel {

	private JLabel title;
	
	public SecretaryPanel(){
		setLayout(new BorderLayout());
		
		title = new JLabel("Patient Database");
		title.setFont(new Font("Cambria",44,Font.BOLD));
		add(title, BorderLayout.NORTH); // Not working for some reason
	}
}