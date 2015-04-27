import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DoctorPanel extends JPanel {

	private JLabel name,age,sex,height,weight,cComplaint,presill,impression,diagnosis;
	private JTextField n,a,h,w,cc,illness,impress,diag,dName;
	private JTextArea review,exam;
	private JCheckBox red,white,liver,renal,elec,xray,ct,mri,urine,stool;
	private JButton submit,cancel,movRight,movLeft;
	private JList patientList,history,perscript,selected,list;
	private JPanel nameList,patientInfo,info,docGuide,comboPanel,labTest,script,pill,needle,buttonPanel,movButton;
	private int currentPatientID,patientAge;
	private boolean listIsDisabled = false;
	private ProjectDB DB = new ProjectDB();
	private DefaultListModel listmodel,hitems,items;
	
	public DoctorPanel(){
		setLayout(new BorderLayout());
		JTabbedPane tab = new JTabbedPane();
		buildList();
		buildPatient();
		buildDocguide();
		buildLab();
		buildPerscript();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		comboPanel = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		comboPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		comboPanel.add(patientInfo,c);
		tab.add(docGuide,"Doctor Guide");
		tab.add(labTest,"Lab Test");
		tab.add(script,"Perscriptions");
		c.gridy=1;
		comboPanel.add(tab,c);
		c.gridy=2;
		comboPanel.add(buttonPanel,c);
		add(nameList,BorderLayout.WEST);
		add(comboPanel,BorderLayout.CENTER);
	}
	
	public void buildList(){
		nameList = new JPanel();
		nameList.setPreferredSize(new Dimension(200,200));
		items = new DefaultListModel();
		ArrayList<DBResult> patientList = DB.getPatients();
		for(DBResult r : patientList) items.addElement(new PatientData((int) r.get("id"),(String) r.get("fname"),(String) r.get("lname")));
		list = new JList(items);
		list.setVisibleRowCount(40);
		list.addListSelectionListener(new ListListener());
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(new Dimension(200,listScrollPane.getPreferredSize().height));
		listScrollPane.setBorder(BorderFactory.createTitledBorder("Patients:"));
		nameList.add(listScrollPane);
	}
	
	public void buildPatient(){
		patientInfo = new JPanel();
		patientInfo.setSize(200,230);
		patientInfo.setLayout(new BorderLayout());
		patientInfo.setBorder(BorderFactory.createTitledBorder("Patient Info:"));
		name = new JLabel("Name: ");
		age = new JLabel("Age: ");
		sex = new JLabel("Gender: ");
		height = new JLabel("Height: ");
		weight = new JLabel("Weight: "); 
		
		n = new JTextField(12);
		n.setEditable(false);
		a = new JTextField(3);
		a.setEditable(false);
		h = new JTextField(4);
		h.setEditable(false);
		w = new JTextField(3);
		w.setEditable(false);
		hitems = new DefaultListModel();
		history = new JList(hitems);
		history.addMouseListener(new HistoryListener());
		history.setBorder(BorderFactory.createTitledBorder("History:"));
		history.setBackground(new Color(238,238,238));
		
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
	
	public void buildDocguide(){
		docGuide = new JPanel();
		docGuide.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		cComplaint = new JLabel("Cheif Complaint: ");
		presill = new JLabel("Present Illness: ");
		review = new JTextArea(5,5);
		//review.setEditable(false);
		review.setBorder(BorderFactory.createTitledBorder("Review of System:"));
		exam = new JTextArea(5,5);
		//exam.setEditable(false);
		exam.setBorder(BorderFactory.createTitledBorder("Physical Exam:"));
		impression = new JLabel("Impression: ");
		diagnosis = new JLabel("Diagnosis: ");
		
		cc = new JTextField(20);
		illness = new JTextField(20);
		impress = new JTextField(20);
		diag = new JTextField(20);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		docGuide.add(cComplaint,c);
		c.gridx=1;
		docGuide.add(cc,c);
		c.gridx=0;
		c.gridy=1;
		docGuide.add(presill,c);
		c.gridx=1;
		c.gridy=1;
		docGuide.add(illness,c);
		c.gridx=0;
		c.gridy=4;
		docGuide.add(impression,c);
		c.gridx=1;
		c.gridy=4;
		docGuide.add(impress,c);
		c.gridx=0;
		c.gridy=5;
		docGuide.add(diagnosis,c);
		c.gridx=1;
		c.gridy=5;
		docGuide.add(diag,c);
		c.gridwidth =4;
		c.gridx=0;
		c.gridy=2;
		docGuide.add(review,c);
		c.gridwidth =4;
		c.gridx=0;
		c.gridy=3;
		docGuide.add(exam,c);
	}
	
	public void buildLab(){
		labTest = new JPanel();
		labTest.setLayout(new GridLayout(5,2));
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
	
	public void buildPerscript(){
		script = new JPanel();
		movButton = new JPanel();
		script.setLayout(new GridLayout(1,3));
		movButton.setLayout(new BoxLayout(movButton,BoxLayout.Y_AXIS));
		DefaultListModel prescriptions = new DefaultListModel();
		ArrayList<DBResult> pr = DB.getPrescriptionList();
		for(DBResult pers : pr) prescriptions.addElement(new PairedValue((int) pers.get("id"), (String) pers.get("prescription"),(String) pers.get("abbr")));
		perscript = new JList(prescriptions);
		perscript.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listmodel = new DefaultListModel();
		selected = new JList(listmodel);
		selected.setVisibleRowCount(6);
		selected.setBorder(BorderFactory.createTitledBorder("Prescriptions:"));
		movRight = new JButton(">>");
		movRight.setAlignmentX(CENTER_ALIGNMENT);
		movRight.setActionCommand("right");
		movRight.addActionListener(new ButtonListener());
		movLeft = new JButton("<<");
		movLeft.setAlignmentX(CENTER_ALIGNMENT);
		movLeft.setActionCommand("left");
		movLeft.addActionListener(new ButtonListener());
		dName = new JTextField(10);
		dName.setBorder(BorderFactory.createTitledBorder("Medication:"));
		script.add(perscript);
		movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));
		movButton.add(movRight);movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));
		movButton.add(movLeft);movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));
		movButton.add(dName);movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));movButton.add(new JLabel(" "));
		script.add(movButton);
		script.add(selected);
	}
	
	private void updatePatientData(PatientData p){
		currentPatientID = p.getPatientID();
		n.setText(p.getFullName());
		w.setText(String.valueOf(p.getWeight()));
		h.setText(p.getFormattedHeight());
		patientAge = p.getAge();
		a.setText(String.valueOf(patientAge));
		hitems.clear();
		ArrayList<DBResult> dbr = p.getHistory();
		if(dbr.isEmpty() || dbr.size() < 1){
			hitems.addElement("None");
		}else{
			for(DBResult r : dbr){
				hitems.addElement(new PairedValue((int) r.get("id"), new SimpleDateFormat("MM-dd-YYYY").format(r.get("visit_date"))+" - "+r.get("complaint")));
			}
		}
	}
	
	private void resetPatientData(){
		currentPatientID = 0;
		n.setText("");
		a.setText("");
		w.setText("");
		h.setText("");
		hitems.clear();
	}
	
	class PairedValue {
		String name,name2;
		int id;
		
		public PairedValue(int id, String str){
			this.name = str;
			this.id = id;
		}
		
		public PairedValue(int id, String str, String str2){
			this.name = str;
			this.name2 = str2;
			this.id = id;
		}
		
		public String toString(){
			return name;
		}
		
		public int getID(){
			return id;
		}
		
		public String getName(){
			return this.toString();
		}
	}
	
	class PairedValue2 {
		String val,val2;
		int id;
		
		public PairedValue2(int id, String str, String str2){
			this.val = str;
			this.val2 = str2;
			this.id = id;
		}
		
		public String toString(){
			return val+" - "+val2;
		}
		
		public int getID(){
			return id;
		}
		
		public String getName(){
			return this.toString();
		}
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			switch(e.getActionCommand()){
			case "right":
				addPrescription();
				break;

			case "left":
				removePrescription();
				break;
			}
		}
		
		private void addPrescription(){
			PairedValue pv = (PairedValue) perscript.getSelectedValue();
			String pname = dName.getText();
			listmodel.addElement(new PairedValue2(pv.getID(),pv.name2,pname));
			dName.setText("");
		}
		
		private void removePrescription(){
			Object selections = selected.getSelectedValue();
			listmodel.removeElement(selections);
		}
	}
	
	private class ListListener implements ListSelectionListener	{
		public void valueChanged(ListSelectionEvent e){
			if(listIsDisabled) return; // When adding new visit
			if(e.getValueIsAdjusting()) return;
			try {
				PatientData patient = (PatientData) list.getSelectedValue();
				if(!patient.isLoaded()) patient.loadPatientInfo();
				updatePatientData(patient);
			}catch(NullPointerException err){
				resetPatientData();
			}
		}
	}
	
	private class HistoryListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e){
			if(!(history.getSelectedValue() instanceof PairedValue)) return;
			if(e.getClickCount() == 2){
				PairedValue p = (PairedValue) history.getSelectedValue();
				new VisitData(p.getID());
				history.clearSelection();
			}
		}
	}
}
