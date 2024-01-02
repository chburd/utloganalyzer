package tb.tartifouette.utlog.cli;

import org.apache.commons.cli.*;

import tb.tartifouette.utlog.AliasManager;
import tb.tartifouette.utlog.Analyzer;

public class Main {
	public static void main(String... args) throws Exception {
		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(new OptionsBuilder().createOptions(), args);

		AliasManager mgr = new AliasManager();
		Analyzer analyzer = new Analyzer(line.getOptionValue(OptionsBuilder.OUTPUR_DIR), mgr);
		analyzer.setInput(line.getOptionValue(OptionsBuilder.INPUT_DIR));
		analyzer.analyze();
	}
}
