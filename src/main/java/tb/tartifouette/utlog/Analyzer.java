package tb.tartifouette.utlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.keys.UserMap;
import tb.tartifouette.utlog.keys.WeaponPerKiller;
import tb.tartifouette.utlog.keys.WhoKilledWho;
import tb.tartifouette.utlog.keys.WhoKilledWhoWithWhat;
import tb.tartifouette.utlog.values.MapResult;
import tb.tartifouette.utlog.values.UserScore;
import tb.tartifouette.utlog.values.UserStats;

public class Analyzer {

	private static final String EOL = "\n";
	private static final String ENVIRONMENT = "<non-client>";
	private static final Log log = LogFactory.getLog(Analyzer.class);;

	private String destDirectory;
	private String fileName;
	private String dirName;

	public Analyzer(String destDirectory) {

		this.destDirectory = destDirectory;
	}

	public void analyze() throws Exception {
		List<File> filesToAnalyze = getFilesToAnaylyze();
		for (File file : filesToAnalyze) {
			if (file.isFile() && file.canRead()) {
				analyzeFile(file);
			}
		}
		generateReport();
	}

	private List<File> getFilesToAnaylyze() {
		List<File> files = new ArrayList<File>();
		if (fileName != null) {
			files.add(new File(fileName));
		}
		if (dirName != null) {
			File directory = new File(dirName);
			files.addAll(Arrays.asList(directory.listFiles()));
		}

		return files;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;

	}

	public void setFileName(String fileName) {
		this.fileName = fileName;

	}

	protected void analyzeFile(File fileToAnalyze)
			throws FileNotFoundException, IOException {
		BufferedReader reader = null;
		Reader isReader = null;
		FileInputStream fileInputStream = null;
		GZIPInputStream gzis = null;
		try {
			log.info("Reading file " + fileToAnalyze);
			fileInputStream = new FileInputStream(fileToAnalyze);
			if (fileToAnalyze.getName().endsWith(".gz")) {
				gzis = new GZIPInputStream(fileInputStream);
				isReader = new InputStreamReader(gzis);
				reader = new BufferedReader(isReader);
			} else {
				isReader = new InputStreamReader(fileInputStream);
				reader = new BufferedReader(isReader);
			}
			String line = reader.readLine();
			for (; line != null; line = reader.readLine()) {
				parseLine(line);
			}

		} finally {
			if (reader != null) {
				reader.close();
			}
			if (isReader != null) {
				isReader.close();
			}
			if (gzis != null) {
				gzis.close();
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

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

	private void parseLine(String line) {
		parseKill(line);
		parseFlag(line);
		parseStats(line);
		detectAlias(line);
		detectUserId(line);
		detectMap(line);
	}

	private void detectMap(String line) {
		Matcher matcher = MAP_NAME.matcher(line);
		if (matcher.matches()) {
			Context.getInstance().setCurrentMap(matcher.group(1));
		}

	}

	private void detectUserId(String line) {
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

	private void detectAlias(String line) {
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
			Stats.updateUserScore(user, score);
		}
	}

	private void parseFlag(String line) {
		Matcher matcher = FLAG_LINE.matcher(line);
		if (matcher.matches()) {
			String userId = matcher.group(1);
			String resolvedAlias = Context.getInstance().getUsername(userId);
			String team = matcher.group(2);
			Stats.updateTeamFlag(team);
			Stats.updateUserFlag(resolvedAlias);
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
					Stats.updateSuicide(killer);
				} else if (Context.getInstance().areUsersSameTeam(killer,
						killed)) {
					Stats.updateTeamKiller(killer);
					Stats.updateTeamKilled(killed);
				} else {
					Stats.updateWhoKilledWhoWithWhat(killer, killed, weapon);

					Stats.updateWhoKilledWho(killer, killed);
					Stats.updateWhoKilled(killer);
					Stats.updateWhoIsKilled(killed);
					Stats.updateWheaponPerKiller(killer, weapon);
				}
				if (ENVIRONMENT.equals(killer)) {
					Stats.updateEnvironmentKill(killed);
				}
			}
		}
	}

	public void generateReport() throws IOException {
		log.info("Possible aliases : "
				+ AliasManager.getInstance().getPossibleAliases());
		File f = new File(destDirectory);
		f.mkdirs();
		generateStats();
	}

	private void generateStats() throws IOException {
		generateUserStats();
		generateScoreStats();
		generateScorePerMapStats();
		generateUserDetailedStats();
		generateMapStats();
		generateWeaponStats();
	}

	private void generateWeaponStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/weapons.csv");
			writer = new FileWriter(destDirectory + "/weapons.csv");
			writer.write("User;Arme;nb kills" + EOL);
			Map<WeaponPerKiller, Integer> weapons = MapUtils
					.sortByValue(Stats.statsWeapons);
			for (Entry<WeaponPerKiller, Integer> entry : weapons.entrySet()) {
				WeaponPerKiller user = entry.getKey();
				writer.write(user.getKiller() + ";" + user.getWeapon() + ";"
						+ entry.getValue() + EOL);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void generateUserDetailedStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory
					+ "/users-details.csv");
			writer = new FileWriter(destDirectory + "/users-details.csv");
			writer.write("User;Ennemi;Arme;nb kills" + EOL);
			Map<WhoKilledWhoWithWhat, Integer> users = MapUtils
					.sortByValue(Stats.statsKills1);
			for (Entry<WhoKilledWhoWithWhat, Integer> entry : users.entrySet()) {
				WhoKilledWhoWithWhat user = entry.getKey();
				writer.write(user.getKiller() + ";" + user.getKilled() + ";"
						+ user.getWeapon() + ";" + entry.getValue() + EOL);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void generateUserStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/users.csv");
			writer = new FileWriter(destDirectory + "/users.csv");
			writer.write("User 1 ;User 2;user 1 kill user 2;user 2 kill user 1"
					+ EOL);
			Map<WhoKilledWho, UserStats> users = MapUtils
					.sortByValue(Stats.statsKills2);
			for (Entry<WhoKilledWho, UserStats> entry : users.entrySet()) {
				WhoKilledWho user = entry.getKey();
				UserStats value = entry.getValue();
				writer.write(user.getKiller() + ";" + user.getKilled() + ";"
						+ value.getNbFrags() + ";" + value.getNbKilled() + EOL);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void generateScorePerMapStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/scores-map.csv");
			writer = new FileWriter(destDirectory + "/scores-map.csv");
			writer.write("User;Map;score cumule;parties jouees;nb frags;nb morts;nb suicides;"
					+ "tue par le decor;drapeaux ramenes;ingrat(tue ses coequipiers);mal aime(tue par ses coequipiers);"
					+ "score/partie;frags/partie;drapeaux/partie;frag/mort"
					+ EOL);
			Map<UserMap, UserScore> users = MapUtils
					.sortByValue(Stats.statsUserMapScore);
			for (Entry<UserMap, UserScore> user : users.entrySet()) {
				UserScore value = user.getValue();
				writer.write(user.getKey().getUser() + ";"
						+ user.getKey().getMap() + ";" + value.getTotalScore()
						+ ";" + value.getNbPlays() + ";"
						+ value.getTotalFrags() + ";" + value.getTotalDeaths()
						+ ";" + value.getTotalSuicides() + ";"
						+ value.getTotalEnvironment() + ";" + value.getFlags()
						+ ";" + value.getTeamKiller() + ";"
						+ value.getTeamKilled() + ";"
						+ value.computeScorePerPlay() + ";"
						+ value.computeFragPerPlay() + ";"
						+ value.computeFlagPerPlay() + ";"
						+ value.computeFragPerDeathRatio() + EOL);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void generateScoreStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/scores.csv");
			writer = new FileWriter(destDirectory + "/scores.csv");
			writer.write("User;score cumule;parties jouees;nb frags;nb morts;nb suicides;tue par le decor;"
					+ "drapeaux ramenes;ingrat(tue ses coequipiers);mal aime(tue par ses coequipiers);score/partie;"
					+ "frags/partie;drapeaux/partie;frag/mort" + EOL);
			Map<String, UserScore> users = MapUtils
					.sortByValue(Stats.statsUserScore);
			for (Entry<String, UserScore> user : users.entrySet()) {
				UserScore value = user.getValue();
				writer.write(user.getKey() + ";" + value.getTotalScore() + ";"
						+ value.getNbPlays() + ";" + value.getTotalFrags()
						+ ";" + value.getTotalDeaths() + ";"
						+ value.getTotalSuicides() + ";"
						+ value.getTotalEnvironment() + ";" + value.getFlags()
						+ ";" + value.getTeamKiller() + ";"
						+ value.getTeamKilled() + ";"
						+ value.computeScorePerPlay() + ";"
						+ value.computeFragPerPlay() + ";"
						+ value.computeFlagPerPlay() + ";"
						+ value.computeFragPerDeathRatio() + EOL);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void generateMapStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/maps.csv");
			writer = new FileWriter(destDirectory + "/maps.csv");
			writer.write("Map;drapeaux bleus;drapeaux rouges;%victoires bleues"
					+ EOL);
			Map<String, MapResult> maps = MapUtils
					.sortByValue(Stats.statsTeamFlag);
			for (Entry<String, MapResult> map : maps.entrySet()) {
				MapResult result = map.getValue();
				String ratio = "N/A";
				int totalPlays = result.flagBlue + result.getFlagRed();
				if (totalPlays > 0) {
					ratio = String.valueOf(100 * result.computeRatio());
				}
				writer.write(map.getKey() + ";" + result.flagBlue + ";"
						+ result.getFlagRed() + ";" + ratio + EOL);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}

}
