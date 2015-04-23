import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class nurse extends JPanel
{
  private JLabel name,age,sex,height,weight;
  private JTextField n,a,s,h,w;
  private JTextArea history;
  private JButton finished;
  private JList patientList;
  private JPanel nameList,patientInfo,info,comboPanel,buttonPanel;
  
  public nurse()
  {
    setLayout(new BorderLayout());
    buildList();
    buildPatient();
    finished = new JButton("Done");
    comboPanel = new JPanel();
    buttonPanel = new JPanel();
    buttonPanel.add(finished);
    comboPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx=0;
    c.gridy=0;
    comboPanel.add(patientInfo,c);
    c.gridy=1;
    comboPanel.add(buttonPanel,c);
    add(nameList,BorderLayout.WEST);
    add(comboPanel,BorderLayout.CENTER);
    
  }
  
  public void buildList()
  {
   nameList = new JPanel();
   nameList.setPreferredSize(new Dimension(200,200));
   nameList.setBorder(BorderFactory.createTitledBorder("Name list:"));
   /*try
   {
   Class.forName("com.mysql.jdbc.Driver");
   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs400","root","rapture");
   Statement stmt = conn.createStatement();
   ResultSet rs = stmt.executeQuery("SELECT name FROM grade");
   String[] names = new String[10];
   int i=0;
   while(rs.next())
   {
     names[i]=rs.getString("Name");
     i++;
   }
   patientList = new JList(names);
   patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   nameList.add(patientList);
   }
   catch(Exception c)
    {
      JOptionPane.showMessageDialog(null, c.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
    }
  }*/
  }
  
  public void buildPatient()
  {
    patientInfo = new JPanel();
    patientInfo.setSize(200,230);
    patientInfo.setLayout(new BorderLayout());
    patientInfo.setBorder(BorderFactory.createTitledBorder("Patient Info:"));
    name = new JLabel("Name: ");
    age = new JLabel("Age: ");
    sex = new JLabel("Gender: ");
    height = new JLabel("Height: ");
    weight = new JLabel("Weight: "); 
    
    n = new JTextField(10);
    n.setEditable(false);
    a = new JTextField(3);
    a.setEditable(false);
    s = new JTextField(6);
    s.setEditable(false);
    h = new JTextField(4);
    h.setEditable(false);
    w = new JTextField(3);
    w.setEditable(false);
    history = new JTextArea(5,5);
    history.setEditable(false);
    history.setBorder(BorderFactory.createTitledBorder("History:"));
    
    info = new JPanel();
    info.setLayout(new GridLayout(8,1));
    info.add(name);
    info.add(n);
    info.add(age);
    info.add(a);
    info.add(height);
    info.add(h);
    info.add(weight);
    info.add(w);
    patientInfo.add(info,BorderLayout.WEST);
    patientInfo.add(history,BorderLayout.CENTER);
    add(patientInfo);
  }
    
}