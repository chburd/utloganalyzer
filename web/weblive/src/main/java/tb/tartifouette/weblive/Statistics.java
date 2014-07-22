package tb.tartifouette.weblive;

import java.util.Map;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.values.UserScore;

public class Statistics {

	private static final Stats stats = new Stats();

	public static Stats getGlobalStats() {
		return stats;
	}

	public static Map<String, UserScore> getStats() {
		return MapUtils.sortByValue(stats.getStatsUserScore());
	}

}
