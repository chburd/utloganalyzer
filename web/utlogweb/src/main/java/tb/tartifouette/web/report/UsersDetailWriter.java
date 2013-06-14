package tb.tartifouette.web.report;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.keys.WhoKilledWhoWithWhat;

public class UsersDetailWriter extends AbstractFileEntryWriter {

	public UsersDetailWriter(Stats stats, Locale locale) {
		super(stats, locale);
	}

	@Override
	public String getFileName() {
		return "users-details.csv";
	}

	@Override
	public String getContent() {
		StringBuilder writer = new StringBuilder();
		Map<WhoKilledWhoWithWhat, Integer> users = MapUtils.sortByValue(stats
				.getStatsKills1());
		for (Entry<WhoKilledWhoWithWhat, Integer> entry : users.entrySet()) {
			WhoKilledWhoWithWhat user = entry.getKey();
			writer.append(user.getKiller()).append(SEMI_COLUMN)
					.append(user.getKilled()).append(SEMI_COLUMN)
					.append(user.getWeapon()).append(SEMI_COLUMN)
					.append(entry.getValue()).append(EOL);
		}
		return writer.toString();
	}

	@Override
	public String getBaseBundleName() {
		return "users.detail.stats.title";
	}

	@Override
	public int getNbTitles() {
		return 4;
	}

}
