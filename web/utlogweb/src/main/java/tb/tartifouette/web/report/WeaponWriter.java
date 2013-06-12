package tb.tartifouette.web.report;

import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.keys.WeaponPerKiller;

public class WeaponWriter extends AbstractFileEntryWriter {

	public WeaponWriter(Stats stats) {
		super(stats);
	}

	@Override
	public String getFileName() {
		return "weapons.csv";
	}

	@Override
	public String getContent() {
		StringBuilder writer = new StringBuilder();
		writer.append("User;Arme;nb kills").append(EOL);
		Map<WeaponPerKiller, Integer> weapons = MapUtils.sortByValue(stats
				.getStatsWeapons());
		for (Entry<WeaponPerKiller, Integer> entry : weapons.entrySet()) {
			WeaponPerKiller user = entry.getKey();
			writer.append(user.getKiller()).append(SEMI_COLUMN)
					.append(user.getWeapon()).append(SEMI_COLUMN)
					.append(entry.getValue()).append(EOL);
		}
		return writer.toString();
	}

}
