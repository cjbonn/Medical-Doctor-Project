import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;



import javax.swing.*;

public class VisitData extends JFrame {

	private int visitid;
	private JPanel panel;
	private ProjectDB DB = new ProjectDB();
	private PatientData p;
	private DBResult v;
	private String tests = "";
	private String prescriptions = "";
	private JTextArea ros, pe;

	public VisitData(int visitid){
		super("Medical Doctor - Visit Information");
		this.visitid = visitid;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		build();
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public void build(){
		panel = new JPanel();
		JTabbedPane tab = new JTabbedPane();
		ArrayList<DBResult> visitdbr = DB.getVisitByID(visitid);
		v = visitdbr.get(0);
		p = new PatientData((int) v.get("patientid"));	p.loadPatientInfo();

		JPanel patientInfoPanel = new JPanel(new GridLayout(1,2));
		JPanel pip1 = new JPanel(new GridLayout(3,2));
		pip1.add(new JLabel("Name: "+p.getFullName()));	pip1.add(new JLabel());
		pip1.add(new JLabel("Sex: "+ ((p.getSex() == 0)?"M":"F"))); pip1.add(new JLabel("Age: "+String.valueOf(p.getAge())));
		pip1.add(new JLabel("Height: "+p.getFormattedHeight())); pip1.add(new JLabel("Weight: "+p.getWeight()+"lbs."));
		patientInfoPanel.add(pip1);
		JPanel pip2 = new JPanel(new GridLayout(3,2));
		pip2.add(new JLabel("Address: ")); pip2.add(new JLabel("Doctor: "+p.getDoctorName()));
		pip2.add(new JLabel(p.getAddress())); pip2.add(new JLabel("Insurance: "+p.getInsuranceName()));
		pip2.add(new JLabel(p.getCity()+", "+p.getState()+" "+p.getZipCode()));
		patientInfoPanel.add(pip2);
		tab.add(patientInfoPanel, "Patient Info");

		JPanel visitPanel = new JPanel(new GridLayout(1,3));
		JPanel vp1 = new JPanel(); vp1.setLayout(new BoxLayout(vp1, BoxLayout.Y_AXIS));
		vp1.add(new JLabel("Present Illness: "+v.get("illness")));
		vp1.add(new JLabel("Cheif Complaint: "+v.get("complaint")));
		vp1.add(new JLabel("Impression: "+v.get("impression")));
		vp1.add(new JLabel("Diagnosis: "+v.get("diagnosis")));
		visitPanel.add(vp1);
		ros = new JTextArea(); ros.setText((String) v.get("symptoms"));
		ros.setEditable(false);
		JScrollPane rosp = new JScrollPane(ros);
		rosp.setBorder(BorderFactory.createTitledBorder("Review of System:"));
		visitPanel.add(rosp);
		pe = new JTextArea(); pe.setText((String) v.get("physical"));
		pe.setEditable(false);
		JScrollPane pep = new JScrollPane(pe);
		pep.setBorder(BorderFactory.createTitledBorder("Physical Exam:"));
		visitPanel.add(pep);
		tab.add(visitPanel, "Visit Details");

		JPanel labPanel = new JPanel();
		ArrayList<DBResult> ltr = DB.getLabsForID(visitid);
		tests = "";
		if(ltr.isEmpty() || ltr.size() < 1) tests = "None.  ";
		for(DBResult lt : ltr) tests += lt.get("test")+", ";
		tests = tests.substring(0, tests.length()-2);
		labPanel.add(new JLabel("Lab Test(s): "+tests));
		tab.add(labPanel, "Lab Tests");

		JPanel perPanel = new JPanel();
		ArrayList<DBResult> pr = DB.getPrescriptionsForID(visitid,false);
		prescriptions = "";
		if(pr.isEmpty() || pr.size() < 1) prescriptions = "None.  ";
		for(DBResult r : pr) prescriptions += r.get("pname")+" ("+r.get("abbr")+"), ";
		prescriptions = prescriptions.substring(0, prescriptions.length()-2);
		perPanel.add(new JLabel("Prescription(s): "+prescriptions));
		tab.add(perPanel, "Prescriptions");
		panel.add(tab);

		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new PrintListener());
		tab.add(new JPanel());
		tab.setTabComponentAt(4, btnPrint);
	}

		private class PrintListener implements ActionListener{
			public void actionPerformed(ActionEvent e){

				try{
					File f = new File("PrintPatientData.txt");
					PrintWriter writer = new PrintWriter(f, "UTF-8");

					writer.println("Patient Info:");
					writer.println("Name: "+p.getFullName()+" Doctor: "+p.getDoctorName());
					writer.println("Visit Date: " + v.get("visit_date"));
					writer.println("Sex: "+((p.getSex() == 0)?"M":"F")+" Age: "+String.valueOf(p.getAge()+ " Insurance: "+p.getInsuranceName()));
					writer.println("Height: "+p.getFormattedHeight()+"Weight: "+p.getWeight()+"lbs.");
					writer.println("Address: " + p.getAddress() + " " + p.getCity()+", "+p.getState()+" "+p.getZipCode());
					writer.println();

					writer.println("Present Illness: "+v.get("illness"));
					writer.println("Cheif Complaint: "+v.get("complaint"));
					writer.println("Impression: "+v.get("impression"));
					writer.println("Diagnosis: "+v.get("diagnosis"));
					writer.println("Review of System:\n"+ros.getText());
					writer.println();
					writer.println("Physical Exam: \n"+pe.getText());
					writer.println();

					writer.println("Lab Test(s): "+tests);
					writer.println();
					writer.println("Prescription(s): "+prescriptions);
					writer.close();

					Desktop d = Desktop.getDesktop();

					d.edit(f);
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}


			}
	}
}
