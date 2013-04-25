package tb.tartifouette.utlog.values;

public class UserStats implements Comparable<UserStats> {

	private int nbFrags;
	private int nbKilled;

	@Override
	public int compareTo(UserStats o) {
		return nbFrags - nbKilled;
	}

	public int getNbFrags() {
		return nbFrags;
	}

	public void setNbFrags(int nbFrags) {
		this.nbFrags = nbFrags;
	}

	public int getNbKilled() {
		return nbKilled;
	}

	public void setNbKilled(int nbKilled) {
		this.nbKilled = nbKilled;
	}

}
