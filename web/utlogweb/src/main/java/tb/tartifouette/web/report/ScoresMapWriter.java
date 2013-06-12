package tb.tartifouette.web.report;

import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.keys.UserMap;
import tb.tartifouette.utlog.values.UserScore;

public class ScoresMapWriter extends AbstractFileEntryWriter {

	public ScoresMapWriter(Stats stats) {
		super(stats);
	}

	@Override
	public String getFileName() {
		return "scores-map.csv";
	}

	@Override
	public String getContent() {
		StringBuilder writer = new StringBuilder();
		writer.append(
				"User;Map;score cumule;parties jouees;nb frags;nb morts;nb suicides;")
				.append("tue par le decor;drapeaux ramenes;ingrat(tue ses coequipiers);mal aime(tue par ses coequipiers);")
				.append("score/partie;frags/partie;drapeaux/partie;frag/mort;best frag serie;")
				.append("meilleure duree invaincu(s);worst kill serie;pire duree sans marquer(s)")
				.append(EOL);
		Map<UserMap, UserScore> users = MapUtils.sortByValue(stats
				.getStatsUserMapScore());
		for (Entry<UserMap, UserScore> user : users.entrySet()) {
			UserScore value = user.getValue();
			writer.append(user.getKey().getUser()).append(SEMI_COLUMN)
					.append(user.getKey().getMap()).append(SEMI_COLUMN)
					.append(value.getTotalScore()).append(SEMI_COLUMN)
					.append(value.getNbPlays()).append(SEMI_COLUMN)
					.append(value.getTotalFrags()).append(SEMI_COLUMN)
					.append(value.getTotalDeaths()).append(SEMI_COLUMN)
					.append(value.getTotalSuicides()).append(SEMI_COLUMN)
					.append(value.getTotalEnvironment()).append(SEMI_COLUMN)
					.append(value.getFlags()).append(SEMI_COLUMN)
					.append(value.getTeamKiller()).append(SEMI_COLUMN)
					.append(value.getTeamKilled()).append(SEMI_COLUMN)
					.append(value.computeScorePerPlay()).append(SEMI_COLUMN)
					.append(value.computeFragPerPlay()).append(SEMI_COLUMN)
					.append(value.computeFlagPerPlay()).append(SEMI_COLUMN)
					.append(value.computeFragPerDeathRatio())
					.append(SEMI_COLUMN).append(value.getBestFragSerie())
					.append(SEMI_COLUMN)
					.append(value.getBestFragSerieDurationInS())
					.append(SEMI_COLUMN).append((-value.getWorseKillSerie()))
					.append(SEMI_COLUMN)
					.append(value.getWorstKillSerieDurationInS()).append(EOL);
		}
		return writer.toString();

	}
}
