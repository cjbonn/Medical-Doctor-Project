import java.sql.*;
import java.util.*;
public class DB {
	
	private String server = "localhost",
				   port = "3306",
			   	   db = "csci400",
				   user = "root",
				   pass = "";
	
	private Connection conn;
	
	public DB(){
		this.connect();
	}
	
	// Runs the connection, handles errors.
	public void connect(){
		try {
			this.setConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			// e.printStackTrace();
			return;
		}
	}

	// Connects to database. Connection saved in variable.
	public void setConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC not found.");
		}
		conn = DriverManager.getConnection("jdbc:mysql://" + this.server + ":" + this.port + "/" + this.db, this.user, this.pass);
		this.conn = conn;
	}
	
	// Run query that doesn't return anything: CREATE/UPDATE/DELETE/DROP/etc
	public boolean execute(String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {
	        if(stmt != null) stmt.close();
	    }
	}
	
	// Runs INSERT query, returns the ID of the newly inserted data.
	public int insert(String command) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            }else{
	                throw new SQLException("Insert failed.");
	            }
	        }
		} finally {
			if(stmt != null) stmt.close();
		}
	}
	
	/*
	 * Runs any query. Will return results. Use execute if not expecting results.
	 * Stores each row of data in an array of DBResults.
	 */
	public ArrayList<DBResult> query(String command) throws SQLException {
		Statement stmt = null;
		ArrayList<DBResult> dbr = new ArrayList<DBResult>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				DBResult result = new DBResult();
				int cols = rsmd.getColumnCount();
				for(int i=1;i<=cols;i++){
					result.add(rsmd.getColumnName(i),rs.getObject(i));
				}
				dbr.add(result);
			}
		} finally {
			if(stmt != null) { stmt.close(); }
		}
		return dbr;
	}
	
	public Connection getConnection(){
		if(conn == null){
			this.connect();
		}
		return this.conn;
	}
}