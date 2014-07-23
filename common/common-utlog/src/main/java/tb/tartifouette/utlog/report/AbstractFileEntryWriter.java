package tb.tartifouette.utlog.report;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import tb.tartifouette.utlog.Stats;

public abstract class AbstractFileEntryWriter {

	protected static final String EOL = "\n";
	protected static final char SEMI_COLUMN = ';';
	private static final Logger log = Logger
			.getLogger(AbstractFileEntryWriter.class);
	protected final Stats stats;
	protected final Locale locale;
	protected final ResourceBundle bundle;

	public AbstractFileEntryWriter(Stats stats, Locale locale) {
		this.stats = stats;
		this.locale = locale;
		bundle = ResourceBundle.getBundle("Report", locale);
	}

	public void writeFileEntry(ZipOutputStream zos) throws IOException {
		CharArrayWriter writer = null;
		ZipEntry zipEntry = new ZipEntry(getFileName());
		try {
			log.info("Writing report file " + getFileName());
			String content = generateTitleLine(getNbTitles()) + getContent();
			zipEntry.setSize(content.length());
			zos.putNextEntry(zipEntry);
			zos.write(content.getBytes());
			zos.closeEntry();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	public abstract String getFileName();

	public abstract String getContent();

	public abstract String getBaseBundleName();

	public abstract int getNbTitles();

	public String generateTitleLine(int nbTitles) {
		String baseBundle = getBaseBundleName();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= nbTitles; i++) {
			sb.append(bundle.getString(baseBundle + i)).append(SEMI_COLUMN);
		}
		sb.append(EOL);
		return sb.toString();
	}

}
