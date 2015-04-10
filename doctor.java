import javax.swing.*;
import java.awt.*;

public class doctor extends JPanel
{
  private JLabel fname,mi,lname,age,sex,height,weight,cComplaint,presill,review,exam,impression,diagnosis;
  private JTextField fn,m,ln,a,s,h,w,cc,illness,rev,pExam,impress,diag;
  private JTextArea history;
  private JCheckBox red,white,liver,renal,elec,xray,ct,mri,urine,stool;
  private JRadioButton inject,pO,im,iv,sc;
  private ButtonGroup injection,perscript;
  private JButton submit,cancel;
  private JList patientList;
  private JPanel nameList,patientInfo,docGuide,comboPanel,labTest,script,pill,needle;
  
  public doctor()
  {
    setLayout(new BorderLayout());
    //setPreferredSize(new Dimension(200,200));
    buildList();
    buildPatient();
    buildDocguide();
    buildLab();
    buildPerscript();
    comboPanel = new JPanel();
    comboPanel.setLayout(new GridLayout(4,1));
    comboPanel.add(patientInfo);
    comboPanel.add(docGuide);
    comboPanel.add(labTest);
    comboPanel.add(script);
    add(nameList,BorderLayout.WEST);
    add(comboPanel,BorderLayout.CENTER);
    //add(patientInfo,BorderLayout.NORTH);
    //add(docGuide,BorderLayout.CENTER);
    
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
    patientInfo.setPreferredSize(new Dimension(500,150));
    patientInfo.setBorder(BorderFactory.createTitledBorder("Patient Info:"));
    fname = new JLabel("First Name: ");
    mi = new JLabel("Middle Initial: ");
    lname = new JLabel("Last Name: ");
    age = new JLabel("Age: ");
    sex = new JLabel("Gender: ");
    height = new JLabel("Height: ");
    weight = new JLabel("Weight: "); 
    
    fn = new JTextField(10);
    fn.setEditable(false);
    m = new JTextField(1);
    m.setEditable(false);
    ln = new JTextField(10);
    ln.setEditable(false);
    a = new JTextField(3);
    a.setEditable(false);
    s = new JTextField(6);
    s.setEditable(false);
    h = new JTextField(4);
    h.setEditable(false);
    w = new JTextField(3);
    w.setEditable(false);
    history = new JTextArea(10,10);
    history.setEditable(false);
    history.setBorder(BorderFactory.createTitledBorder("History:"));
    
    patientInfo.add(nameList);
    patientInfo.add(fname);
    patientInfo.add(fn);
    patientInfo.add(mi);
    patientInfo.add(m);
    patientInfo.add(lname);
    patientInfo.add(ln);
    patientInfo.add(age);
    patientInfo.add(a);
    patientInfo.add(height);
    patientInfo.add(h);
    patientInfo.add(weight);
    patientInfo.add(w);
    patientInfo.add(history);
  }
  
  public void buildDocguide()
  {
    docGuide = new JPanel();
    docGuide.setPreferredSize(new Dimension(500,150));
    cComplaint = new JLabel("Cheif Complaint: ");
    presill = new JLabel("Present Illness: ");
    review = new JLabel("Review of System: ");
    exam = new JLabel("Physical Exam: ");
    impression = new JLabel("Impression: ");
    diagnosis = new JLabel("Diagnosis: ");
    
    cc = new JTextField(10);
    illness = new JTextField(10);
    rev = new JTextField(10);
    pExam = new JTextField(10);
    impress = new JTextField(10);
    diag = new JTextField(10);
    
    docGuide.add(cComplaint);
    docGuide.add(cc);
    docGuide.add(presill);
    docGuide.add(illness);
    docGuide.add(review);
    docGuide.add(rev);
    docGuide.add(exam);
    docGuide.add(pExam);
    docGuide.add(impression);
    docGuide.add(impress);
    docGuide.add(diagnosis);
    docGuide.add(diag);
    
  }
  public void buildLab()
  {
    labTest = new JPanel();
    labTest.setPreferredSize(new Dimension(500,150));
    red = new JCheckBox("Red Blood Cell");
    white= new JCheckBox("White Blood Cell");
    liver= new JCheckBox("Liver Function");
    renal= new JCheckBox("Renal Function");
    elec= new JCheckBox("Electrol");
    xray= new JCheckBox("X-Ray");
    ct= new JCheckBox("Computed Tomography");
    mri= new JCheckBox("Magnetic Resonance Image");
    urine= new JCheckBox("Urine");
    stool= new JCheckBox("Stool");
    labTest.add(red);
    labTest.add(white);
    labTest.add(liver);
    labTest.add(renal);
    labTest.add(elec);
    labTest.add(xray);
    labTest.add(ct);
    labTest.add(mri);
    labTest.add(urine);
    labTest.add(stool);
  }
  public void buildPerscript()
  {
   script = new JPanel();
   script.setPreferredSize(new Dimension(500,150));
   script.setLayout(new GridLayout(1,2));
   pill = new JPanel();
   pill.setBorder(BorderFactory.createTitledBorder("Pills"));
   needle = new JPanel();
   needle.setBorder(BorderFactory.createTitledBorder("Injections"));
   inject = new JRadioButton("Injection");
   pO = new JRadioButton("Oral");
   perscript = new ButtonGroup();
   perscript.add(inject);
   perscript.add(pO);
   im = new JRadioButton("Intramuscular");
   iv = new JRadioButton("Intravascular");
   sc = new JRadioButton("Subcutaneous");
   injection = new ButtonGroup();
   injection.add(im);
   injection.add(iv);
   injection.add(sc);
   needle.add(inject);
   needle.add(im);
   needle.add(iv);
   needle.add(sc);
   pill.add(pO);
   submit = new JButton("Submit");
   cancel = new JButton("Cancel");
   script.add(needle);
   script.add(pill);
   script.add(submit);
   script.add(cancel);
  }
}