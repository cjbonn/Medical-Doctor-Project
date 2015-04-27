import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class VisitData extends JFrame {

	private int visitid;
	private JPanel panel;
	private ProjectDB DB = new ProjectDB();
	
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
		DBResult v = visitdbr.get(0);
		PatientData p = new PatientData((int) v.get("patientid"));	p.loadPatientInfo();
		
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
		JTextArea ros = new JTextArea(); ros.setText((String) v.get("symptoms"));
		JScrollPane rosp = new JScrollPane(ros);
		rosp.setBorder(BorderFactory.createTitledBorder("Review of System:"));
		visitPanel.add(rosp);
		JTextArea pe = new JTextArea(); pe.setText((String) v.get("physical"));
		JScrollPane pep = new JScrollPane(pe);
		pep.setBorder(BorderFactory.createTitledBorder("Physical Exam:"));
		visitPanel.add(pep);
		tab.add(visitPanel, "Visit Details");
		
		JPanel labPanel = new JPanel();
		ArrayList<DBResult> ltr = DB.getLabsForID(visitid);
		String tests = "";
		if(ltr.isEmpty() || ltr.size() < 1) tests = "None.  ";
		for(DBResult lt : ltr) tests += lt.get("test")+", ";
		tests = tests.substring(0, tests.length()-2);
		labPanel.add(new JLabel("Lab Test(s): "+tests));
		tab.add(labPanel, "Lab Tests");
		
		JPanel perPanel = new JPanel();
		ArrayList<DBResult> pr = DB.getPrescriptionsForID(visitid);
		String prescriptions = "";
		if(pr.isEmpty() || pr.size() < 1) prescriptions = "None.  ";
		for(DBResult r : pr) prescriptions += r.get("pname")+" ("+r.get("abbr")+"), ";
		prescriptions = prescriptions.substring(0, prescriptions.length()-2);
		perPanel.add(new JLabel("Prescription(s): "+prescriptions));
		tab.add(perPanel, "Prescriptions");
		panel.add(tab);
	}
}
