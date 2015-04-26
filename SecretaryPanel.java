import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SecretaryPanel extends JPanel {

	private JLabel title;
	private JPanel infoPanel;
	private JList list;
	private int currentPatientID;
	private ArrayList<PairedValue> doctorList;
	private JTextField fname,mname,lname,weight,height,address,city,state,zip;
	private JComboBox month,day,year,sex,insurance,doctor;
	private JButton add,remove,save,cancel;
	private DefaultListModel items;
	private DefaultComboBoxModel ditems,iitems;
	private ProjectDB DB = new ProjectDB();
	private boolean listIsDisabled = false;
	
	public SecretaryPanel(){
		setLayout(new BorderLayout());
		
		title = new JLabel("Patient Database");
		title.setFont(new Font("Cambria",Font.BOLD,18));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);

		buildPatientList();
		buildPatientInfo();
	}
	
	public void buildPatientList(){
		JPanel patientPanel = new JPanel(new GridLayout(2,1));
		items = new DefaultListModel();
		ArrayList<DBResult> patientList = DB.getPatients();
		for(DBResult r : patientList){
			items.addElement(new PatientData((int) r.get("id"),(String) r.get("fname"),(String) r.get("lname")));
		}
		list = new JList(items);
		list.setVisibleRowCount(40);
		list.addListSelectionListener(new ListListener());
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBorder(BorderFactory.createTitledBorder("Patients:"));
		patientPanel.add(listScrollPane);
		
		JPanel buttonPanel = new JPanel();
		remove = new JButton(new ImageIcon("icon_delete.png"));
		remove.setPreferredSize(new Dimension(32,32));
		remove.setActionCommand("remove");
		remove.addActionListener(new ButtonListener());
		buttonPanel.add(remove);
		
		buttonPanel.add(new JLabel(" "));
		
		add = new JButton(new ImageIcon("icon_add.png"));
		add.setPreferredSize(new Dimension(32,32));
		add.setActionCommand("add");
		add.addActionListener(new ButtonListener());
		buttonPanel.add(add);

		patientPanel.add(buttonPanel);
		add(patientPanel, BorderLayout.WEST);
	}
	
	public void buildPatientInfo(){
		infoPanel = new JPanel();
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Full Name:"));
		fname = new JTextField(7);
		namePanel.add(fname);
		mname = new JTextField(1);
		namePanel.add(mname);
		lname = new JTextField(7);
		namePanel.add(lname);
		infoPanel.add(namePanel);
		
		JPanel datePanel = new JPanel();
		datePanel.add(new JLabel("Date of Birth: "));
		String[] monthData = {"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
		String[] dayData = new String[32]; dayData[0] = "Day";
		String[] yearData = new String[100]; yearData[0] = "Year";
		for(int i=1;i<100;i++){
			yearData[i] = String.valueOf((1915+i));
			if(i <= 31) dayData[i] = String.valueOf(i);
		}
		month = new JComboBox<String>(monthData);
		day = new JComboBox<String>(dayData);
		year = new JComboBox<String>(yearData);
		datePanel.add(month); datePanel.add(day); datePanel.add(year);
		infoPanel.add(datePanel);
		
		JPanel whPanel = new JPanel();
		whPanel.add(new JLabel("Sex: "));
		String[] sexData = {"Sex","Male","Female"};
		sex = new JComboBox<String>(sexData);
		whPanel.add(sex);
		whPanel.add(new JLabel("Weight: "));
		weight = new JTextField(3);
		whPanel.add(weight);
		whPanel.add(new JLabel("Height (in inches): "));
		height = new JTextField(2);
		whPanel.add(height);
		infoPanel.add(whPanel);
		
		JPanel addressPanel = new JPanel();
		addressPanel.add(new JLabel("Address: "));
		address = new JTextField(20);
		addressPanel.add(address);
		infoPanel.add(addressPanel);
		
		JPanel cszPanel = new JPanel();
		cszPanel.add(new JLabel("City:"));
		city = new JTextField(10);
		cszPanel.add(city);
		cszPanel.add(new JLabel("State:"));
		state = new JTextField(2);
		cszPanel.add(state);
		cszPanel.add(new JLabel("Zip Code:"));
		zip = new JTextField(5);
		cszPanel.add(zip);
		infoPanel.add(cszPanel);
		
		JPanel doctorPanel = new JPanel();
		doctorPanel.add(new JLabel("Doctor:"));
		ditems = new DefaultComboBoxModel();
		ditems.addElement("");
		doctorList = new ArrayList<PairedValue>();
		ArrayList<DBResult> docdbr = DB.getDoctorList();
		for(DBResult r : docdbr) doctorList.add(new PairedValue((int) r.get("id"),"Dr. "+(String) r.get("lname")));
		for(PairedValue pv : doctorList) ditems.addElement(pv);	
		doctor = new JComboBox(ditems);
		doctorPanel.add(doctor);
		infoPanel.add(doctorPanel);
		
		JPanel insurancePanel = new JPanel();
		insurancePanel.add(new JLabel("Insurance:"));
		iitems = new DefaultComboBoxModel();
		iitems.addElement("");
		ArrayList<DBResult> idbr = DB.getInsuranceList();
		for(DBResult r : idbr){
			iitems.addElement(new PairedValue((int) r.get("id"),(String) r.get("name")));
		}
		insurance = new JComboBox(iitems);
		insurance.setEditable(true);
		insurancePanel.add(insurance);
		infoPanel.add(insurancePanel);
		
		JPanel buttonPanel = new JPanel();
		save = new JButton("Edit");
		save.setActionCommand("edit");
		save.addActionListener(new ButtonListener());
		buttonPanel.add(save);
		buttonPanel.add(new JLabel("  "));
		cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(new ButtonListener());
		buttonPanel.add(cancel);
		infoPanel.add(buttonPanel);
		
		infoPanel.setBorder(BorderFactory.createTitledBorder("Patient Info:"));
		add(infoPanel, BorderLayout.CENTER);
		
		disableForm();
	}
	
	private void updatePatientData(PatientData p){
		currentPatientID = p.getPatientID();
		fname.setText(p.getFirstName());
		mname.setText(p.getMiddleInitial());
		lname.setText(p.getLastName());
		weight.setText(String.valueOf(p.getWeight()));
		height.setText(String.valueOf(p.getHeight()));
		Date date = p.getDOB();
		String m = new SimpleDateFormat("MMMMMMMM").format(date);
		String d = new SimpleDateFormat("dd").format(date);
		String y = new SimpleDateFormat("yyyy").format(date);
		month.setSelectedItem(m);
		day.setSelectedIndex(Integer.parseInt(d));
		year.setSelectedItem(y);
		sex.setSelectedIndex(p.getSex()+1);
		address.setText(p.getAddress());
		city.setText(p.getCity());
		state.setText(p.getState());
		zip.setText(p.getZipCode());
		for(int i=0;i<doctorList.size();i++){
			if(doctorList.get(i).getName().equalsIgnoreCase(p.getDoctorName())) doctor.setSelectedIndex(i+1);
		}
		insurance.setSelectedItem(p.getInsuranceName());
	}
	
	private void resetPatientData(){
		currentPatientID = 0;
		fname.setText("");
		mname.setText("");
		lname.setText("");
		weight.setText("");
		height.setText("");
		month.setSelectedIndex(0);
		day.setSelectedIndex(0);
		year.setSelectedIndex(0);
		sex.setSelectedIndex(0);
		address.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		doctor.setSelectedIndex(0);
		insurance.setSelectedIndex(0);
	}
	
	private void disableForm(){
		for(Component c : infoPanel.getComponents()){
			JPanel pl = (JPanel) c;
			for(Component cc : pl.getComponents()){
				cc.setFocusable(false);
			}
		}
	}
	
	private void enableForm(){
		for(Component c : infoPanel.getComponents()){
			JPanel pl = (JPanel) c;
			for(Component cc : pl.getComponents()){
				cc.setFocusable(true);
			}
		}
	}
	
	class PairedValue {
		String name;
		int id;
		
		public PairedValue(int id, String str){
			this.name = str;
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
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			switch(e.getActionCommand()){
			case "save":
				if(updatePatient()){
					listIsDisabled = false;
					save.setText("Edit");
					save.setActionCommand("edit");
					disableForm();
					((PatientData) list.getSelectedValue()).isLoaded(false);
				}
				break;
				
			case "edit":
				listIsDisabled = true;
				save.setText("Save");
				save.setActionCommand("save");
				enableForm();
				break;
				
			case "cancel":
				listIsDisabled = false;
				resetPatientData();
				list.clearSelection();
				disableForm();
				save.setText("Edit");
				save.setActionCommand("edit");
				break;
				
			case "add":
				listIsDisabled = true; 
				enableForm();
				resetPatientData();
				save.setText("Add Patient");
				save.setActionCommand("insert");
				break;
			
			case "insert":
				listIsDisabled = false;
				save.setText("Edit");
				save.setActionCommand("edit");
				disableForm();
				break;
				
			case "remove":
				resetPatientData();
				break;
			}
		}
		
		private boolean updatePatient(){
			String firstname = fname.getText();
			String middlename = mname.getText();
			String lastname = lname.getText();
			
			int monthVal = month.getSelectedIndex();
			int dayVal = day.getSelectedIndex();
			int yearVal = (year.getSelectedIndex() > 0) ? Integer.parseInt(year.getSelectedItem().toString()) : -1;
			
			int sexVal = sex.getSelectedIndex()-1; // 0 = Male, 1 = Female
			String weightVal = weight.getText();
			String heightVal = height.getText();
			
			String addressVal = address.getText();
			String cityVal = city.getText();
			String stateVal = state.getText();
			String zipVal = zip.getText();
			
			int doctorid = (doctor.getSelectedItem() instanceof PairedValue) ?((PairedValue) doctor.getSelectedItem()).getID() : -1;
			int insuranceid = checkInsurance(insurance.getSelectedItem());
			
			if(firstname.equalsIgnoreCase("") || middlename.equalsIgnoreCase("") || lastname.equalsIgnoreCase("") || addressVal.equalsIgnoreCase("") ||
			   cityVal.equalsIgnoreCase("") || stateVal.equalsIgnoreCase("") || zipVal.equalsIgnoreCase("") || sexVal < 0 || yearVal < 0 ||
			   monthVal <= 0 || dayVal <= 0 || !isNumeric(weightVal) || !isNumeric(heightVal) ||
			   doctorid <= 0 || insuranceid <= 0){
				JOptionPane.showMessageDialog(null, "Could not submit, there are invalid inputs.", "Update Patient", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			int weightInt = Integer.parseInt(weightVal);
			int heightInt = Integer.parseInt(heightVal);
			String monthStr = (monthVal < 10) ? "0"+String.valueOf(monthVal) : String.valueOf(monthVal);
			String dayStr = (dayVal < 10) ? "0"+String.valueOf(dayVal) : String.valueOf(dayVal);
			String dateofbirth = yearVal+"-"+monthStr+"-"+dayStr;
			if(DB.updatePatient(currentPatientID, firstname,middlename,lastname, sexVal, dateofbirth, heightInt, weightInt, addressVal, cityVal, stateVal, zipVal, doctorid, insuranceid)){
				JOptionPane.showMessageDialog(null, "Patient updated successfully.", "Update Patient", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}else{
				JOptionPane.showMessageDialog(null, DB.getError(), "Update Patient", JOptionPane.ERROR_MESSAGE);
				System.out.println(DB.getErrorInfo());
				return false;
			}
		}
		
		private int checkInsurance(Object o){
			if("".equalsIgnoreCase(o.toString())) return -1;
			if(o instanceof PairedValue) return ((PairedValue) o).getID();
			ArrayList<DBResult> dbr = DB.query("SELECT id FROM insurance WHERE name='"+o.toString()+"' LIMIT 1");
			if(dbr.isEmpty() || dbr.size() < 1){ // insurance doesn't exist
				int newID = DB.addNewInsurance(o.toString());
				iitems.addElement(new PairedValue(newID,o.toString()));
				insurance.setSelectedIndex(iitems.getSize()-1);
				return newID;
			}else{
				DBResult result = dbr.get(0);
				return (int) result.get("id");
			}
		}
		
		public boolean isNumeric(String str){  
			try {  
				int d = Integer.parseInt(str);  
			}catch(Exception e){  
				return false;  
			}  
			return true;  
		}
	}
	
	private class ListListener implements ListSelectionListener	{
		public void valueChanged(ListSelectionEvent e){
			if(listIsDisabled) return; // When adding new patient
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