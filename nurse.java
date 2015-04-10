import javax.swing.*;
import java.awt.*;

public class nurse extends JPanel
{
  private JLabel name,instructions;
  private JTextField n,i;
  
  public nurse()
  {
    setLayout(new FlowLayout());
    
    name = new JLabel("Name: ");
    n = new JTextField(10);
    n.setEditable(false);
    instructions = new JLabel("Instructions: ");
    i = new JTextField(10);
    i.setEditable(false);
    add(name);
    add(n);
    add(instructions);
    add(i);
  }
}
  