package tb.tartifouette.utlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	private static final Logger log = LogManager.getLogger(ReportGenerator.class);

	private static final String SEMI_COLUMN = ";";

	private final File destDirectory;

	private final Stats stats;

	public ReportGenerator(Stats stats, String destDirectory, String dateGuessedFromInput) {
		this.destDirectory = getDestDirectory(destDirectory, dateGuessedFromInput);

		this.stats = stats;
	}

	private File getDestDirectory(String destDirectory, String dateGuessedFromInput) {
		String origDate = getDate(dateGuessedFromInput);
		File dest = new File(destDirectory, origDate);
		int i = 1;
		while (dest.exists()) {
			String date = origDate + "-" + i;
			dest = new File(destDirectory, date);
			i++;
		}
		return dest;
	}

	public void generateReport(AliasManager aliasManager) throws IOException {
		log.info("Possible aliases : " + aliasManager.getPossibleAliases());
		destDirectory.mkdirs();
		generateStats();
	}

	private String getDate(String dateGuessedFromInput) {
		if (dateGuessedFromInput != null) {
			return dateGuessedFromInput;
		}
		return LocalDate.now().toString();
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
			File destFile = new File(destDirectory, "hits.csv");
			log.info("Writing report file " + destFile.getAbsolutePath());
			writer = new FileWriter(destFile);
			writer.write("Shouter;Shouted;Weapon;Body Region;Hit count;Damage" + EOL);
			Map<HitsKey, Hits> hits = MapUtils.sortByValue(stats.getHits());
			for (Entry<HitsKey, Hits> entry : hits.entrySet()) {
				HitsKey key = entry.getKey();
				Hits value = entry.getValue();
				writer.write(key.getShouter() + ";" + key.getShouted() + ";" + key.getWeapon() + ";" + key.getRegion()
						+ ";" + value.getCount() + ";" + value.getHp() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateWeaponStats() throws IOException {
		Writer writer = null;
		try {
			File destFile = new File(destDirectory, "weapons.csv");
			log.info("Writing report file " + destFile.getAbsolutePath());
			writer = new FileWriter(destFile);
			writer.write("User;Weapon;# frags" + EOL);
			Map<WeaponPerKiller, Integer> weapons = MapUtils.sortByValue(stats.getStatsWeapons());
			for (Entry<WeaponPerKiller, Integer> entry : weapons.entrySet()) {
				WeaponPerKiller user = entry.getKey();
				writer.write(user.getKiller() + ";" + user.getWeapon() + ";" + entry.getValue() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateUserDetailedStats() throws IOException {
		Writer writer = null;
		try {
			File destFile = new File(destDirectory, "users-details.csv");
			log.info("Writing report file " + destFile.getAbsolutePath());
			writer = new FileWriter(destFile);
			writer.write("User;Opponent;Weapon;# frags" + EOL);
			Map<WhoKilledWhoWithWhat, Integer> users = MapUtils.sortByValue(stats.getStatsKills1());
			for (Entry<WhoKilledWhoWithWhat, Integer> entry : users.entrySet()) {
				WhoKilledWhoWithWhat user = entry.getKey();
				writer.write(user.getKiller() + ";" + user.getKilled() + ";" + user.getWeapon() + ";" + entry.getValue()
						+ EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateUserStats() throws IOException {
		Writer writer = null;
		try {
			File destFile = new File(destDirectory, "users.csv");
			log.info("Writing report file " + destFile.getAbsolutePath());
			writer = new FileWriter(destFile);
			writer.write("User 1 ;User 2;User 1 kill User 2;User 2 kill User 1" + EOL);
			Map<WhoKilledWho, UserStats> users = MapUtils.sortByValue(stats.getStatsKills2());
			for (Entry<WhoKilledWho, UserStats> entry : users.entrySet()) {
				WhoKilledWho user = entry.getKey();
				UserStats value = entry.getValue();
				writer.write(user.getKiller() + ";" + user.getKilled() + ";" + value.getNbFrags() + ";"
						+ value.getNbKilled() + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateScorePerMapStats() throws IOException {
		Writer writer = null;
		try {
			File destFile = new File(destDirectory, "scores-map.csv");
			log.info("Writing report file " + destFile.getAbsolutePath());
			writer = new FileWriter(destFile);
			writer.write("User;Map;Total score;# plays;# frags;# deaths;# suicides;"
					+ "# environment kill;# flags captured;# flags returned;# teammate kill;# killed by his teammates;"
					+ "score / play;frags / play;retrieved flags / play;frag / death ratio;best frag serie;worst kill serie;"
					+ "hits given;hits received;damage given;damage received" + EOL);
			Map<UserMap, UserScore> users = MapUtils.sortByValue(stats.getStatsUserMapScore());
			for (Entry<UserMap, UserScore> user : users.entrySet()) {

				UserScore value = user.getValue();
				StringBuilder sb = new StringBuilder();
				sb.append(user.getKey().getUser()).append(SEMI_COLUMN).append(user.getKey().getMap())
						.append(SEMI_COLUMN).append(value.getTotalScore()).append(SEMI_COLUMN)
						.append(value.getNbPlays()).append(SEMI_COLUMN).append(value.getTotalFrags())
						.append(SEMI_COLUMN).append(value.getTotalDeaths()).append(SEMI_COLUMN)
						.append(value.getTotalSuicides()).append(SEMI_COLUMN).append(value.getTotalEnvironment())
						.append(SEMI_COLUMN).append(value.getFlagsCaptured()).append(SEMI_COLUMN)
						.append(value.getFlagsReturned()).append(SEMI_COLUMN).append(value.getTeamKiller())
						.append(SEMI_COLUMN).append(value.getTeamKilled()).append(SEMI_COLUMN)
						.append(value.computeScorePerPlay()).append(SEMI_COLUMN).append(value.computeFragPerPlay())
						.append(SEMI_COLUMN).append(value.computeFlagPerPlay()).append(SEMI_COLUMN)
						.append(value.computeFragPerDeathRatio()).append(SEMI_COLUMN).append(value.getBestFragSerie())
						.append(SEMI_COLUMN).append((-value.getWorseKillSerie())).append(SEMI_COLUMN)
						.append((value.getHitsGiven())).append(SEMI_COLUMN).append((value.getHitsReceived()))
						.append(SEMI_COLUMN).append((value.getDamageGiven())).append(SEMI_COLUMN)
						.append((value.getDamageReceived())).append(SEMI_COLUMN).append(EOL);
				writer.append(sb);

			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateScoreStats() throws IOException {
		Writer writer = null;
		try {
			File destFile = new File(destDirectory, "scores.csv");
			log.info("Writing report file " + destFile.getAbsolutePath());
			writer = new FileWriter(destFile);

			writer.write("User;Total score;# plays;# frags;# deaths;# suicides;# environment kill;# flags captured;"
					+ "# flags returned;# teammate kill;# killed by his teammates;score / play;frags / play;"
					+ "retrieved flags / play;frag / death ratio;best frags serie;worst kill serie;hits given;"
					+ "hits received;damage given;damage received" + EOL);
			Map<String, UserScore> users = MapUtils.sortByValue(stats.getStatsUserScore());
			for (Entry<String, UserScore> user : users.entrySet()) {
				UserScore value = user.getValue();
				StringBuilder sb = new StringBuilder();
				sb.append(user.getKey()).append(SEMI_COLUMN).append(value.getTotalScore()).append(SEMI_COLUMN)
						.append(value.getNbPlays()).append(SEMI_COLUMN).append(value.getTotalFrags())
						.append(SEMI_COLUMN).append(value.getTotalDeaths()).append(SEMI_COLUMN)
						.append(value.getTotalSuicides()).append(SEMI_COLUMN).append(value.getTotalEnvironment())
						.append(SEMI_COLUMN).append(value.getFlagsCaptured()).append(SEMI_COLUMN)
						.append(value.getFlagsReturned()).append(SEMI_COLUMN).append(value.getTeamKiller())
						.append(SEMI_COLUMN).append(value.getTeamKilled()).append(SEMI_COLUMN)
						.append(value.computeScorePerPlay()).append(SEMI_COLUMN).append(value.computeFragPerPlay())
						.append(SEMI_COLUMN).append(value.computeFlagPerPlay()).append(SEMI_COLUMN)
						.append(value.computeFragPerDeathRatio()).append(SEMI_COLUMN).append(value.getBestFragSerie())
						.append(SEMI_COLUMN).append((-value.getWorseKillSerie())).append(SEMI_COLUMN)
						.append((value.getHitsGiven())).append(SEMI_COLUMN).append((value.getHitsReceived()))
						.append(SEMI_COLUMN).append((value.getDamageGiven())).append(SEMI_COLUMN)
						.append((value.getDamageReceived())).append(EOL);
				writer.append(sb);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private void generateMapStats() throws IOException {
		Writer writer = null;
		try {
			File destFile = new File(destDirectory, "maps.csv");
			log.info("Writing report file " + destFile.getAbsolutePath());
			writer = new FileWriter(destFile);

			writer.write("Map;Blue flags;Red flags;% blue flags" + EOL);
			Map<String, MapResult> maps = MapUtils.sortByValue(stats.getStatsTeamFlag());
			for (Entry<String, MapResult> map : maps.entrySet()) {
				MapResult result = map.getValue();
				String ratio = "N/A";
				int totalPlays = result.getFlagBlue() + result.getFlagRed();
				if (totalPlays > 0) {
					ratio = String.valueOf(100 * result.computeRatio());
				}
				writer.write(map.getKey() + ";" + result.getFlagBlue() + ";" + result.getFlagRed() + ";" + ratio + EOL);
			}
		} finally {
			IOUtils.closeQuietly(writer);
		}

	}

}
