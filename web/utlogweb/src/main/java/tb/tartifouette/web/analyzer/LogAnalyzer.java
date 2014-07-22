package tb.tartifouette.web.analyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import tb.tartifouette.utlog.AliasManager;
import tb.tartifouette.utlog.LineParser;
import tb.tartifouette.utlog.Stats;

public class LogAnalyzer {

	private static final Logger log = Logger.getLogger(LogAnalyzer.class);

	private final List<Input> items;

	private final List<String> dates;

	private final AliasManager aliasManager;

	private static final SimpleDateFormat YMD_DATE_PARSER = new SimpleDateFormat(
			"yyyy_MM_dd");
	private static final SimpleDateFormat YMD_DASH_PARSER = new SimpleDateFormat(
			"yyyy-MM-dd");

	public LogAnalyzer(List<Input> items, AliasManager aliasManager) {
		this.items = items;
		this.aliasManager = aliasManager;
		dates = new ArrayList<String>();
	}

	public Stats analyze() throws FileNotFoundException, IOException,
			ParseException {
		Stats stats = new Stats();
		for (Input item : items) {
			analyzeFile(stats, item);
		}
		return stats;
	}

	protected void analyzeFile(Stats stats, Input file) throws IOException,
			ParseException {

		BufferedReader reader = null;
		Reader isReader = null;
		GZIPInputStream gzis = null;
		InputStream inputStream = file.getInputStream();
		try {
			log.info("Reading file " + file.getFileName());
			addParsedDate(getParsedDateFromFileName(file.getFileName()));
			if (file.getFileName().endsWith(".gz")) {
				gzis = new GZIPInputStream(inputStream);
				isReader = new InputStreamReader(gzis);
				reader = new BufferedReader(isReader);
			} else {
				isReader = new InputStreamReader(inputStream);
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

	private String getParsedDateFromFileName(String name) throws ParseException {
		Date date = extractFileDateFromFileName(name);
		String formatted = null;
		if (date != null) {
			formatted = YMD_DASH_PARSER.format(date);
		}
		return formatted;
	}

	private void addParsedDate(String parsedDateFromFileName) {
		if (parsedDateFromFileName != null) {
			dates.add(parsedDateFromFileName);
		}

	}

	private static final Pattern DATE = Pattern
			.compile(".*(\\d{4}_\\d{2}_\\d{2}).*");

	private Date extractFileDateFromFileName(String path) throws ParseException {
		Matcher matcher = DATE.matcher(path);
		Date date = null;
		if (matcher.matches()) {
			String dateS = matcher.group(1);
			date = YMD_DATE_PARSER.parse(dateS);
			log.info("File " + path + " ==> " + dateS);
		}
		return date;
	}

	public String getDateHint() {
		if (dates.isEmpty()) {
			return null;
		}
		if (dates.size() == 1) {
			return dates.get(0);
		}
		return arrayToString(dates);
	}

	private String arrayToString(List<String> dates) {
		Collections.sort(dates);
		StringBuilder sb = new StringBuilder(dates.get(0));
		for (int i = 1; i < dates.size(); i++) {
			sb.append("--");
			sb.append(dates.get(i));
		}
		return sb.toString();
	}
}
