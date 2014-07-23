package tb.tartifouette.utlog.report;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.MapUtils;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.keys.HitsKey;
import tb.tartifouette.utlog.values.Hits;

public class HitsWriter extends AbstractFileEntryWriter {

	public HitsWriter(Stats stats, Locale locale) {
		super(stats, locale);
	}

	@Override
	public String getFileName() {
		return "hits.csv";
	}

	@Override
	public String getContent() {
		StringBuilder writer = new StringBuilder();
		Map<HitsKey, Hits> hits = MapUtils.sortByValue(stats.getHits());
		for (Entry<HitsKey, Hits> entry : hits.entrySet()) {
			HitsKey key = entry.getKey();
			Hits value = entry.getValue();
			writer.append(key.getShouter()).append(SEMI_COLUMN)
					.append(key.getShouted()).append(SEMI_COLUMN)
					.append(key.getWeapon()).append(SEMI_COLUMN)
					.append(key.getRegion()).append(SEMI_COLUMN)
					.append(value.getCount()).append(SEMI_COLUMN)
					.append(value.getHp()).append(EOL);
		}
		return writer.toString();
	}

	@Override
	public String getBaseBundleName() {
		return "hits.stats.title";
	}

	@Override
	public int getNbTitles() {
		return 6;
	}

}
