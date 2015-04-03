import java.util.HashMap;
import java.util.Map;

// Stores one row of a database result set.
public class DBResult {
	
	private Map<String, Object> data = new HashMap<String, Object>();
	
	// Adds key->val pair to result set (ie "first_name"->"Chris")
	public void add(String key, Object val){
		data.put(key,val);
	}
	
	// Return the entire row
	public Map<String, Object> getResult(){
		return data;
	}
	
	// Return a specific column in row
	public Object get(Object key){
		return this.getResult().get(key);
	}
	
	public String toString(){
		return data.toString();
	}
	
}