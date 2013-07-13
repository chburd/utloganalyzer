package tb.tartifouette.utlog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

	private static final String ENVIRONMENT = "<non-client>";

	private static final Pattern KILL_LINE = Pattern
			.compile("\\s*(\\d+:\\d+) Kill:\\s\\d+\\s\\d+\\s\\d+:\\s+(\\S+)\\skilled (\\S+) by (.*)");

	private static final Pattern FLAG_LINE = Pattern
			.compile("\\s*\\d+:\\d+ Flag:\\s(\\d+)\\s(\\d+):\\s(.*)");

	private static final Pattern STATS_LINE = Pattern
			.compile("\\s*(\\d+:\\d+) score:\\s(\\d+)\\s+ping:\\s+\\d+\\s+client:\\s+\\d+\\s+(.*)");

	private static final Pattern CONNECT_LINE = Pattern
			.compile("\\s+\\d+:\\d+\\s+ClientUserinfo:\\s+(\\d+)\\s+\\\\ip\\\\(\\d+\\.\\d+\\.\\d+\\.\\d+):\\d+\\\\name\\\\(.*)\\\\racered.*");

	private static final Pattern CLIENT_USER_INFO_CHANGED = Pattern
			.compile("\\s+(\\d+:\\d+)\\s+ClientUserinfoChanged:\\s+(\\d+)\\s+n\\\\(.*)\\\\t\\\\(\\d+)\\\\r.*");

	private static final Pattern MAP_NAME = Pattern
			.compile("\\s+\\d+:\\d+\\s+InitGame:\\s+.*\\\\mapname\\\\([^\\\\]*)\\\\.*");

	private static final DateFormat HOUR_PARSER = new SimpleDateFormat("mm:ss");

	private static final String MOD_CHANGE_TEAM = "MOD_CHANGE_TEAM";

	private final Stats stats;

	private final AliasManager aliasManager;

	public LineParser(Stats stats, AliasManager aliasManager) {
		this.stats = stats;
		this.aliasManager = aliasManager;
	}

	public void parseLine(String line) throws ParseException {
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
			Context.getInstance().clear();
			Context.getInstance().setCurrentMap(matcher.group(1));
			stats.resetSeriesStats();
		}

	}

	private void parseUserId(String line) throws ParseException {
		Matcher matcher = CLIENT_USER_INFO_CHANGED.matcher(line);
		if (matcher.matches()) {
			String times = matcher.group(1);
			int time = getSecondCount(times);
			String clientId = matcher.group(2);
			String user = matcher.group(3);
			String team = matcher.group(4);
			Context.getInstance().putClientId(clientId,
					aliasManager.resolveUserName(user));
			Context.getInstance().setTeamComposition(team, user);
			stats.setUserStartDate(user, time);
		}
	}

	private void parseAlias(String line) {
		Matcher matcher = CONNECT_LINE.matcher(line);
		if (matcher.matches()) {
			String clientId = matcher.group(1);
			String ip = matcher.group(2);
			String user = matcher.group(3);
			aliasManager.addPossibleAlias(ip, user);
			Context.getInstance().putClientId(clientId,
					aliasManager.resolveUserName(user));
		}
	}

	private void parseStats(String line) throws ParseException {
		Matcher matcher = STATS_LINE.matcher(line);
		if (matcher.matches()) {
			String timeS = matcher.group(1);
			int time = getSecondCount(timeS);
			int score = Integer.parseInt(matcher.group(2));
			String user = matcher.group(3);
			user = aliasManager.resolveUserName(user);
			stats.updateUserScore(user, score, time);
		}
	}

	private void parseFlag(String line) {
		Matcher matcher = FLAG_LINE.matcher(line);
		if (matcher.matches()) {
			String userId = matcher.group(1);
			String resolvedAlias = Context.getInstance().getUsername(userId);
			String team = matcher.group(3);
			String action = matcher.group(2);
			FlagAction flagAction = FlagAction.fromValue(action);
			if (FlagAction.FLAG_CAPTURED.equals(flagAction)) {
				stats.updateTeamFlag(team);
			}
			stats.updateUserFlag(resolvedAlias, flagAction);
		}
	}

	private void parseKill(String line) throws ParseException {
		Matcher matcher = KILL_LINE.matcher(line);
		if (matcher.matches()) {
			String times = matcher.group(1);
			int time = getSecondCount(times);
			String killer = matcher.group(2);
			killer = aliasManager.resolveUserName(killer);
			String killed = matcher.group(3);
			killed = aliasManager.resolveUserName(killed);
			String weapon = matcher.group(4);
			if (!MOD_CHANGE_TEAM.equals(weapon)) {
				if (killer.equals(killed)) {
					stats.updateSuicide(killer);
					stats.updateKillSerie(killer, time);
				} else if (Context.getInstance().areUsersSameTeam(killer,
						killed)) {
					stats.updateTeamKiller(killer);
					stats.updateTeamKilled(killed);
					stats.updateKillSerie(killed, time);
				} else {
					stats.updateWhoKilledWhoWithWhat(killer, killed, weapon);

					stats.updateWhoKilledWho(killer, killed);
					stats.updateWhoKilled(killer);
					stats.updateWhoIsKilled(killed);
					stats.updateWeaponPerKiller(killer, weapon);
					stats.updateFragSerie(killer, time);
					stats.updateKillSerie(killed, time);
				}
				if (ENVIRONMENT.equals(killer)) {
					stats.updateEnvironmentKill(killed);
					stats.updateKillSerie(killed, time);
				}
			}
		}
	}

	private int getSecondCount(String times) throws ParseException {
		HOUR_PARSER.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date d = HOUR_PARSER.parse(times);
		Date ref = new Date(0);
		return (int) ((d.getTime() - ref.getTime()) / 1000);
	}
}
