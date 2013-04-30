package tb.tartifouette.utlog.values;

public class UserScore implements Comparable<UserScore> {

	private int nbPlays;
	private int totalScore;
	private int totalFrags;
	private int totalDeaths;
	private int totalSuicides;
	private int totalEnvironment;
	private int flags;
	private int teamKiller;
	private int teamKilled;

	@Override
	public int compareTo(UserScore o) {
		return totalScore - o.totalScore;
	}

	public void add(UserScore value) {
		nbPlays += value.nbPlays;
		totalScore += value.totalScore;
		totalFrags += value.totalFrags;
		totalDeaths += value.totalDeaths;
		totalSuicides += value.totalSuicides;
		totalEnvironment += value.totalEnvironment;
		flags += value.flags;
		teamKiller += value.teamKiller;
		teamKilled += value.teamKilled;
	}

	public String computeScorePerPlay() {
		double result = 0;
		if (nbPlays != 0) {
			result = (double) totalScore / nbPlays;
		}
		return String.valueOf(result);
	}

	public String computeFlagPerPlay() {
		double result = 0;
		if (nbPlays != 0) {
			result = (double) flags / nbPlays;
		}
		return String.valueOf(result);
	}

	public String computeFragPerPlay() {
		double result = 0;
		if (nbPlays != 0) {
			result = (double) totalFrags / nbPlays;
		}
		return String.valueOf(result);
	}

	public String computeFragPerDeathRatio() {
		double result = 0;
		if (nbPlays != 0) {
			result = (double) totalFrags / totalDeaths;
		}
		return String.valueOf(result);
	}

	public int getNbPlays() {
		return nbPlays;
	}

	public void setNbPlays(int nbPlays) {
		this.nbPlays = nbPlays;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getTotalFrags() {
		return totalFrags;
	}

	public void setTotalFrags(int totalFrags) {
		this.totalFrags = totalFrags;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getTotalSuicides() {
		return totalSuicides;
	}

	public void setTotalSuicides(int totalSuicides) {
		this.totalSuicides = totalSuicides;
	}

	public int getTotalEnvironment() {
		return totalEnvironment;
	}

	public void setTotalEnvironment(int totalEnvironment) {
		this.totalEnvironment = totalEnvironment;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public int getTeamKiller() {
		return teamKiller;
	}

	public void setTeamKiller(int teamKiller) {
		this.teamKiller = teamKiller;
	}

	public int getTeamKilled() {
		return teamKilled;
	}

	public void setTeamKilled(int teamKilled) {
		this.teamKilled = teamKilled;
	}

}
