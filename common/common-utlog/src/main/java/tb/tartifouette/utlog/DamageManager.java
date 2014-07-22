package tb.tartifouette.utlog;

import java.util.HashMap;
import java.util.Map;

import tb.tartifouette.utlog.HitResolver.BodyPart;
import tb.tartifouette.utlog.HitResolver.Weapon;

public class DamageManager {

	private final Map<String, Integer> damages = new HashMap<String, Integer>();

	private static final DamageManager instance = new DamageManager();

	private DamageManager() {
		init();
	}

	private void init() {
		// HK69,SPAS12 : no precise damage: go to default (0)

		addDamage(Weapon.G36, BodyPart.HEAD, 100);
		addDamage(Weapon.G36, BodyPart.HELMET, 51);
		addDamage(Weapon.G36, BodyPart.TORSO, 44);
		addDamage(Weapon.G36, BodyPart.KEVLAR, 29);
		addDamage(Weapon.G36, BodyPart.ARMS, 17);
		addDamage(Weapon.G36, BodyPart.LEGS, 17);

		addDamage(Weapon.AK, BodyPart.HEAD, 100);
		addDamage(Weapon.AK, BodyPart.HELMET, 58);
		addDamage(Weapon.AK, BodyPart.TORSO, 51);
		addDamage(Weapon.AK, BodyPart.KEVLAR, 34);
		addDamage(Weapon.AK, BodyPart.ARMS, 19);
		addDamage(Weapon.AK, BodyPart.LEGS, 19);

		addDamage(Weapon.LR300, BodyPart.HEAD, 100);
		addDamage(Weapon.LR300, BodyPart.HELMET, 51);
		addDamage(Weapon.LR300, BodyPart.TORSO, 44);
		addDamage(Weapon.LR300, BodyPart.KEVLAR, 29);
		addDamage(Weapon.LR300, BodyPart.ARMS, 17);
		addDamage(Weapon.LR300, BodyPart.LEGS, 17);

		addDamage(Weapon.PSG1, BodyPart.HEAD, 100);
		addDamage(Weapon.PSG1, BodyPart.HELMET, 63);
		addDamage(Weapon.PSG1, BodyPart.TORSO, 97);
		addDamage(Weapon.PSG1, BodyPart.KEVLAR, 63);
		addDamage(Weapon.PSG1, BodyPart.ARMS, 36);
		addDamage(Weapon.PSG1, BodyPart.LEGS, 36);

		addDamage(Weapon.SR8, BodyPart.HEAD, 100);
		addDamage(Weapon.SR8, BodyPart.HELMET, 100);
		addDamage(Weapon.SR8, BodyPart.TORSO, 100);
		addDamage(Weapon.SR8, BodyPart.KEVLAR, 100);
		addDamage(Weapon.SR8, BodyPart.ARMS, 50);
		addDamage(Weapon.SR8, BodyPart.LEGS, 50);

		addDamage(Weapon.NEGEV, BodyPart.HEAD, 50);
		addDamage(Weapon.NEGEV, BodyPart.HELMET, 34);
		addDamage(Weapon.NEGEV, BodyPart.TORSO, 30);
		addDamage(Weapon.NEGEV, BodyPart.KEVLAR, 20);
		addDamage(Weapon.NEGEV, BodyPart.ARMS, 11);
		addDamage(Weapon.NEGEV, BodyPart.LEGS, 11);

		addDamage(Weapon.M4, BodyPart.HEAD, 100);
		addDamage(Weapon.M4, BodyPart.HELMET, 51);
		addDamage(Weapon.M4, BodyPart.TORSO, 44);
		addDamage(Weapon.M4, BodyPart.KEVLAR, 29);
		addDamage(Weapon.M4, BodyPart.ARMS, 17);
		addDamage(Weapon.M4, BodyPart.LEGS, 17);

		addDamage(Weapon.MP5K, BodyPart.HEAD, 50);
		addDamage(Weapon.MP5K, BodyPart.HELMET, 34);
		addDamage(Weapon.MP5K, BodyPart.TORSO, 30);
		addDamage(Weapon.MP5K, BodyPart.KEVLAR, 20);
		addDamage(Weapon.MP5K, BodyPart.ARMS, 11);
		addDamage(Weapon.MP5K, BodyPart.LEGS, 11);

		addDamage(Weapon.UMP, BodyPart.HEAD, 100);
		addDamage(Weapon.UMP, BodyPart.HELMET, 51);
		addDamage(Weapon.UMP, BodyPart.TORSO, 44);
		addDamage(Weapon.UMP, BodyPart.KEVLAR, 29);
		addDamage(Weapon.UMP, BodyPart.ARMS, 17);
		addDamage(Weapon.UMP, BodyPart.LEGS, 17);

		addDamage(Weapon.BERETTA, BodyPart.HEAD, 100);
		addDamage(Weapon.BERETTA, BodyPart.HELMET, 34);
		addDamage(Weapon.BERETTA, BodyPart.TORSO, 30);
		addDamage(Weapon.BERETTA, BodyPart.KEVLAR, 20);
		addDamage(Weapon.BERETTA, BodyPart.ARMS, 11);
		addDamage(Weapon.BERETTA, BodyPart.LEGS, 11);

		addDamage(Weapon.BERETTA, BodyPart.HEAD, 100);
		addDamage(Weapon.BERETTA, BodyPart.HELMET, 34);
		addDamage(Weapon.BERETTA, BodyPart.TORSO, 30);
		addDamage(Weapon.BERETTA, BodyPart.KEVLAR, 20);
		addDamage(Weapon.BERETTA, BodyPart.ARMS, 11);
		addDamage(Weapon.BERETTA, BodyPart.LEGS, 11);

		addDamage(Weapon.DEAGLE, BodyPart.HEAD, 100);
		addDamage(Weapon.DEAGLE, BodyPart.HELMET, 66);
		addDamage(Weapon.DEAGLE, BodyPart.TORSO, 57);
		addDamage(Weapon.DEAGLE, BodyPart.KEVLAR, 38);
		addDamage(Weapon.DEAGLE, BodyPart.ARMS, 22);
		addDamage(Weapon.DEAGLE, BodyPart.LEGS, 22);

		addDamage(Weapon.KNIFE, BodyPart.HEAD, 100);
		addDamage(Weapon.KNIFE, BodyPart.HELMET, 60);
		addDamage(Weapon.KNIFE, BodyPart.TORSO, 44);
		addDamage(Weapon.KNIFE, BodyPart.KEVLAR, 35);
		addDamage(Weapon.KNIFE, BodyPart.ARMS, 20);
		addDamage(Weapon.KNIFE, BodyPart.LEGS, 20);

	}

	private void addDamage(Weapon weapon, BodyPart body, int damage) {
		damages.put(weapon.name() + ":" + body.name(), damage);
	}

	public static DamageManager getInstance() {
		return instance;
	}

	public int getDamage(Weapon weapon, BodyPart body) {
		Integer damage = damages.get(weapon.name() + ":" + body.name());
		if (damage == null) {
			damage = 0;
		}
		return damage;
	}

}
