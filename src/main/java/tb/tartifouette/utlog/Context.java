package tb.tartifouette.utlog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Context {

    private String currentMap;

    private Map<String, String> clientIds = new HashMap<String, String>();
    
    private Map<String, String> userTeam = new HashMap<String, String>();
    
    
    private static Context instance = new Context();

    private Context() {
    }

    public static Context getInstance() {
        return instance;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

	public String putClientId(String key, String value) {
		return clientIds.put(key, value);
	}

	public String getUsername(Object key) {
		return clientIds.get(key);
	}

	public void setTeamComposition(String team, String user) {
		userTeam.put(user,team);
			}
    
    public boolean areUsersSameTeam(String user1, String user2){
    	String team1 = userTeam.get(user1);
    	String team2 = userTeam.get(user2);
    	return team1!=null && team1.equals(team2);
    	
    }

}
