package tb.tartifouette.utlog.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionsBuilder {

	public static final Option ALIAS = createAliasOption();
	public static final Option OUTPUR_DIR = createOutputDirOption();
	public static final Option INPUT_DIR = createInputDirOption();

	public Options createOptions() {
		Options options = new Options();
		options.addOption(ALIAS);
		options.addOption(OUTPUR_DIR);
		options.addOption(INPUT_DIR);

		return options;

	}

	private static Option createOutputDirOption() {
		return Option.builder("o").argName("outputDir").hasArg().desc("the output dir name").required().build();
	}

	private static Option createAliasOption() {
		return Option.builder("a").argName("alias").hasArg().desc("the optional alias file").build();
	}

	private static Option createInputDirOption() {
		return Option.builder("i").argName("input").hasArg().desc("the input dir or file").required().build();
	}

}
