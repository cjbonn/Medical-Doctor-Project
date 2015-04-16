import javax.swing.*;
import java.awt.*;

public class SecretaryPanel extends JPanel {

		 private JLabel fname,mi,lname,address,insurance,age,height,weight;
		  private JTextField fn,m,ln,ad,in,a,h,w;
		  private JTextArea history;
		  private JRadioButton male,female;
		  private ButtonGroup gender;
		  private JButton submit,cancel;
		  private JPanel name,info,hist,button;
		  public SecretaryPanel()
		  {
		    setLayout(new GridLayout(4,1));
		    setPreferredSize(new Dimension(500,500));
		    name = new JPanel();
		    info = new JPanel();
		    hist = new JPanel();
		    button = new JPanel();
		    fname = new JLabel("First Name: ");
		    mi = new JLabel("Middle Initial: ");
		    lname = new JLabel("Last Name: ");
		    address = new JLabel("Address: ");
		    age = new JLabel("Age: ");
		    height = new JLabel("Height: ");
		    weight = new JLabel("Weight: ");
		    insurance = new JLabel("Insurance: ");

		    male = new JRadioButton("Male");
		    female = new JRadioButton("Female");
		    gender = new ButtonGroup();
		    gender.add(male);
		    gender.add(female);

		    fn = new JTextField(10);
		    m = new JTextField(1);
		    ln = new JTextField(10);
		    ad = new JTextField(10);
		    in = new JTextField(10);
		    a = new JTextField(3);
		    h = new JTextField(4);
		    w = new JTextField(3);
		    history = new JTextArea(20,20);
		    history.setBorder(BorderFactory.createTitledBorder("History:"));

		    submit = new JButton("Submit");
		    cancel = new JButton("Cancel");
		    name.add(fname);
		    name.add(fn);
		    name.add(mi);
		    name.add(m);
		    name.add(lname);
		    name.add(ln);
		    info.add(address);
		    info.add(ad);
		    info.add(age);
		    info.add(a);
		    info.add(height);
		    info.add(h);
		    info.add(weight);
		    info.add(w);
		    info.add(male);
		    info.add(female);
		    info.add(insurance);
		    info.add(in);
		    hist.add(history);
		    add(name);
		    add(info);
		    add(hist);
		    button.add(submit);
		    button.add(cancel);
		    add(button);

		  }
}