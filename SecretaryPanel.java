import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SecretaryPanel extends JPanel {

	private JLabel title;
	private JList list;
	private JTextField fname,mname,lname,weight,height;
	private JComboBox month,day,year;
	private JButton add,remove;
	private DefaultListModel items;
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
		remove.setActionCommand("delete");
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
		JPanel infoPanel = new JPanel();
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
		whPanel.add(new JLabel("Weight: "));
		weight = new JTextField(3);
		whPanel.add(weight);
		whPanel.add(new JLabel("Height (in inches): "));
		height = new JTextField(2);
		whPanel.add(height);
		infoPanel.add(whPanel);
		
		infoPanel.setBorder(BorderFactory.createTitledBorder("Patient Info:"));
		add(infoPanel, BorderLayout.CENTER);
	}
	
	private void updatePatientData(PatientData p){
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
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			System.out.println("button clicked");
		}
	}
	
	private class ListListener implements ListSelectionListener	{
		public void valueChanged(ListSelectionEvent e){
			if(listIsDisabled) return; // When adding new patient
			if(e.getValueIsAdjusting()) return;
			PatientData patient = (PatientData) list.getSelectedValue();
			if(!patient.isLoaded()) patient.loadPatientInfo();
			updatePatientData(patient);
			System.out.println(patient.getFullName());
		}
	}
}