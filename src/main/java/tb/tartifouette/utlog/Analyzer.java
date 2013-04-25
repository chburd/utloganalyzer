package tb.tartifouette.utlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Analyzer {

	private static final Log log = LogFactory.getLog(Analyzer.class);;

	private String destDirectory;
	private String fileName;
	private String dirName;

	public Analyzer(String destDirectory) {

		this.destDirectory = destDirectory;
	}

	public void analyze() throws Exception {
		List<File> filesToAnalyze = getFilesToAnaylyze();
		Stats stats = new Stats();
		for (File file : filesToAnalyze) {
			if (file.isFile() && file.canRead()) {
				analyzeFile(stats, file);
			}
		}
		ReportGenerator generator = new ReportGenerator(stats, destDirectory);
		generator.generateReport();
	}

	private List<File> getFilesToAnaylyze() {
		List<File> files = new ArrayList<File>();
		if (fileName != null) {
			files.add(new File(fileName));
		}
		if (dirName != null) {
			File directory = new File(dirName);
			files.addAll(Arrays.asList(directory.listFiles()));
		}

		return files;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;

	}

	public void setFileName(String fileName) {
		this.fileName = fileName;

	}

	protected void analyzeFile(Stats stats, File fileToAnalyze)
			throws FileNotFoundException, IOException {

		BufferedReader reader = null;
		Reader isReader = null;
		FileInputStream fileInputStream = null;
		GZIPInputStream gzis = null;
		try {
			log.info("Reading file " + fileToAnalyze);
			fileInputStream = new FileInputStream(fileToAnalyze);
			if (fileToAnalyze.getName().endsWith(".gz")) {
				gzis = new GZIPInputStream(fileInputStream);
				isReader = new InputStreamReader(gzis);
				reader = new BufferedReader(isReader);
			} else {
				isReader = new InputStreamReader(fileInputStream);
				reader = new BufferedReader(isReader);
			}
			String line = reader.readLine();
			for (; line != null; line = reader.readLine()) {
				LineParser lineParser = new LineParser(stats);
				lineParser.parseLine(line);
			}

		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(isReader);
			IOUtils.closeQuietly(gzis);
			IOUtils.closeQuietly(fileInputStream);

		}
	}

}
