package tb.tartifouette.utlog;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import tb.tartifouette.utlog.HitResolver.BodyPart;
import tb.tartifouette.utlog.HitResolver.Weapon;
import tb.tartifouette.utlog.keys.UserMap;
import tb.tartifouette.utlog.keys.WeaponPerKiller;
import tb.tartifouette.utlog.keys.WhoKilledWho;
import tb.tartifouette.utlog.keys.WhoKilledWhoWithWhat;
import tb.tartifouette.utlog.values.MapResult;
import tb.tartifouette.utlog.values.Serie;
import tb.tartifouette.utlog.values.UserScore;
import tb.tartifouette.utlog.values.UserStats;

public class Stats {

	public static final String BLUE = "team_CTF_blueflag";
	public static final String RED = "team_CTF_redflag";

	private final Map<WhoKilledWhoWithWhat, Integer> statsKills1 = new HashMap<WhoKilledWhoWithWhat, Integer>();

	private final Map<WhoKilledWho, UserStats> statsKills2 = new HashMap<WhoKilledWho, UserStats>();

	private final Map<WeaponPerKiller, Integer> statsWeapons = new HashMap<WeaponPerKiller, Integer>();

	private final Map<String, MapResult> statsTeamFlag = new HashMap<String, MapResult>();

	private final Map<UserMap, UserScore> statsUserMapScore = new HashMap<UserMap, UserScore>();

	private final Map<String, Serie> series = new HashMap<String, Serie>();

	public void updateEnvironmentKill(String user) {

		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		existingScore
				.setTotalEnvironment(existingScore.getTotalEnvironment() + 1);
		statsUserMapScore.put(userMap, existingScore);
	}

	private UserScore getOrInitExistingScore(UserMap userMap) {
		UserScore existingScore = statsUserMapScore.get(userMap);
		if (existingScore == null) {
			existingScore = new UserScore();
		}
		return existingScore;
	}

	public void updateSuicide(String user) {

		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		existingScore.setTotalSuicides(existingScore.getTotalSuicides() + 1);
		statsUserMapScore.put(userMap, existingScore);
	}

	public void updateUserScore(String user, int score, int time) {

		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		existingScore.setTotalScore(existingScore.getTotalScore() + score);
		existingScore.setNbPlays(existingScore.getNbPlays() + 1);
		statsUserMapScore.put(userMap, existingScore);

		finishSerie(user, time);

	}

	public void updateTeamFlag(String team) {
		MapResult result = statsTeamFlag.get(Context.getInstance()
				.getCurrentMap());
		if (result == null) {
			result = new MapResult();
		}
		if (isBlue(team)) {
			result.setFlagBlue(result.getFlagBlue() + 1);
		} else {
			result.setFlagRed(result.getFlagRed() + 1);
		}
		statsTeamFlag.put(Context.getInstance().getCurrentMap(), result);
	}

	public boolean isBlue(String team) {
		return BLUE.equals(team);
	}

	public void updateTeamKiller(String user) {
		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		existingScore.setTeamKiller(existingScore.getTeamKiller() + 1);
		statsUserMapScore.put(userMap, existingScore);
	}

	public void updateTeamKilled(String user) {

		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		existingScore.setTeamKilled(existingScore.getTeamKilled() + 1);
		statsUserMapScore.put(userMap, existingScore);
	}

	public void updateUserFlag(String user, FlagAction action) {

		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		if (action == FlagAction.FLAG_CAPTURED) {
			existingScore
					.setFlagsCaptured(existingScore.getFlagsCaptured() + 1);
		} else if (action == FlagAction.FLAG_RETURNED) {
			existingScore
					.setFlagsReturned(existingScore.getFlagsReturned() + 1);
		}
		statsUserMapScore.put(userMap, existingScore);
	}

	public void updateWhoIsKilled(String user) {

		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		existingScore.setTotalDeaths(existingScore.getTotalDeaths() + 1);
		statsUserMapScore.put(userMap, existingScore);

	}

	public void updateWhoKilled(String user) {

		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);
		existingScore.setTotalFrags(existingScore.getTotalFrags() + 1);
		statsUserMapScore.put(userMap, existingScore);
	}

	public void updateWeaponPerKiller(String killer, String weapon) {
		WeaponPerKiller key = new WeaponPerKiller();
		key.setKiller(killer);
		key.setWeapon(weapon);
		Integer count = statsWeapons.get(key);
		if (count == null) {
			count = 0;
		}
		count++;
		statsWeapons.put(key, count);
	}

	public void updateWhoKilledWho(String killer, String killed) {
		WhoKilledWho key = new WhoKilledWho();
		key.setKiller(killer);
		key.setKilled(killed);
		UserStats count = statsKills2.get(key);
		if (count == null) {
			count = new UserStats();
		}
		count.setNbFrags(count.getNbFrags() + 1);
		statsKills2.put(key, count);

		key = new WhoKilledWho();
		key.setKiller(killed);
		key.setKilled(killer);
		count = statsKills2.get(key);
		if (count == null) {
			count = new UserStats();
		}
		count.setNbKilled(count.getNbKilled() + 1);
		statsKills2.put(key, count);

	}

	public void updateWhoKilledWhoWithWhat(String killer, String killed,
			String weapon) {
		WhoKilledWhoWithWhat key = new WhoKilledWhoWithWhat();
		key.setKiller(killer);
		key.setKilled(killed);
		key.setWeapon(weapon);
		Integer count = statsKills1.get(key);
		if (count == null) {
			count = 0;
		}
		count++;
		statsKills1.put(key, count);
	}

	public Map<UserMap, UserScore> getStatsUserMapScore() {
		return statsUserMapScore;
	}

	public Map<String, UserScore> getStatsUserScore() {
		Map<String, UserScore> statsUserScore = new HashMap<String, UserScore>();
		for (Entry<UserMap, UserScore> score : statsUserMapScore.entrySet()) {
			UserMap key = score.getKey();
			UserScore stats = statsUserScore.get(key.getUser());
			if (stats == null) {
				stats = new UserScore();
				statsUserScore.put(key.getUser(), stats);
			}
			stats.add(score.getValue());
		}
		return statsUserScore;
	}

	public Map<String, MapResult> getStatsTeamFlag() {
		return statsTeamFlag;
	}

	public Map<WeaponPerKiller, Integer> getStatsWeapons() {
		return statsWeapons;
	}

	public Map<WhoKilledWho, UserStats> getStatsKills2() {
		return statsKills2;
	}

	public Map<WhoKilledWhoWithWhat, Integer> getStatsKills1() {
		return statsKills1;
	}

	public void updateKillSerie(String user, int time) {
		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);

		Serie currentSerie = series.get(user);
		if (currentSerie == null) {
			currentSerie = new Serie();
			series.put(user, currentSerie);
		}
		int oldStartTime = currentSerie.getStartTime();
		int duration = time - oldStartTime;
		if (currentSerie.getCount() > 0) {
			currentSerie.setCount(0);
			currentSerie.setStartTime(time);

			if (duration > existingScore.getBestFragSerieDurationInS()) {
				existingScore.setBestFragSerieDurationInS(duration);
			}
			duration = time - currentSerie.getLastFragTime();
			if (duration > existingScore.getWorstKillSerieDurationInS()) {
				existingScore.setWorstKillSerieDurationInS(duration);
			}
		} else {
			if (duration > existingScore.getWorstKillSerieDurationInS()) {
				existingScore.setWorstKillSerieDurationInS(duration);
			}
		}
		currentSerie.decreaseCount();

		if (currentSerie.getCount() < existingScore.getWorseKillSerie()) {
			existingScore.setWorseKillSerie(currentSerie.getCount());
		}
		currentSerie.setLastKillTime(time);

	}

	private void finishSerie(String user, int time) {
		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);

		Serie currentSerie = series.get(user);
		if (currentSerie == null) {
			currentSerie = new Serie();
			series.put(user, currentSerie);
		}
		if (currentSerie.isInFragSerie()) {
			int duration = time - currentSerie.getLastFragTime();
			if (duration > existingScore.getBestFragSerieDurationInS()) {
				existingScore.setBestFragSerieDurationInS(duration);
			}
		} else {
			int duration = time - currentSerie.getLastKillTime();
			if (duration > existingScore.getWorstKillSerieDurationInS()) {
				existingScore.setWorstKillSerieDurationInS(duration);
			}
		}
	}

	public void updateFragSerie(String user, int time) {
		UserMap userMap = new UserMap(user, Context.getInstance()
				.getCurrentMap());
		UserScore existingScore = getOrInitExistingScore(userMap);

		Serie currentSerie = series.get(user);
		if (currentSerie == null) {
			currentSerie = new Serie();
			series.put(user, currentSerie);
		}
		int oldStartTime = currentSerie.getStartTime();
		int duration = time - oldStartTime;
		if (currentSerie.getCount() < 0) {
			currentSerie.setCount(0);
			currentSerie.setStartTime(time);
			if (duration > existingScore.getWorstKillSerieDurationInS()) {
				existingScore.setWorstKillSerieDurationInS(duration);
			}
			duration = time - currentSerie.getLastKillTime();
			if (duration > existingScore.getBestFragSerieDurationInS()) {
				existingScore.setBestFragSerieDurationInS(duration);
			}
		} else {
			if (duration > existingScore.getBestFragSerieDurationInS()) {
				existingScore.setBestFragSerieDurationInS(duration);
			}
		}
		currentSerie.increaseCount();

		if (currentSerie.getCount() > existingScore.getBestFragSerie()) {
			existingScore.setBestFragSerie(currentSerie.getCount());
		}
		currentSerie.setLastFragTime(time);
	}

	public void resetSeriesStats() {
		series.clear();
	}

	public void setUserStartDate(String user, int time) {
		Serie currentSerie = series.get(user);
		if (currentSerie == null) {
			currentSerie = new Serie();
		}
		currentSerie.setStartTime(time);
	}

    public void addHit(String shouter, String shouted, BodyPart bodyPart, Weapon weapon) {
        // TODO Auto-generated method stub

    }

}
