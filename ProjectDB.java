import java.sql.*;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProjectDB extends DB {

	private String error, errorInfo;
	private int user_id,type;
	private String fname,lname;
	
	public ArrayList<DBResult> query(String command){
		try {
			return super.query(command);
		} catch(SQLException e){
			setError("There was a problem querying the database.");
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			return new ArrayList<DBResult>();
		}
	}
	
	public boolean login(String username, char[] passwd){
		String password = new String(passwd);
		String u = username;
		String p = getHash(password);
		try {
			String query = "SELECT id,fname,lname,type FROM users WHERE username=? AND password=? LIMIT 1";
			Connection conn = this.getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1,username); stmt.setString(2, p);
			ResultSet rs = stmt.executeQuery();
			boolean exists = rs.next();
			if(!exists){
				setError("Login/Password Incorrect.");
			}else{
				this.user_id = rs.getInt("id");
				this.fname = rs.getString("fname");
				this.lname = rs.getString("lname");
				this.type = rs.getInt("type");
			}
			rs.close(); stmt.close();
			return exists;
		} catch (SQLException e){
			setErrorInfo(e.getMessage());
		}
		return false;
	}
	
	public static String getHash(String str) {
        if (str == null || str.length() <= 4) {
            throw new IllegalArgumentException("Password must be more than 4 characters in length.");
        }
        MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("MD5");
			digester.update(str.getBytes());
	        byte[] hash = digester.digest();
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < hash.length; i++) {
	            if ((0xff & hash[i]) < 0x10) {
	                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
	            }
	            else {
	                hexString.append(Integer.toHexString(0xFF & hash[i]));
	            }
	        }
	        return hexString.toString();
		} catch (NoSuchAlgorithmException e) {}
		return str;
    }

	public ArrayList<DBResult> getPatients(boolean isDoctor) {
		String append = (isDoctor) ? " WHERE doctorid="+user_id : "";
		return this.query("SELECT id,fname,lname FROM patients"+append+" ORDER BY lname,fname");
	}
	
	public ArrayList<DBResult> getDoctorList(){
		return this.query("SELECT id,lname FROM users WHERE type=0 ORDER BY lname");
	}
	
	public ArrayList<DBResult> getInsuranceList(){
		return this.query("SELECT id,name FROM insurance ORDER BY name");
	}
	
	public ArrayList<DBResult> getPrescriptionList(){
		return this.query("SELECT id,prescription,abbr FROM prescriptiontype ORDER BY id");
	}
	
	public ArrayList<DBResult> getLabTestList(){
		return this.query("SELECT id,test FROM labtype ORDER BY test");
	}
	
	public ArrayList<DBResult> getPatientsNeedingPrescriptionList(){
		return this.query("SELECT * FROM (SELECT P.id,P.fname,P.lname FROM prescriptions R INNER JOIN visits V ON R.visit_id=V.id INNER JOIN patients P ON P.id=V.patientid WHERE R.completed=0 GROUP BY P.id) Tbl ORDER BY id");
	}
	
	public int addNewInsurance(String name){
		try {
			return this.insert("INSERT INTO insurance VALUES (NULL,'"+name+"')");
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			return -1;
		}
	}

	public int addNewPatient(int userid, String fname, String mname,
			String lname, int sex, String dob, int height,
			int weight, String address, String city,
			String state, String zip, int doctorid, int insuranceid) {
		try {
			return this.insert("INSERT INTO patients VALUES (NULL,'"+fname+"','"+mname+"','"+lname+"',"
					+ doctorid+","+sex+",'"+dob+"',"+height+","+weight+","
					+ "'"+address+"','"+city+"','"+state+"','"+zip+"',"+insuranceid+")");
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			setError("Failed to update Patient.");
			return -1;
		}
	}
	
	public int addNewVisit(int patientid, String complaint, String symptoms, String physical, String illness, String impression, String diagnosis){
		try {
			return this.insert("INSERT INTO visits VALUES (NULL,"+patientid+",NULL,'"+complaint+"','"+symptoms+"','"
								+physical+"','"+illness+"','"+impression+"','"+diagnosis+"')");
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			setError("Failed to add visit.");
			return -1;
		}
	}
	
	public boolean addVisitLabTests(int visitid, int lab){
		try {
			return this.execute("INSERT INTO labs VALUES (NULL,"+visitid+","+lab+")");
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			setError("Failed to add lab tests.");
			return false;
		}
	}
	
	public boolean addVisitPrescriptions(int visitid, int ptype, String pname){
		try {
			return this.execute("INSERT INTO prescriptions VALUES (NULL,"+visitid+","+ptype+",'"+pname+"',0)");
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			setError("Failed to add prescriptions.");
			return false;
		}
	}

	public boolean updatePatient(int userid, String fname, String mname,
			String lname, int sex, String dob, int height,
			int weight, String address, String city,
			String state, String zip, int doctorid, int insuranceid) {
		try {
			return this.execute("UPDATE patients SET fname='"+fname+"',minitial='"+mname+"',lname='"+lname+"',"
					+ "doctorid="+doctorid+",sex="+sex+",dob='"+dob+"',height="+height+",weight="+weight+","
					+ "address='"+address+"',city='"+city+"',state='"+state+"',zip='"+zip+"',insuranceid="+insuranceid+" WHERE id="+userid);
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			setError("Failed to update Patient.");
			return false;
		}
	}
	
	public boolean updatePrescription(int id){
		try {
			return this.execute("UPDATE prescriptions SET completed=1 WHERE id="+id+" LIMIT 1");
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			setError("Failed to save prescription.");
			return false;
		}
	}
	
	public boolean removePatient(int id){
		try {
			return this.execute("DELETE FROM patients WHERE id="+id+" LIMIT 1");
		} catch (SQLException e) {
			setErrorInfo(Thread.currentThread().getStackTrace()[1].getMethodName()+": "+e.getMessage());
			setError("Failed to delete patient.");
			return false;
		}
	}
	
	public ArrayList<DBResult> getPatientHistory(int id){
		return this.query("SELECT id,visit_date,complaint FROM visits WHERE patientid="+id+" ORDER BY visit_date DESC");
	}
	
	public ArrayList<DBResult> getVisitByID(int id){
		return this.query("SELECT * FROM visits WHERE id="+id+" LIMIT 1");
	}
	
	public ArrayList<DBResult> getLabsForID(int id){
		return this.query("SELECT T.test FROM labs L INNER JOIN labtype T ON L.ltype = T.id WHERE L.visit_id="+id);
	}
	
	public ArrayList<DBResult> getPrescriptionsForID(int id, boolean whereNotCompleted){
		String append = (whereNotCompleted) ? "completed=0 AND " : "";
		return this.query("SELECT P.id,T.abbr,P.pname FROM prescriptions P INNER JOIN prescriptiontype T ON P.ptype = T.id WHERE "+append+"P.visit_id="+id);
	}
	
	public int getUserID(){
		return this.user_id;
	}
	
	public int getAccountType(){
		return this.type;
	}
	
	public String getFirstName(){
		return this.fname;
	}
	
	public String getLastName(){
		return this.lname;
	}
	
	public String getFullName(){
		return getFirstName()+" "+getLastName();
	}
	
	private void setError(String str){
		this.error = str;
	}
	
	private void setErrorInfo(String str){
		this.errorInfo = str;
	}
	
	public String getError(){
		return this.error;
	}

	public String getErrorInfo() {
		return this.errorInfo;
	}
}
