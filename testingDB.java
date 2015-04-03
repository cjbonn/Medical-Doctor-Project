import java.util.ArrayList;
import java.util.prefs.Preferences;

public class testingDB {
	
	public ProjectDB ProjectDB = new ProjectDB();

	public static void main(String[] args){
		new testingDB();
	}
	
	public testingDB(){
		System.out.println("Running query...");
		ArrayList<DBResult> result = ProjectDB.query("SELECT fname,lname FROM jdbc_test");
		if(result.isEmpty() || result.size() < 1){
			System.out.println("No Results.\nError: " + ProjectDB.getError() + "\nErrorInfo: "+ ProjectDB.getErrorInfo());
		}
		for(DBResult r : result){
			System.out.println(r.toString());
			System.out.println(r.get("fname")+" "+r.get("lname"));
		}
	}
}