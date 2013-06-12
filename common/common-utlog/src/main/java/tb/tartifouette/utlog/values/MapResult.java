package tb.tartifouette.utlog.values;

public class MapResult implements Comparable<MapResult> {
	private int flagRed;
	private int flagBlue;

	@Override
	public int compareTo(MapResult o) {
		return (int) (100 * (computeRatio() - o.computeRatio()));
	}

	public double computeRatio() {
		double ratio = 0;
		int totalPlays = flagBlue + flagRed;
		if (totalPlays > 0) {
			ratio = (double) flagBlue / ((double) totalPlays);
		}
		return ratio;
	}

	public int getFlagRed() {
		return flagRed;
	}

	public void setFlagRed(int flagRed) {
		this.flagRed = flagRed;
	}

	public int getFlagBlue() {
		return flagBlue;
	}

	public void setFlagBlue(int flagBlue) {
		this.flagBlue = flagBlue;
	}

}
