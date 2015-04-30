import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PatientData {
	
	private String fname,mname,lname,address,city,state,zip,insurance,doctor;
	private int id,height,weight,insuranceid,sex,doctorid;
	private ArrayList<DBResult> history;
	private Date dob;
	private boolean isLoaded;
	
	public PatientData(int id){
		this.id = id;
		this.history = new ArrayList<DBResult>();
		isLoaded(false);
	}
	
	public PatientData(int id, String fname, String lname){
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.history = new ArrayList<DBResult>();
		isLoaded(false);
	}
	
	// Converts the Object into LastName, FirstName string for use in JList
	public String toString(){
		return lname+", "+fname;
	}
	
	// Trigger the loading of all the patient details
	public void loadPatientInfo(){
		ProjectDB DB = new ProjectDB();
		ArrayList<DBResult> dbr = DB.query("SELECT * FROM patients WHERE id="+id+" LIMIT 1");
		DBResult result = dbr.get(0);
		fname = (String) result.get("fname");
		mname = (String) result.get("minitial");
		lname = (String) result.get("lname");
		address = (String) result.get("address");
		city = (String) result.get("city");
		state = (String) result.get("state");
		zip = (String) result.get("zip");
		dob = (Date) result.get("dob");
		height = (int) result.get("height");
		weight = (int) result.get("weight");
		doctorid = (int) result.get("doctorid");
		insuranceid = (int) result.get("insuranceid");
		sex = (int) result.get("sex");
		isLoaded(true);
	}
	
	public int getPatientID(){
		return id;
	}
	
	public String getFirstName(){
		return fname;
	}
	
	public String getLastName(){
		return lname;
	}
	
	public String getMiddleInitial(){
		return mname;
	}
	
	public String getFullName(){
		return fname+" "+mname+". "+lname;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getState(){
		return state;
	}
	
	public String getZipCode(){
		return zip;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getHeightInFeet(){
		return height/12;
	}
	
	public String getFormattedHeight(){
		int inches = height%12;
		return getHeightInFeet()+"'"+inches+"\"";
	}
	
	public Date getDOB(){
		return dob;
	}
	
	// Calculate the current age based on the date of birth and current date
	public int getAge(){
		Date date = this.getDOB();
		int m = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		int d = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		int y = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int mnow = Integer.parseInt(new SimpleDateFormat("MM").format(new java.util.Date()));
		int dnow = Integer.parseInt(new SimpleDateFormat("dd").format(new java.util.Date()));
		int ynow = Integer.parseInt(new SimpleDateFormat("yyyy").format(new java.util.Date()));
		int patientAge = ynow - y;
		if(mnow < m) patientAge--;
		else if(m == mnow && dnow < d) patientAge--;
		return patientAge;
	}
	
	public int getSex(){
		return sex;
	}
	
	public int getDoctorID(){
		return doctorid;
	}
	
	// Get the doctor name based off the doctorid retrieve from loadPatientInfo()
	public String getDoctorName(){
		if(doctor == ""){
			ProjectDB DB = new ProjectDB();
			ArrayList<DBResult> dbr = DB.query("SELECT lname FROM users WHERE id="+doctorid+" LIMIT 1");
			DBResult result = dbr.get(0);
			doctor = (String) result.get("lname");
			return "Dr. " +doctor;
		}else{
			return "Dr. " +doctor;
		}
	}
	
	public int getInsuranceID(){
		return insuranceid;
	}
	
	// Get the insurance name based off the insuranceid retrieve from loadPatientInfo()
	public String getInsuranceName(){
		if(insurance == ""){
			ProjectDB DB = new ProjectDB();
			ArrayList<DBResult> dbr = DB.query("SELECT name FROM insurance WHERE id="+insuranceid+" LIMIT 1");
			DBResult result = dbr.get(0);
			insurance = (String) result.get("name");
			return insurance;
		}else{
			return insurance;
		}
	}
	
	// Return list of past visits by this patient
	public ArrayList<DBResult> getHistory(){
		if(history.size() > 0) return history;
		ProjectDB DB = new ProjectDB();
		history = DB.getPatientHistory(this.getPatientID());
		return history;
	}
	
	// Sets if the object is loaded, if not loaded, will refresh data
	public void isLoaded(boolean b){
		isLoaded = b; 
		if(!b){
			doctor = ""; insurance = ""; history.clear();
		}
	}
	
	public boolean isLoaded(){
		return isLoaded;
	}
}
