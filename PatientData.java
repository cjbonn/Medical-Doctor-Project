import java.sql.Date;
import java.util.ArrayList;

public class PatientData {
	
	private String fname,mname,lname,address,city,state,zip,insurance,doctor;
	private int id,height,weight,insuranceid,sex,doctorid;
	private Date dob;
	private boolean isLoaded;
	
	public PatientData(int id, String fname, String lname){
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		insurance = "";
		doctor = "";
		isLoaded(false);
	}
	
	public String toString(){
		return lname+", "+fname;
	}
	
	public void loadPatientInfo(){
		ProjectDB DB = new ProjectDB();
		ArrayList<DBResult> dbr = DB.query("SELECT * FROM patients WHERE id="+id+" LIMIT 1");
		DBResult result = dbr.get(0);
		mname = (String) result.get("minitial");
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
		return fname+" "+mname+" "+lname;
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
	
	public int getSex(){
		return sex;
	}
	
	public int getDoctorID(){
		return doctorid;
	}
	
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
	
	public void isLoaded(boolean b){
		isLoaded = b; doctor = ""; insurance = "";
	}
	
	public boolean isLoaded(){
		return isLoaded;
	}
}
