package tb.tartifouette.weblive.tail;

import java.text.ParseException;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.log4j.Logger;

import tb.tartifouette.utlog.AliasManager;
import tb.tartifouette.utlog.LineParser;
import tb.tartifouette.weblive.Statistics;

public class UtLogTailListener implements TailerListener {

	private static final Logger log = Logger.getLogger(UtLogTailListener.class);

	private LineParser lineParser;

	@Override
	public void init(Tailer tailer) {
		AliasManager mgr = new AliasManager();
		lineParser = new LineParser(Statistics.getGlobalStats(), mgr);

	}

	@Override
	public void fileNotFound() {
		log.error("File not found");

	}

	@Override
	public void fileRotated() {
		log.warn("File rotated event not handled");

	}

	@Override
	public void handle(String line) {
		try {
			lineParser.parseLine(line);
		} catch (ParseException e) {
			log.error("Error during line handling [" + line + "]", e);
		}

	}

	@Override
	public void handle(Exception ex) {
		log.error("Error", ex);

	}
}
