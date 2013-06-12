package tb.tartifouette.utlog;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class AliasManager {
	private static final Logger log = Logger.getLogger(AliasManager.class);

	private final Map<String, String> aliasesMap;
	private final Map<String, Set<String>> possibleAlias;
	private static final Pattern pComma = Pattern.compile(",");

	public AliasManager() {
		aliasesMap = new HashMap<String, String>();
		possibleAlias = new HashMap<String, Set<String>>();
	}

	public void init(Properties props) {
		log.info("Init from properties");
		aliasesMap.clear();
		possibleAlias.clear();
		String userNamesS = props.getProperty("aliases.mainNames");
		List<String> userNames = getPropertyList(userNamesS);
		for (String userName : userNames) {
			String aliasList = props.getProperty("aliases.list." + userName);
			if (aliasList != null) {
				List<String> aliases = getPropertyList(aliasList);
				for (String alias : aliases) {
					aliasesMap.put(alias, userName);
				}
			}
		}
	}

	private List<String> getPropertyList(String userNamesS) {
		String[] userNames = pComma.split(userNamesS);
		return Arrays.asList(userNames);
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
