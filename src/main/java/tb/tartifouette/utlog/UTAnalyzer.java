package tb.tartifouette.utlog;

public class UTAnalyzer {

	public static void main(String[] args) {
		try {
			String fileName = System.getProperty("file");
			String dirName = System.getProperty("dir");
			if ((fileName != null && dirName != null)
					|| (fileName == null && dirName == null)) {
				System.err.println("Usage -Dfile=xx ou -Ddir=xxx");
			}
			String destFile = args[0];
			Analyzer analyzer = new Analyzer(destFile);
			if (dirName != null) {
				analyzer.setDirName(dirName);
			}
			if (fileName != null) {
				analyzer.setFileName(fileName);
			}
			analyzer.analyze();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
