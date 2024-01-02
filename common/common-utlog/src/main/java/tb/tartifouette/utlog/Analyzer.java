package tb.tartifouette.utlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Analyzer {

	private static final Logger log = LogManager.getLogger(Analyzer.class);

	private static final Pattern P_INPUTFILENAME = Pattern.compile("games\\.log\\.(\\d{4}_\\d{2}_\\d{2})");

	private final String destDirectory;
	private String input;

	private final AliasManager aliasManager;

	public Analyzer(String destDirectory, AliasManager aliasManager) {
		this.aliasManager = aliasManager;
		this.destDirectory = destDirectory;
	}

	public void analyze() throws Exception {
		List<File> filesToAnalyze = getFilesToAnaylyze();
		String dateGuessedFromInput = guessDateFromInputFile(filesToAnalyze);
		Stats stats = new Stats();
		for (File file : filesToAnalyze) {
			if (file.isFile() && file.canRead()) {
				InputStream is = new FileInputStream(file);
				try {
					analyzeFile(stats, is, file.getName());
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
		}
		ReportGenerator generator = new ReportGenerator(stats, destDirectory, dateGuessedFromInput);
		generator.generateReport(aliasManager);
	}

	private String guessDateFromInputFile(List<File> filesToAnalyze) {
		String date = null;
		if (filesToAnalyze.size() == 1) {

			Matcher matcher = P_INPUTFILENAME.matcher(filesToAnalyze.get(0).getName());
			if (matcher.matches()) {
				date = matcher.group(1);
			}
			date = date.replaceAll("_", "-");
		}
		return date;
	}

	private List<File> getFilesToAnaylyze() {
		List<File> files = new ArrayList<File>();
		File inputFile = new File(input);
		if (inputFile.isFile()) {
			files.add(inputFile);
		}
		if (inputFile.isDirectory()) {
			files.addAll(Arrays.asList(inputFile.listFiles()));
		}

		return files;
	}

	public void setInput(String input) {
		this.input = input;

	}

	protected void analyzeFile(Stats stats, InputStream fileInputStream, String fileName)
			throws FileNotFoundException, IOException, ParseException {

		BufferedReader reader = null;
		Reader isReader = null;
		GZIPInputStream gzis = null;
		try {
			log.info("Reading file " + fileName);
			if (fileName.endsWith(".gz")) {
				gzis = new GZIPInputStream(fileInputStream);
				isReader = new InputStreamReader(gzis);
				reader = new BufferedReader(isReader);
			} else {
				isReader = new InputStreamReader(fileInputStream);
				reader = new BufferedReader(isReader);
			}
			String line = reader.readLine();
			for (; line != null; line = reader.readLine()) {
				LineParser lineParser = new LineParser(stats, aliasManager);
				lineParser.parseLine(line);
			}

		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(isReader);
			IOUtils.closeQuietly(gzis);
		}
	}

}
