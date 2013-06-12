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
    private int bestFragSerie;
    private int worseKillSerie;
    private int bestFragSerieDurationInS;
    private int worstKillSerieDurationInS;

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
        double result = 0;
        if (nbPlays != 0) {
            result = (double)totalScore / nbPlays;
        }
        return String.valueOf(result);
    }

    public String computeFlagPerPlay() {
        double result = 0;
        if (nbPlays != 0) {
            result = (double)flags / nbPlays;
        }
        return String.valueOf(result);
    }

    public String computeFragPerPlay() {
        double result = 0;
        if (nbPlays != 0) {
            result = (double)totalFrags / nbPlays;
        }
        return String.valueOf(result);
    }

    public String computeFragPerDeathRatio() {
        double result = 0;
        if (nbPlays != 0) {
            result = (double)totalFrags / totalDeaths;
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

}
