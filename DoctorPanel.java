import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DoctorPanel extends JPanel {

	private JLabel name,age,sex,height,weight,cComplaint,presill,impression,diagnosis;
	private JTextField n,a,s,h,w,cc,illness,impress,diag,dName;
	private JTextArea history,review,exam;
	private JCheckBox red,white,liver,renal,elec,xray,ct,mri,urine,stool;
	private JButton submit,cancel,edit,movRight,movLeft;
	private JList patientList,perscript,selected,list;
	private JPanel nameList,patientInfo,info,docGuide,comboPanel,labTest,script,pill,needle,buttonPanel,movButton;
	private ProjectDB DB = new ProjectDB();
	DefaultListModel listmodel,items;
	
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
		edit = new JButton("Edit");
		edit.addActionListener(new EditListener());
		comboPanel = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.add(submit);
		buttonPanel.add(edit);
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
		review.setEditable(false);
		review.setBorder(BorderFactory.createTitledBorder("Review of System:"));
		exam = new JTextArea(5,5);
		exam.setEditable(false);
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
		String[] prescriptions ={"Intramuscular injection","Intravascular injection","Subcutaneous injection","Oral Medication"};
		perscript = new JList(prescriptions);
		perscript.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listmodel = new DefaultListModel();
		selected = new JList(listmodel);
		selected.setVisibleRowCount(6);
		selected.setBorder(BorderFactory.createTitledBorder("Persciptions:"));
		movRight = new JButton(">>");
		movRight.setAlignmentX(CENTER_ALIGNMENT);
		movRight.addActionListener(new RightListener());
		movLeft = new JButton("<<");
		movLeft.setAlignmentX(CENTER_ALIGNMENT);
		movLeft.addActionListener(new LeftListener());
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
//		currentPatientID = p.getPatientID();
//		fname.setText(p.getFirstName());
//		mname.setText(p.getMiddleInitial());
//		lname.setText(p.getLastName());
//		weight.setText(String.valueOf(p.getWeight()));
//		height.setText(String.valueOf(p.getHeight()));
//		Date date = p.getDOB();
//		String m = new SimpleDateFormat("MMMMMMMM").format(date);
//		String d = new SimpleDateFormat("dd").format(date);
//		String y = new SimpleDateFormat("yyyy").format(date);
//		month.setSelectedItem(m);
//		day.setSelectedIndex(Integer.parseInt(d));
//		year.setSelectedItem(y);
//		sex.setSelectedIndex(p.getSex()+1);
//		address.setText(p.getAddress());
//		city.setText(p.getCity());
//		state.setText(p.getState());
//		zip.setText(p.getZipCode());
//		for(int i=0;i<doctorList.size();i++){
//			if(doctorList.get(i).getName().equalsIgnoreCase(p.getDoctorName())) doctor.setSelectedIndex(i+1);
//		}
//		insurance.setSelectedItem(p.getInsuranceName());
	}
	
	private void resetPatientData(){
//		currentPatientID = 0;
//		fname.setText("");
//		mname.setText("");
//		lname.setText("");
//		weight.setText("");
//		height.setText("");
//		month.setSelectedIndex(0);
//		day.setSelectedIndex(0);
//		year.setSelectedIndex(0);
//		sex.setSelectedIndex(0);
//		address.setText("");
//		city.setText("");
//		state.setText("");
//		zip.setText("");
//		doctor.setSelectedIndex(0);
//		insurance.setSelectedIndex(0);
	}
	
	private class RightListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object selections = perscript.getSelectedValue();
			String drug="";
			if(selections=="Intramuscular injection") drug="IM";
			else if(selections=="Intravascular injection") drug="IV";
			else if(selections=="Subcutaneous injection") drug="SC";
			else if(selections=="Oral Medication") drug="OM";
			listmodel.addElement(drug+"-"+dName.getText());
			dName.setText("");
		}
	}
	private class LeftListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object selections = selected.getSelectedValue();
			listmodel.removeElement(selections);
		}
	}
	
	private class EditListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			n.setEditable(true);
			a.setEditable(true);
			s.setEditable(true);
			h.setEditable(true);
			w.setEditable(true);
			history.setEditable(true);
			history.setBackground(Color.WHITE);
		}
	}
	
	private class ListListener implements ListSelectionListener	{
		public void valueChanged(ListSelectionEvent e){
			//if(listIsDisabled) return; // When adding new patient
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
}
