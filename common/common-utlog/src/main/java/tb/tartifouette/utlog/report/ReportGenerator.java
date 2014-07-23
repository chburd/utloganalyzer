package tb.tartifouette.utlog.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import tb.tartifouette.utlog.AliasManager;
import tb.tartifouette.utlog.Stats;

public class ReportGenerator {

	private static final Logger log = Logger.getLogger(ReportGenerator.class);

	private final Stats stats;
	private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

	private final Locale locale;

	public ByteArrayOutputStream getBaos() {
		return baos;
	}

	public ReportGenerator(Stats stats, Locale locale) {
		this.stats = stats;
		this.locale = locale;
	}

	public void generateReport(AliasManager mgr) throws IOException {
		log.info("Possible aliases : " + mgr.getPossibleAliases());
		generateStats();
	}

	private void generateStats() throws IOException {

		ZipOutputStream zos = new ZipOutputStream(baos);

		List<AbstractFileEntryWriter> writers = getWriters();
		for (AbstractFileEntryWriter writer : writers) {
			writer.writeFileEntry(zos);
		}
		zos.close();
	}

	private List<AbstractFileEntryWriter> getWriters() {
		List<AbstractFileEntryWriter> writers = new ArrayList<AbstractFileEntryWriter>();
		writers.add(new MapWriter(stats, locale));
		writers.add(new ScoresMapWriter(stats, locale));
		writers.add(new ScoreWriter(stats, locale));
		writers.add(new UsersDetailWriter(stats, locale));
		writers.add(new UsersWriter(stats, locale));
		writers.add(new WeaponWriter(stats, locale));
		writers.add(new HitsWriter(stats, locale));
		return writers;
	}

}
