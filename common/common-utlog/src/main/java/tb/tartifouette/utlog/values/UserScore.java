package tb.tartifouette.utlog.values;

public class UserScore implements Comparable<UserScore> {

	private int nbPlays;
	private int totalScore;
	private int totalFrags;
	private int totalDeaths;
	private int totalSuicides;
	private int totalEnvironment;
	private int flagsCaptured;
	private int flagsReturned;
	private int teamKiller;
	private int teamKilled;
	private int bestFragSerie;
	private int worseKillSerie;
	private int bestFragSerieDurationInS;
	private int worstKillSerieDurationInS;
	private int hitsGiven;
	private int damageGiven;
	private int hitsReceived;
	private int damageReceived;

	@Override
	public int compareTo(UserScore o) {
		return (int) (1000 * getScorePerPlay() - 1000 * o.getScorePerPlay());
	}

	public void add(UserScore value) {
		nbPlays += value.nbPlays;
		totalScore += value.totalScore;
		totalFrags += value.totalFrags;
		totalDeaths += value.totalDeaths;
		totalSuicides += value.totalSuicides;
		totalEnvironment += value.totalEnvironment;
		flagsCaptured += value.flagsCaptured;
		flagsReturned += value.flagsReturned;
		teamKiller += value.teamKiller;
		teamKilled += value.teamKilled;
		hitsGiven += value.hitsGiven;
		damageGiven += value.damageGiven;
		hitsReceived += value.hitsReceived;
		damageReceived += value.damageReceived;
		if (bestFragSerie < value.bestFragSerie) {
			bestFragSerie = value.bestFragSerie;
		}
		if (worseKillSerie > value.worseKillSerie) {
			worseKillSerie = value.worseKillSerie;
		}
		if (bestFragSerieDurationInS < value.bestFragSerieDurationInS) {
			bestFragSerieDurationInS = value.bestFragSerieDurationInS;
		}
		if (worstKillSerieDurationInS < value.worstKillSerieDurationInS) {
			worstKillSerieDurationInS = value.worstKillSerieDurationInS;
		}
	}

	public String computeScorePerPlay() {
		double result = getScorePerPlay();
		return String.valueOf(result);
	}

	private double getScorePerPlay() {
		double result = 0;
		if (nbPlays != 0) {
			result = (double) totalScore / nbPlays;
		}
		return result;
	}

	public String computeFlagPerPlay() {
		double result = 0;
		if (nbPlays != 0) {
			result = (double) flagsCaptured / nbPlays;
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

	public int getFlagsCaptured() {
		return flagsCaptured;
	}

	public void setFlagsCaptured(int flagsCaptured) {
		this.flagsCaptured = flagsCaptured;
	}

	public int getFlagsReturned() {
		return flagsReturned;
	}

	public void setFlagsReturned(int flagsReturned) {
		this.flagsReturned = flagsReturned;
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

	public int getBestFragSerie() {
		return bestFragSerie;
	}

	public void setBestFragSerie(int bestFragSerie) {
		this.bestFragSerie = bestFragSerie;
	}

	public int getWorseKillSerie() {
		return worseKillSerie;
	}

	public void setWorseKillSerie(int worseKillSerie) {
		this.worseKillSerie = worseKillSerie;
	}

	public int getBestFragSerieDurationInS() {
		return bestFragSerieDurationInS;
	}

	public void setBestFragSerieDurationInS(int bestFragSerieDurationInS) {
		this.bestFragSerieDurationInS = bestFragSerieDurationInS;
	}

	public int getWorstKillSerieDurationInS() {
		return worstKillSerieDurationInS;
	}

	public void setWorstKillSerieDurationInS(int worstKillSerieDurationInS) {
		this.worstKillSerieDurationInS = worstKillSerieDurationInS;
	}

	public int getHitsGiven() {
		return hitsGiven;
	}

	public void setHitsGiven(int hitsGiven) {
		this.hitsGiven = hitsGiven;
	}

	public int getDamageGiven() {
		return damageGiven;
	}

	public void setDamageGiven(int damageGiven) {
		this.damageGiven = damageGiven;
	}

	public int getHitsReceived() {
		return hitsReceived;
	}

	public void setHitsReceived(int hitsReceived) {
		this.hitsReceived = hitsReceived;
	}

	public int getDamageReceived() {
		return damageReceived;
	}

	public void setDamageReceived(int damageReceived) {
		this.damageReceived = damageReceived;
	}

	public void incrementHitsGiven() {
		hitsGiven++;

	}

	public void addDamageGiven(int damage) {
		damageGiven += damage;

	}

	public void incrementHitsReceived() {
		hitsReceived++;

	}

	public void addDamageReceived(int damage) {
		damageReceived += damage;
	}

}
