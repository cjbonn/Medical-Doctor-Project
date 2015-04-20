import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class SecretaryPanel extends JPanel {

	private JLabel title;
	private JList list;
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

		displayPatientList();
	}
	
	public void displayPatientList(){
		JPanel patientPanel = new JPanel(new GridLayout(2,1));
		items = new DefaultListModel();
		ArrayList<DBResult> patientList = DB.getPatients();
		for(DBResult r : patientList){
			items.addElement(new PatientData((int) r.get("id"),(String) r.get("fname"),(String) r.get("lname")));
		}
		list = new JList(items);
		list.setVisibleRowCount(20);
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
	
	private void updatePatientData(PatientData p){
		
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
		}
	}
}