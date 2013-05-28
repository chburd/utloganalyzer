package tb.tartifouette.utlog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tb.tartifouette.utlog.keys.UserMap;
import tb.tartifouette.utlog.keys.WeaponPerKiller;
import tb.tartifouette.utlog.keys.WhoKilledWho;
import tb.tartifouette.utlog.keys.WhoKilledWhoWithWhat;
import tb.tartifouette.utlog.values.MapResult;
import tb.tartifouette.utlog.values.UserScore;
import tb.tartifouette.utlog.values.UserStats;

public class StatsTest {

	private static final String MAP1 = "map1";
	private static final String MAP2 = "map2";
	private static final String USER1 = "toto";
	private static final String USER2 = "titi";
	private static final String WEAPON = "AK47";
	private Stats subject;

	@Before
	public void setUp() {
		subject = new Stats();
		Context.getInstance().setCurrentMap(MAP1);
	}

	@Test
	public void testUpdateEnvironmentKill() {
		assertEmptyScores();
		subject.updateEnvironmentKill(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getTotalEnvironment());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalEnvironment());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateEnvironmentKill(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getTotalEnvironment());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalEnvironment());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getTotalEnvironment());

	}

	@Test
	public void testUpdateSuicide() {
		assertEmptyScores();
		subject.updateSuicide(USER1);

		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getTotalSuicides());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalSuicides());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateSuicide(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getTotalSuicides());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalSuicides());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getTotalSuicides());
	}

	@Test
	public void testUpdateUserScore() {
		assertEmptyScores();
		subject.updateUserScore(USER1, 12, 600);

		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getNbPlays());
		assertEquals(12, score.getTotalScore());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getNbPlays());
		assertEquals(12, score.getTotalScore());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateUserScore(USER1, 20, 600);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getNbPlays());
		assertEquals(32, score.getTotalScore());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getNbPlays());
		assertEquals(12, score.getTotalScore());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getNbPlays());
		assertEquals(20, score.getTotalScore());
	}

	@Test
	public void testUpdateTeamFlag() {
		assertEmptyScores();
		subject.updateTeamFlag(Stats.BLUE);
		assertEquals(1, subject.getStatsTeamFlag().size());
		MapResult score = getTeamScore(MAP1);
		assertNotNull(score);
		assertEquals(1, score.getFlagBlue());
		assertEquals(0, score.getFlagRed());
	}

	@Test
	public void testIsBlue() {
		assertTrue(subject.isBlue(Stats.BLUE));
		assertFalse(subject.isBlue(Stats.RED));
	}

	@Test
	public void testUpdateTeamKiller() {
		assertEmptyScores();
		subject.updateTeamKiller(USER1);

		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getTeamKiller());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTeamKiller());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateTeamKiller(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getTeamKiller());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTeamKiller());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getTeamKiller());
	}

	@Test
	public void testUpdateTeamKilled() {
		assertEmptyScores();
		subject.updateTeamKilled(USER1);

		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getTeamKilled());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTeamKilled());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateTeamKilled(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getTeamKilled());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTeamKilled());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getTeamKilled());
	}

	@Test
	public void testUpdateUserFlag() {
		assertEmptyScores();
		subject.updateUserFlag(USER1);

		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getFlags());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getFlags());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateUserFlag(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getFlags());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getFlags());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getFlags());
	}

	@Test
	public void testUpdateWhoIsKilled() {
		assertEmptyScores();
		subject.updateWhoIsKilled(USER1);

		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getTotalDeaths());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalDeaths());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateWhoIsKilled(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getTotalDeaths());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalDeaths());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getTotalDeaths());
	}

	@Test
	public void testUpdateWhoKilled() {
		assertEmptyScores();
		subject.updateWhoKilled(USER1);

		assertEquals(1, subject.getStatsUserScore().size());
		UserScore score = getScore(USER1);

		assertNotNull(score);
		assertEquals(1, score.getTotalFrags());

		assertEquals(1, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalFrags());

		Context.getInstance().setCurrentMap(MAP2);

		subject.updateWhoKilled(USER1);
		assertEquals(1, subject.getStatsUserScore().size());
		score = getScore(USER1);

		assertNotNull(score);
		assertEquals(2, score.getTotalFrags());

		assertEquals(2, subject.getStatsUserMapScore().size());
		score = getScore(USER1, MAP1);
		assertNotNull(score);
		assertEquals(1, score.getTotalFrags());

		score = getScore(USER1, MAP2);
		assertNotNull(score);
		assertEquals(1, score.getTotalFrags());
	}

	@Test
	public void testUpdateWeaponPerKiller() {
		assertEmptyScores();
		subject.updateWeaponPerKiller(USER1, WEAPON);
		Map<WeaponPerKiller, Integer> statsKills = subject.getStatsWeapons();
		WeaponPerKiller key = new WeaponPerKiller();
		key.setKiller(USER1);
		key.setWeapon(WEAPON);
		assertEquals(1, statsKills.get(key).intValue());
	}

	@Test
	public void testUpdateWhoKilledWho() {
		assertEmptyScores();
		subject.updateWhoKilledWho(USER1, USER2);
		Map<WhoKilledWho, UserStats> statsKills1 = subject.getStatsKills2();
		WhoKilledWho key = new WhoKilledWho();
		key.setKiller(USER1);
		key.setKilled(USER2);
		UserStats stats = statsKills1.get(key);
		assertEquals(1, stats.getNbFrags());

		key = new WhoKilledWho();
		key.setKiller(USER2);
		key.setKilled(USER1);
		stats = statsKills1.get(key);
		assertEquals(1, stats.getNbKilled());
	}

	@Test
	public void testUpdateWhoKilledWhoWithWhat() {
		assertEmptyScores();
		subject.updateWhoKilledWhoWithWhat(USER1, USER2, WEAPON);
		Map<WhoKilledWhoWithWhat, Integer> statsKills1 = subject
				.getStatsKills1();
		WhoKilledWhoWithWhat key = new WhoKilledWhoWithWhat();
		key.setKiller(USER1);
		key.setKilled(USER2);
		key.setWeapon(WEAPON);
		assertEquals(1, statsKills1.get(key).intValue());
	}

	private UserScore getScore(String user) {
		Map<String, UserScore> scores = subject.getStatsUserScore();
		assertNotNull(scores);
		return scores.get(USER1);
	}

	private UserScore getScore(String user, String map) {
		Map<UserMap, UserScore> scores = subject.getStatsUserMapScore();
		assertNotNull(scores);
		return scores.get(new UserMap(user, map));
	}

	private void assertEmptyScores() {
		assertTrue(subject.getStatsUserScore().isEmpty());
		assertTrue(subject.getStatsUserMapScore().isEmpty());
		assertTrue(subject.getStatsTeamFlag().isEmpty());
		assertTrue(subject.getStatsWeapons().isEmpty());
		assertTrue(subject.getStatsKills2().isEmpty());
		assertTrue(subject.getStatsKills1().isEmpty());
	}

	private MapResult getTeamScore(String map) {
		return subject.getStatsTeamFlag().get(map);
	}

}
