import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class _asclepius extends JFrame
{
  private _patient p;
  private _doctor d;
  private _nurse n;
  
  private JPanel panelA,panelB,panelC;
  
  public _asclepius()
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
    p = new _patient();
    panelA.add(p);
  }
  
  public void buildB()
  {
    panelB = new JPanel();
    d = new _doctor();
    panelB.add(d);
  }
  
  public void buildC()
  {
    panelC = new JPanel();
    n = new _nurse();
    panelC.add(n);
  }
  public static void main(String[] args)
{
  new _asclepius();
}
}