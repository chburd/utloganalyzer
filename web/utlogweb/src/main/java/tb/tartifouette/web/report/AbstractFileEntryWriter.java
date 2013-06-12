package tb.tartifouette.web.report;

import java.io.CharArrayWriter;
import java.io.IOException;
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

	public AbstractFileEntryWriter(Stats stats) {
		this.stats = stats;
	}

	public void writeFileEntry(ZipOutputStream zos) throws IOException {
		CharArrayWriter writer = null;
		ZipEntry zipEntry = new ZipEntry(getFileName());
		try {
			log.info("Writing report file " + getFileName());
			String content = getContent();
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
}
