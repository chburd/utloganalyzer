package tb.tartifouette.web.report;

import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.values.UserScore;

public class ScoreWriter extends AbstractFileEntryWriter {

	public ScoreWriter(Stats stats) {
		super(stats);
	}

	@Override
	public String getFileName() {
		return "scores.csv";
	}

	@Override
	public String getContent() {
		StringBuilder writer = new StringBuilder();
		writer.append(
				"User;score cumule;parties jouees;nb frags;nb morts;nb suicides;tue par le decor;")
				.append("drapeaux ramenes;ingrat(tue ses coequipiers);mal aime(tue par ses coequipiers);score/partie;")
				.append("frags/partie;drapeaux/partie;frag/mort;best frag serie;")
				.append("meilleure duree invaincu(s);worst kill serie;pire durée sans marquer(s)")
				.append(EOL);
		Map<String, UserScore> users = MapUtils.sortByValue(stats
				.getStatsUserScore());
		for (Entry<String, UserScore> user : users.entrySet()) {
			UserScore value = user.getValue();
			writer.append(user.getKey()).append(SEMI_COLUMN)
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
