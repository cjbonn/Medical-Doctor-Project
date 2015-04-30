import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class NursePanel extends JPanel {

	private JLabel name,age,height,weight;
	private JTextField n,a,h,w,dName;
	private JList history,list;
	private ArrayList<JCheckBox> pres;
	private JPanel nameList,patientInfo,info,comboPanel,pre,buttonPanel;
	private int currentPatientID,patientAge;
	private boolean listIsDisabled = false;
	private ArrayList<JCheckBox> pcbs;
	private ProjectDB DB;
	private DefaultListModel hitems,items;

	public NursePanel(ProjectDB dbh){
		this.DB = dbh;
		setLayout(new BorderLayout());
		buildList();
		buildPatient();
		buildPrescription();
		comboPanel = new JPanel();
		comboPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		comboPanel.add(patientInfo,c);
		c.gridy=1; c.weighty = 100;
		comboPanel.add(pre,c);
		add(nameList,BorderLayout.WEST);
		add(comboPanel,BorderLayout.CENTER);
	}

	// Create the List of patients that need prescriptions administered
	public void buildList(){
		nameList = new JPanel();
		nameList.setPreferredSize(new Dimension(200,200));
		items = new DefaultListModel();
		ArrayList<DBResult> patientList = DB.getPatientsNeedingPrescriptionList();
		if(patientList.size() < 1 || patientList.isEmpty()) items.addElement("No patients need attention.");
		for(DBResult r : patientList) items.addElement(new PatientData((int) r.get("id"),(String) r.get("fname"),(String) r.get("lname")));
		list = new JList(items);
		list.setVisibleRowCount(40);
		list.addListSelectionListener(new ListListener());
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(new Dimension(200,listScrollPane.getPreferredSize().height));
		listScrollPane.setBorder(BorderFactory.createTitledBorder("Patients:"));
		nameList.add(listScrollPane);
	}

	// Create the infobox with patients' details
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
		history = new JList(hitems); history.setPreferredSize(new Dimension(250,150));
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

	// Create the list of needed prescriptions
	public void buildPrescription(){
		pre = new JPanel();
		pre.setLayout(new GridLayout(0,1));
		pres = new ArrayList<JCheckBox>();
		pre.setBorder(BorderFactory.createTitledBorder("Prescriptions:"));
		pre.add(new JLabel("No prescriptions."));
		pcbs = new ArrayList<JCheckBox>();
	}

	// Update the infobox with the currently selected patient's information
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
		pre.removeAll();
		ArrayList<DBResult> pres = DB.getPrescriptionsForID(currentPatientID,true);
		if(pres.size() < 1 || pres.isEmpty()){
			 pre.add(new JLabel("No prescriptions."));
		}else{
			JCheckBox example = new JCheckBox("Prescription administered.");
			example.setEnabled(false); example.setSelected(true);
			pre.add(example);
			for(DBResult pdbr : pres){
				JCheckBox temp = new JCheckBox((String) pdbr.get("pname")+" ("+(String) pdbr.get("abbr")+")");
				temp.setActionCommand(String.valueOf(pdbr.get("id")));
				temp.addActionListener(new PrescriptionListener());
				pcbs.add(temp);
			}
			for(JCheckBox cbs : pcbs) pre.add(cbs);
		}
	}

	// Reset the infobox
	private void resetPatientData(){
		currentPatientID = -1;
		n.setText("");
		a.setText("");
		w.setText("");
		h.setText("");
		h.setText("");
		hitems.clear();
		for(JCheckBox cb : pres) cb.setSelected(false);
		dName.setText("");
		list.clearSelection();
	}

	// Used for Key=>Val items (JList)
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
	
	// Handles when a prescription's checkbox has been checked.
	private class PrescriptionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JCheckBox cb = (JCheckBox) e.getSource();
			int pid = Integer.parseInt(cb.getActionCommand());
			if(updatePrescription(pid)) cb.setEnabled(false);
		}
		
		private boolean updatePrescription(int id){
			if(!DB.updatePrescription(id)){
				JOptionPane.showMessageDialog(null, DB.getError(), "Prescription", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
	}

	// Handles when the patient list has a patient selected
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

	// Handles when a patient's past visits are double clicked
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
