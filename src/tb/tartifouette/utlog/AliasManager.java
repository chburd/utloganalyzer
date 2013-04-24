package tb.tartifouette.utlog;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class AliasManager {

	private Map<String, String> aliasesMap;
	private Map<String, Set<String>> possibleAlias;
	private static final Pattern pComma = Pattern.compile(",");
	private static AliasManager instance = new AliasManager();

	private AliasManager() {
		aliasesMap = new HashMap<String, String>();
		possibleAlias = new HashMap<String, Set<String>>();
		initFromConfig();
	}

	private void initFromConfig() {
		Properties props = new Properties();
		try {
			String aliasFileName = System.getProperty("alias.file");
			if (aliasFileName != null) {
				props.load(new FileInputStream(aliasFileName));
				String userNamesS = props.getProperty("aliases.mainNames");
				List<String> userNames = getPropertyList(userNamesS);
				for (String userName : userNames) {
					String aliasList = props.getProperty("aliases.list."
							+ userName);
					if (aliasList != null) {
						List<String> aliases = getPropertyList(aliasList);
						for (String alias : aliases) {
							aliasesMap.put(alias, userName);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}

	}

	private List<String> getPropertyList(String userNamesS) {
		String[] userNames = pComma.split(userNamesS);
		return Arrays.asList(userNames);
	}

	public static AliasManager getInstance() {
		return instance;
	}

	public String resolveUserName(String alias) {
		String aliasUser = aliasesMap.get(alias);
		return aliasUser == null ? alias : aliasUser;
	}

	public void addPossibleAlias(String ip, String user) {
		Set<String> aliases = possibleAlias.get(ip);
		if (aliases == null) {
			aliases = new HashSet<String>();
		}
		aliases.add(user);
		possibleAlias.put(ip, aliases);
	}

	public Collection<Set<String>> getPossibleAliases() {
		return possibleAlias.values();
	}

}
