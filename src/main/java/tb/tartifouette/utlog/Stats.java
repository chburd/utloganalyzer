package tb.tartifouette.utlog;

import java.util.HashMap;
import java.util.Map;

import tb.tartifouette.utlog.keys.UserMap;
import tb.tartifouette.utlog.keys.WeaponPerKiller;
import tb.tartifouette.utlog.keys.WhoKilledWho;
import tb.tartifouette.utlog.keys.WhoKilledWhoWithWhat;
import tb.tartifouette.utlog.values.MapResult;
import tb.tartifouette.utlog.values.UserScore;
import tb.tartifouette.utlog.values.UserStats;

public class Stats {

    public static Map<WhoKilledWhoWithWhat, Integer> statsKills1 =
            new HashMap<WhoKilledWhoWithWhat, Integer>();

    public static Map<WhoKilledWho, UserStats> statsKills2 = new HashMap<WhoKilledWho, UserStats>();

    public static Map<WeaponPerKiller, Integer> statsWeapons =
            new HashMap<WeaponPerKiller, Integer>();

    public static Map<String, MapResult> statsTeamFlag = new HashMap<String, MapResult>();

    public static Map<String, UserScore> statsUserScore = new HashMap<String, UserScore>();

    public static Map<UserMap, UserScore> statsUserMapScore = new HashMap<UserMap, UserScore>();

    public static void updateEnvironmentKill(String user) {
        UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalEnvironment(existingScore.getTotalEnvironment() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalEnvironment(existingScore.getTotalEnvironment() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);
    }

    public static void updateSuicide(String user) {
        UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalSuicides(existingScore.getTotalSuicides() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalSuicides(existingScore.getTotalSuicides() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);
    }

    public static void updateUserScore(String user, int score) {
        UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalScore(existingScore.getTotalScore() + score);
        existingScore.setNbPlays(existingScore.getNbPlays() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalScore(existingScore.getTotalScore() + score);
        existingScore.setNbPlays(existingScore.getNbPlays() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);
    }

    public static void updateTeamFlag(String team) {
        MapResult result = Stats.statsTeamFlag.get(Context.getInstance().getCurrentMap());
        if (result == null) {
            result = new MapResult();
        }
        if (isBlue(team)) {
            result.flagBlue++;
        } else {
            result.setFlagRed(result.getFlagRed() + 1);
        }
        Stats.statsTeamFlag.put(Context.getInstance().getCurrentMap(), result);
    }

    public static boolean isBlue(String team) {
        return "team_CTF_blueflag".equals(team);
    }

	public static void updateTeamKiller(String user) {
		UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTeamKiller(existingScore.getTeamKiller() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTeamKiller(existingScore.getTeamKiller() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);
	}

	public static void updateTeamKilled(String user) {
		UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTeamKilled(existingScore.getTeamKilled() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTeamKilled(existingScore.getTeamKilled() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);
	}
    
    public static void updateUserFlag(String user) {
        UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setFlags(existingScore.getFlags() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setFlags(existingScore.getFlags() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);
    }

    public static void updateWhoIsKilled(String user) {
        UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalDeaths(existingScore.getTotalDeaths() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalDeaths(existingScore.getTotalDeaths() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);

    }

    public static void updateWhoKilled(String user) {
        UserScore existingScore = Stats.statsUserScore.get(user);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalFrags(existingScore.getTotalFrags() + 1);
        Stats.statsUserScore.put(user, existingScore);

        UserMap userMap = new UserMap(user, Context.getInstance().getCurrentMap());
        existingScore = statsUserMapScore.get(userMap);
        if (existingScore == null) {
            existingScore = new UserScore();
        }
        existingScore.setTotalFrags(existingScore.getTotalFrags() + 1);
        Stats.statsUserMapScore.put(userMap, existingScore);
    }

    public static void updateWheaponPerKiller(String killer, String weapon) {
        WeaponPerKiller key = new WeaponPerKiller();
        key.setKiller(killer);
        key.setWeapon(weapon);
        Integer count = Stats.statsWeapons.get(key);
        if (count == null) {
            count = 0;
        }
        count++;
        Stats.statsWeapons.put(key, count);
    }

    public static void updateWhoKilledWho(String killer, String killed) {
        WhoKilledWho key = new WhoKilledWho();
        key.setKiller(killer);
        key.setKilled(killed);
        UserStats count = Stats.statsKills2.get(key);
        if (count == null) {
            count = new UserStats();
        }
        count.setNbFrags(count.getNbFrags() + 1);
        Stats.statsKills2.put(key, count);

        key = new WhoKilledWho();
        key.setKiller(killed);
        key.setKilled(killer);
        count = Stats.statsKills2.get(key);
        if (count == null) {
            count = new UserStats();
        }
        count.setNbKilled(count.getNbKilled() + 1);
        Stats.statsKills2.put(key, count);

    }

    public static void updateWhoKilledWhoWithWhat(String killer, String killed, String weapon) {
        WhoKilledWhoWithWhat key = new WhoKilledWhoWithWhat();
        key.setKiller(killer);
        key.setKilled(killed);
        key.setWeapon(weapon);
        Integer count = Stats.statsKills1.get(key);
        if (count == null) {
            count = 0;
        }
        count++;
        Stats.statsKills1.put(key, count);
    }



}
