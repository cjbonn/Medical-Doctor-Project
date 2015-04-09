import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class Footer extends JPanel {

	Login login;
	JLabel footerName,footerTime,footerDate;
	
	public Footer(Login l){
		login = l;
		setLayout(new GridLayout(1,3));
		footerName = new JLabel(login.getFullName());
		footerName.setHorizontalAlignment(SwingConstants.LEFT);
		add(footerName);
		footerTime = new JLabel(getCurrentTime());
		footerTime.setHorizontalAlignment(SwingConstants.CENTER);
		add(footerTime);
		footerDate = new JLabel(getCurrentDate());
		footerDate.setHorizontalAlignment(SwingConstants.RIGHT);
		add(footerDate);
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
	}
	
	public void updateTime(){
		footerTime.setText(getCurrentTime());
		revalidate();
	}
	
	private String getCurrentDate(){
		return new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	}
	
	private String getCurrentTime(){
		return new SimpleDateFormat("hh:mm aaa").format(new Date());
	}
}
