package tb.tartifouette.utlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.keys.HitsKey;
import tb.tartifouette.utlog.keys.UserMap;
import tb.tartifouette.utlog.keys.WeaponPerKiller;
import tb.tartifouette.utlog.keys.WhoKilledWho;
import tb.tartifouette.utlog.keys.WhoKilledWhoWithWhat;
import tb.tartifouette.utlog.values.Hits;
import tb.tartifouette.utlog.values.MapResult;
import tb.tartifouette.utlog.values.UserScore;
import tb.tartifouette.utlog.values.UserStats;

public class ReportGenerator {

	private static final String EOL = "\n";

	private static final Logger log = Logger.getLogger(ReportGenerator.class);

	private final String destDirectory;

	private final Stats stats;

	public ReportGenerator(Stats stats, String destDirectory) {
		this.destDirectory = destDirectory;
		this.stats = stats;
	}

	public void generateReport(AliasManager aliasManager) throws IOException {
		log.info("Possible aliases : " + aliasManager.getPossibleAliases());
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
		generateHitsStats();
	}

	private void generateHitsStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/hits.csv");
			writer = new FileWriter(destDirectory + "/hits.csv");
			writer.write("Shouter;Shouted;Weapon;BodyPart;Nb Hits;Damage" + EOL);
			Map<HitsKey, Hits> hits = MapUtils.sortByValue(stats.getHits());
			for (Entry<HitsKey, Hits> entry : hits.entrySet()) {
				HitsKey key = entry.getKey();
				Hits value = entry.getValue();
				writer.write(key.getShouter() + ";" + key.getShouted() + ";"
						+ key.getWeapon() + ";" + key.getRegion() + ";"
						+ value.getCount() + ";" + value.getHp() + ";" + value
						+ EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateWeaponStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/weapons.csv");
			writer = new FileWriter(destDirectory + "/weapons.csv");
			writer.write("User;Arme;nb kills" + EOL);
			Map<WeaponPerKiller, Integer> weapons = MapUtils.sortByValue(stats
					.getStatsWeapons());
			for (Entry<WeaponPerKiller, Integer> entry : weapons.entrySet()) {
				WeaponPerKiller user = entry.getKey();
				writer.write(user.getKiller() + ";" + user.getWeapon() + ";"
						+ entry.getValue() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
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
					.sortByValue(stats.getStatsKills1());
			for (Entry<WhoKilledWhoWithWhat, Integer> entry : users.entrySet()) {
				WhoKilledWhoWithWhat user = entry.getKey();
				writer.write(user.getKiller() + ";" + user.getKilled() + ";"
						+ user.getWeapon() + ";" + entry.getValue() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateUserStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/users.csv");
			writer = new FileWriter(destDirectory + "/users.csv");
			writer.write("User 1 ;User 2;user 1 kill user 2;user 2 kill user 1"
					+ EOL);
			Map<WhoKilledWho, UserStats> users = MapUtils.sortByValue(stats
					.getStatsKills2());
			for (Entry<WhoKilledWho, UserStats> entry : users.entrySet()) {
				WhoKilledWho user = entry.getKey();
				UserStats value = entry.getValue();
				writer.write(user.getKiller() + ";" + user.getKilled() + ";"
						+ value.getNbFrags() + ";" + value.getNbKilled() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateScorePerMapStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/scores-map.csv");
			writer = new FileWriter(destDirectory + "/scores-map.csv");
			writer.write("User;Map;score cumule;parties jouees;nb frags;nb morts;nb suicides;"
					+ "tue par le decor;drapeaux ramenes;ingrat(tue ses coequipiers);mal aime(tue par ses coequipiers);"
					+ "score/partie;frags/partie;drapeaux/partie;frag/mort;best frag serie;"
					+ "meilleure duree invaincu(s);worst kill serie;pire durée sans marquer(s)"
					+ EOL);
			Map<UserMap, UserScore> users = MapUtils.sortByValue(stats
					.getStatsUserMapScore());
			for (Entry<UserMap, UserScore> user : users.entrySet()) {
				UserScore value = user.getValue();
				writer.write(user.getKey().getUser() + ";"
						+ user.getKey().getMap() + ";" + value.getTotalScore()
						+ ";" + value.getNbPlays() + ";"
						+ value.getTotalFrags() + ";" + value.getTotalDeaths()
						+ ";" + value.getTotalSuicides() + ";"
						+ value.getTotalEnvironment() + ";"
						+ value.getFlagsCaptured() + ";"
						+ value.getTeamKiller() + ";" + value.getTeamKilled()
						+ ";" + value.computeScorePerPlay() + ";"
						+ value.computeFragPerPlay() + ";"
						+ value.computeFlagPerPlay() + ";"
						+ value.computeFragPerDeathRatio() + ";"
						+ value.getBestFragSerie() + ";"
						+ value.getBestFragSerieDurationInS() + ";"
						+ (-value.getWorseKillSerie()) + ";"
						+ value.getWorstKillSerieDurationInS() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateScoreStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/scores.csv");
			writer = new FileWriter(destDirectory + "/scores.csv");
			writer.write("User;score cumule;parties jouees;nb frags;nb morts;nb suicides;tue par le decor;"
					+ "drapeaux ramenes;ingrat(tue ses coequipiers);mal aime(tue par ses coequipiers);score/partie;"
					+ "frags/partie;drapeaux/partie;frag/mort;best frag serie;"
					+ "meilleure duree invaincu(s);worst kill serie;pire durée sans marquer(s)"
					+ EOL);
			Map<String, UserScore> users = MapUtils.sortByValue(stats
					.getStatsUserScore());
			for (Entry<String, UserScore> user : users.entrySet()) {
				UserScore value = user.getValue();
				writer.write(user.getKey() + ";" + value.getTotalScore() + ";"
						+ value.getNbPlays() + ";" + value.getTotalFrags()
						+ ";" + value.getTotalDeaths() + ";"
						+ value.getTotalSuicides() + ";"
						+ value.getTotalEnvironment() + ";"
						+ value.getFlagsCaptured() + ";"
						+ value.getTeamKiller() + ";" + value.getTeamKilled()
						+ ";" + value.computeScorePerPlay() + ";"
						+ value.computeFragPerPlay() + ";"
						+ value.computeFlagPerPlay() + ";"
						+ value.computeFragPerDeathRatio() + ";"
						+ value.getBestFragSerie() + ";"
						+ value.getBestFragSerieDurationInS() + ";"
						+ (-value.getWorseKillSerie()) + ";"
						+ value.getWorstKillSerieDurationInS() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateMapStats() throws IOException {
		Writer writer = null;
		try {
			log.info("Writing report file " + destDirectory + "/maps.csv");
			writer = new FileWriter(destDirectory + "/maps.csv");
			writer.write("Map;drapeaux bleus;drapeaux rouges;%victoires bleues"
					+ EOL);
			Map<String, MapResult> maps = MapUtils.sortByValue(stats
					.getStatsTeamFlag());
			for (Entry<String, MapResult> map : maps.entrySet()) {
				MapResult result = map.getValue();
				String ratio = "N/A";
				int totalPlays = result.getFlagBlue() + result.getFlagRed();
				if (totalPlays > 0) {
					ratio = String.valueOf(100 * result.computeRatio());
				}
				writer.write(map.getKey() + ";" + result.getFlagBlue() + ";"
						+ result.getFlagRed() + ";" + ratio + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}

	}

}
