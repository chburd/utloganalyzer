package tb.tartifouette.utlog;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class LineParserTest {

	@Test
	public void testParseMap() throws Exception {
		Stats stats = new Stats();
		String lineMap = readFile("InitGame.test");
		LineParser parser = new LineParser(stats);
		assertNull(Context.getInstance().getCurrentMap());
		parser.parseLine(lineMap);
		assertEquals("ut4_orbital", Context.getInstance().getCurrentMap());

	}

	private String readFile(String fileName) throws IOException {
		return FileUtils.readFileToString(new File("src/test/resources/"
				+ fileName));
	}

}
