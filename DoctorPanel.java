import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DoctorPanel extends JPanel {

	private JLabel name,age,height,weight,cComplaint,presill,impression,diagnosis;
	private JTextField n,a,h,w,cc,illness,impress,diag,dName;
	private JTextArea review,exam;
	private JButton submit,cancel,movRight,movLeft;
	private JList history,perscript,selected,list;
	private JTabbedPane tab;
	private ArrayList<JCheckBox> labTests;
	private JPanel nameList,patientInfo,info,docGuide,comboPanel,labTest,script,buttonPanel,movButton;
	private int currentPatientID,patientAge;
	private boolean listIsDisabled = false;
	private ProjectDB DB;
	private DefaultListModel listmodel,hitems,items;
	
	public DoctorPanel(ProjectDB dbh){
		this.DB = dbh;
		setLayout(new BorderLayout());
		tab = new JTabbedPane();
		buildList();
		buildPatient();
		buildDocguide();
		buildLab();
		buildPerscript();
		submit = new JButton("Add Visit");
		submit.setActionCommand("add");
		submit.addActionListener(new ButtonListener());
		submit.setEnabled(false);
		cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(new ButtonListener());
		cancel.setEnabled(false);
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
		labTest.setLayout(new GridLayout(0,2));
		labTests = new ArrayList<JCheckBox>();
		ArrayList<DBResult> dbr = DB.getLabTestList();
		for(DBResult labs : dbr){
			JCheckBox tempCheck = new JCheckBox((String) labs.get("test"));
			tempCheck.setActionCommand(String.valueOf(labs.get("id")));
			labTests.add(tempCheck);
		}
		for(JCheckBox cb : labTests) labTest.add(cb);
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
		System.out.println("Width: "+history.getSize().width + ". Height: "+ history.getSize().height);
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
		submit.setEnabled(true);
		cancel.setEnabled(true);
	}
	
	private void resetPatientData(){
		currentPatientID = -1;
		n.setText("");
		a.setText("");
		w.setText("");
		h.setText("");
		h.setText("");
		cc.setText("");
		illness.setText("");
		impress.setText("");
		diag.setText("");
		review.setText("");
		exam.setText("");
		hitems.clear();
		for(JCheckBox cb : labTests) cb.setSelected(false);
		dName.setText("");
		listmodel.clear();
		list.clearSelection();
		tab.setSelectedIndex(0);
		submit.setEnabled(false);
		cancel.setEnabled(false);
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
				
			case "add":
				if(addVisit()) resetPatientData();
				break;
				
			case "cancel":
				resetPatientData();
				break;
			}
		}
		
		private boolean addVisit(){
			String ccv = cc.getText();
			String illnessv = illness.getText();
			String impressv = impress.getText();
			String diagv = diag.getText();
			String reviewv = review.getText();
			String examv = exam.getText();
			
			if(ccv.equalsIgnoreCase("")){
				JOptionPane.showMessageDialog(null, "Cannot submit without a visit reason.", "New Visit", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			try {
				DB.getConnection().setAutoCommit(false);
				// Visit Insert
				int newID = DB.addNewVisit(currentPatientID, ccv, reviewv, examv, illnessv, impressv, diagv);
				if(newID < 0){
					System.out.println(DB.getErrorInfo());
					JOptionPane.showMessageDialog(null, DB.getError(), "New Visit", JOptionPane.ERROR_MESSAGE);
					DB.getConnection().rollback();
					return false;
				}
				// Lab Tests Insert
				for(JCheckBox cb : labTests){
					if(cb.isSelected()){
						if(!DB.addVisitLabTests(newID, Integer.parseInt(cb.getActionCommand()))){
							DB.getConnection().rollback();
							System.out.println(DB.getErrorInfo());
							JOptionPane.showMessageDialog(null, DB.getError(), "New Visit", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
				}
				// Prescriptions Insert
				for(Object pv : listmodel.toArray()){
					PairedValue2 obj = (PairedValue2) pv;
					if(!DB.addVisitPrescriptions(newID, obj.getID(), obj.val2)){
						DB.getConnection().rollback();
						System.out.println(DB.getErrorInfo());
						JOptionPane.showMessageDialog(null, DB.getError(), "New Visit", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
				DB.getConnection().commit();
				JOptionPane.showMessageDialog(null, "New visit added successfully", "New Visit", JOptionPane.INFORMATION_MESSAGE);
				((PatientData) list.getSelectedValue()).isLoaded(false);
				return true;
			} catch (SQLException e) {
				System.out.println(DB.getErrorInfo());
				JOptionPane.showMessageDialog(null, DB.getError(), "New Visit", JOptionPane.ERROR_MESSAGE);
				return false;
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
