package tb.tartifouette.utlog.report;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.keys.WhoKilledWho;
import tb.tartifouette.utlog.values.UserStats;

public class UsersWriter extends AbstractFileEntryWriter {

	public UsersWriter(Stats stats, Locale locale) {
		super(stats, locale);
	}

	@Override
	public String getFileName() {
		return "users.csv";
	}

	@Override
	public String getContent() {
		StringBuilder writer = new StringBuilder();
		Map<WhoKilledWho, UserStats> users = MapUtils.sortByValue(stats
				.getStatsKills2());
		for (Entry<WhoKilledWho, UserStats> entry : users.entrySet()) {
			WhoKilledWho user = entry.getKey();
			UserStats value = entry.getValue();
			writer.append(user.getKiller()).append(SEMI_COLUMN)
					.append(user.getKilled()).append(SEMI_COLUMN)
					.append(value.getNbFrags()).append(SEMI_COLUMN)
					.append(value.getNbKilled() + EOL);
		}
		return writer.toString();
	}

	@Override
	public String getBaseBundleName() {
		return "user.stats.title";
	}

	@Override
	public int getNbTitles() {
		return 4;
	}
}
