package tb.tartifouette.web.report;

import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.values.MapResult;

public class MapWriter extends AbstractFileEntryWriter {

	public MapWriter(Stats stats) {
		super(stats);
	}

	@Override
	public String getFileName() {
		return "maps.csv";
	}

	@Override
	public String getContent() {
		StringBuilder writer = new StringBuilder();
		writer.append("Map;drapeaux bleus;drapeaux rouges;%victoires bleues")
				.append(EOL);
		Map<String, MapResult> maps = MapUtils.sortByValue(stats
				.getStatsTeamFlag());
		for (Entry<String, MapResult> map : maps.entrySet()) {
			MapResult result = map.getValue();
			String ratio = "N/A";
			int totalPlays = result.getFlagBlue() + result.getFlagRed();
			if (totalPlays > 0) {
				ratio = String.valueOf(100 * result.computeRatio());
			}
			writer.append(map.getKey()).append(SEMI_COLUMN)
					.append(result.getFlagBlue()).append(SEMI_COLUMN)
					.append(result.getFlagRed()).append(SEMI_COLUMN)
					.append(ratio).append(EOL);
		}
		return writer.toString();
	}
}
