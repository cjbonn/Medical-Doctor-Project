import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class ProjectDB extends DB {

	private String error, errorInfo;
	private int user_id,type;
	private String fname,lname;
	
	public ProjectDB(){
		
	}
	
	public ArrayList<DBResult> query(String command){
		try {
			return super.query(command);
		} catch(SQLException e){
			setError("There was a problem querying the database.");
			setErrorInfo(e.getMessage());
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

	public ArrayList<DBResult> getPatients() {
		return this.query("SELECT id,fname,lname FROM patients ORDER BY lname,fname");
	}
	
	public ArrayList<DBResult> getDoctorList(){
		return this.query("SELECT id,lname FROM users WHERE type=0 ORDER BY lname");
	}
	
	public ArrayList<DBResult> getInsuranceList(){
		return this.query("SELECT id,name FROM insurance ORDER BY name");
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
