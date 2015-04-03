import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class ProjectDB extends DB {

	private String error, errorInfo;
	private int user_id;
	
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
	
	// Temporary testing method
	public void createTable(){
		// Create a table
		try {
		    String createString =
			        "CREATE TABLE jdbc_test ( " +
			        "id INTEGER NOT NULL, " +
			        "fname varchar(40) NOT NULL, " +
			        "lname varchar(40) NOT NULL, " +
			        "age INTEGER, " +
			        "PRIMARY KEY (ID))";
			this.execute(createString);
			System.out.println("Created a table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
	}
	
	// Temporary testing method
	public void dropTable(){
		// Drop the table
		try {
		    String dropString = "DROP TABLE jdbc_test";
			this.execute(dropString);
			System.out.println("Dropped the table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}
	}
	
	public boolean login(String username, String password){
		String u = username;
		String p = getHash(password);
		try {
			String query = "SELECT id FROM users WHERE username=?,password=? LIMIT 1";
			Connection conn = this.getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1,username); stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			boolean exists = rs.next();
			if(!exists){
				setError("Login/Password Incorrect.");
			}else{
				this.user_id = rs.getInt("id");
			}
			rs.close(); stmt.close();
			return exists;
		} catch (SQLException e){
			setErrorInfo(e.getMessage());
		}
		return false;
	}
	
	public static String getHash(String str) {
        if (str == null || str.length() <= 5) {
            throw new IllegalArgumentException("Password must be more than 5 characters in length.");
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
