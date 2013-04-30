package tb.tartifouette.utlog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

	private static final String ENVIRONMENT = "<non-client>";

	private static final Pattern KILL_LINE = Pattern
			.compile("\\s*(\\d+:\\d+) Kill:\\s\\d+\\s\\d+\\s\\d+:\\s+(\\S+)\\skilled (\\S+) by (.*)");

	private static final Pattern FLAG_LINE = Pattern
			.compile("\\s*\\d+:\\d+ Flag:\\s(\\d+)\\s2:\\s(.*)");

	private static final Pattern STATS_LINE = Pattern
			.compile("\\s*(\\d+:\\d+) score:\\s(\\d+)\\s+ping:\\s+\\d+\\s+client:\\s+\\d+\\s+(.*)");

	private static final Pattern CONNECT_LINE = Pattern
			.compile("\\s+\\d+:\\d+\\s+ClientUserinfo:\\s+(\\d+)\\s+\\\\ip\\\\(\\d+\\.\\d+\\.\\d+\\.\\d+):\\d+\\\\name\\\\(.*)\\\\racered.*");

	private static final Pattern CLIENT_USER_INFO_CHANGED = Pattern
			.compile("\\s+\\d+:\\d+\\s+ClientUserinfoChanged:\\s+(\\d+)\\s+n\\\\(.*)\\\\t\\\\(\\d+)\\\\r.*");

	private static final Pattern MAP_NAME = Pattern
			.compile("\\s+\\d+:\\d+\\s+InitGame:\\s+.*\\\\mapname\\\\([^\\\\]*)\\\\.*");

	private static final String MOD_CHANGE_TEAM = "MOD_CHANGE_TEAM";

	private final Stats stats;

	public LineParser(Stats stats) {
		this.stats = stats;
	}

	public void parseLine(String line) {
		parseKill(line);
		parseFlag(line);
		parseStats(line);
		parseAlias(line);
		parseUserId(line);
		parseMap(line);
	}

	private void parseMap(String line) {
		Matcher matcher = MAP_NAME.matcher(line);
		if (matcher.matches()) {
			Context.getInstance().setCurrentMap(matcher.group(1));
		}

	}

	private void parseUserId(String line) {
		Matcher matcher = CLIENT_USER_INFO_CHANGED.matcher(line);
		if (matcher.matches()) {
			String clientId = matcher.group(1);
			String user = matcher.group(2);
			String team = matcher.group(3);
			Context.getInstance().putClientId(clientId,
					AliasManager.getInstance().resolveUserName(user));
			Context.getInstance().setTeamComposition(team, user);
		}
	}

	private void parseAlias(String line) {
		Matcher matcher = CONNECT_LINE.matcher(line);
		if (matcher.matches()) {
			String clientId = matcher.group(1);
			String ip = matcher.group(2);
			String user = matcher.group(3);
			AliasManager.getInstance().addPossibleAlias(ip, user);
			Context.getInstance().putClientId(clientId,
					AliasManager.getInstance().resolveUserName(user));
		}
	}

	private void parseStats(String line) {
		Matcher matcher = STATS_LINE.matcher(line);
		if (matcher.matches()) {
			int score = Integer.parseInt(matcher.group(2));
			String user = matcher.group(3);
			user = AliasManager.getInstance().resolveUserName(user);
			stats.updateUserScore(user, score);
		}
	}

	private void parseFlag(String line) {
		Matcher matcher = FLAG_LINE.matcher(line);
		if (matcher.matches()) {
			String userId = matcher.group(1);
			String resolvedAlias = Context.getInstance().getUsername(userId);
			String team = matcher.group(2);
			stats.updateTeamFlag(team);
			stats.updateUserFlag(resolvedAlias);
		}
	}

	private void parseKill(String line) {
		Matcher matcher = KILL_LINE.matcher(line);
		if (matcher.matches()) {
			String killer = matcher.group(2);
			killer = AliasManager.getInstance().resolveUserName(killer);
			String killed = matcher.group(3);
			killed = AliasManager.getInstance().resolveUserName(killed);
			String weapon = matcher.group(4);
			if (!MOD_CHANGE_TEAM.equals(weapon)) {
				if (killer.equals(killed)) {
					stats.updateSuicide(killer);
				} else if (Context.getInstance().areUsersSameTeam(killer,
						killed)) {
					stats.updateTeamKiller(killer);
					stats.updateTeamKilled(killed);
				} else {
					stats.updateWhoKilledWhoWithWhat(killer, killed, weapon);

					stats.updateWhoKilledWho(killer, killed);
					stats.updateWhoKilled(killer);
					stats.updateWhoIsKilled(killed);
					stats.updateWeaponPerKiller(killer, weapon);
				}
				if (ENVIRONMENT.equals(killer)) {
					stats.updateEnvironmentKill(killed);
				}
			}
		}
	}

}
