package tb.tartifouette.web.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import tb.tartifouette.utlog.AliasManager;
import tb.tartifouette.utlog.Stats;

public class ReportGenerator {

	private static final Logger log = Logger.getLogger(ReportGenerator.class);

	private final Stats stats;
	private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

	public ByteArrayOutputStream getBaos() {
		return baos;
	}

	public ReportGenerator(Stats stats) {
		this.stats = stats;
	}

	public void generateReport() throws IOException {
		log.info("Possible aliases : "
				+ AliasManager.getInstance().getPossibleAliases());
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
		writers.add(new MapWriter(stats));
		writers.add(new ScoresMapWriter(stats));
		writers.add(new ScoreWriter(stats));
		writers.add(new UsersDetailWriter(stats));
		writers.add(new UsersWriter(stats));
		writers.add(new WeaponWriter(stats));
		return writers;
	}

}
