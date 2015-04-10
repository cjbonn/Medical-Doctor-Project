import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class asclepius extends JFrame
{
  private patient p;
  private doctor d;
  private nurse n;
  
  private JPanel panelA,panelB,panelC;
  
  public asclepius()
  {
    super("Asclepius Medical System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    JTabbedPane tab = new JTabbedPane();
    
    buildA();
    buildB();
    buildC();
    tab.addTab("Patient",panelA);
    tab.addTab("Doctor",panelB);
    tab.addTab("Nurse",panelC);
    add(tab);
   setLocationRelativeTo(null);
    pack();
    setVisible(true); 
  }
  
  public void buildA()
  {
    panelA = new JPanel();
    p = new patient();
    panelA.add(p);
  }
  
  public void buildB()
  {
    panelB = new JPanel();
    d = new doctor();
    panelB.add(d);
  }
  
  public void buildC()
  {
    panelC = new JPanel();
    n = new nurse();
    panelC.add(n);
  }
  public static void main(String[] args)
{
  new asclepius();
}
}