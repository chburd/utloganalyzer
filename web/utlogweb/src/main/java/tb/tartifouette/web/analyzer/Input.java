package tb.tartifouette.web.analyzer;

import java.io.InputStream;

public class Input {

	private final String fileName;
	private final InputStream inputStream;

	public Input(String fileName, InputStream inputStream) {
		this.fileName = fileName;
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

}
