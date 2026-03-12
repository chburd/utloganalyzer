package tb.tartifouette.utlog;

import java.util.HashMap;
import java.util.Map;

import tb.tartifouette.utlog.HitResolver.BodyZone;
import tb.tartifouette.utlog.HitResolver.Weapon;

public class DamageManager {

	private final Map<String, Integer> damages = new HashMap<>();

	private static final DamageManager instance = new DamageManager();

	private DamageManager() {
		init();
	}

	private void init() {
		// HK69,SPAS12 : no precise damage: go to default (0)

        // G36
        addDamage(Weapon.G36, BodyZone.HEAD, 100);
        addDamage(Weapon.G36, BodyZone.HELMET, 51);
        addDamage(Weapon.G36, BodyZone.TORSO, 44);
        addDamage(Weapon.G36, BodyZone.VEST, 29);
        addDamage(Weapon.G36, BodyZone.ARM, 17);
        addDamage(Weapon.G36, BodyZone.GROIN, 31);
        addDamage(Weapon.G36, BodyZone.BUTT, 29);
        addDamage(Weapon.G36, BodyZone.UPPER_LEG, 20);
        addDamage(Weapon.G36, BodyZone.LOWER_LEG, 17);
        addDamage(Weapon.G36, BodyZone.FOOT, 14);

        // LR300
        addDamage(Weapon.LR300, BodyZone.HEAD, 100);
        addDamage(Weapon.LR300, BodyZone.HELMET, 51);
        addDamage(Weapon.LR300, BodyZone.TORSO, 44);
        addDamage(Weapon.LR300, BodyZone.VEST, 29);
        addDamage(Weapon.LR300, BodyZone.ARM, 17);
        addDamage(Weapon.LR300, BodyZone.GROIN, 31);
        addDamage(Weapon.LR300, BodyZone.BUTT, 29);
        addDamage(Weapon.LR300, BodyZone.UPPER_LEG, 20);
        addDamage(Weapon.LR300, BodyZone.LOWER_LEG, 17);
        addDamage(Weapon.LR300, BodyZone.FOOT, 14);

        // M4
        addDamage(Weapon.M4, BodyZone.HEAD, 100);
        addDamage(Weapon.M4, BodyZone.HELMET, 51);
        addDamage(Weapon.M4, BodyZone.TORSO, 44);
        addDamage(Weapon.M4, BodyZone.VEST, 29);
        addDamage(Weapon.M4, BodyZone.ARM, 17);
        addDamage(Weapon.M4, BodyZone.GROIN, 31);
        addDamage(Weapon.M4, BodyZone.BUTT, 29);
        addDamage(Weapon.M4, BodyZone.UPPER_LEG, 20);
        addDamage(Weapon.M4, BodyZone.LOWER_LEG, 17);
        addDamage(Weapon.M4, BodyZone.FOOT, 14);

        // AK
        addDamage(Weapon.AK, BodyZone.HEAD, 100);
        addDamage(Weapon.AK, BodyZone.HELMET, 58);
        addDamage(Weapon.AK, BodyZone.TORSO, 51);
        addDamage(Weapon.AK, BodyZone.VEST, 35);
        addDamage(Weapon.AK, BodyZone.ARM, 19);
        addDamage(Weapon.AK, BodyZone.GROIN, 37);
        addDamage(Weapon.AK, BodyZone.BUTT, 35);
        addDamage(Weapon.AK, BodyZone.UPPER_LEG, 22);
        addDamage(Weapon.AK, BodyZone.LOWER_LEG, 19);
        addDamage(Weapon.AK, BodyZone.FOOT, 15);

        // PSG1
        addDamage(Weapon.PSG1, BodyZone.HEAD, 100);
        addDamage(Weapon.PSG1, BodyZone.HELMET, 100);
        addDamage(Weapon.PSG1, BodyZone.TORSO, 97);
        addDamage(Weapon.PSG1, BodyZone.VEST, 70);
        addDamage(Weapon.PSG1, BodyZone.ARM, 36);
        addDamage(Weapon.PSG1, BodyZone.GROIN, 75);
        addDamage(Weapon.PSG1, BodyZone.BUTT, 70);
        addDamage(Weapon.PSG1, BodyZone.UPPER_LEG, 41);
        addDamage(Weapon.PSG1, BodyZone.LOWER_LEG, 36);
        addDamage(Weapon.PSG1, BodyZone.FOOT, 29);

        // SR8
        addDamage(Weapon.SR8, BodyZone.HEAD, 100);
        addDamage(Weapon.SR8, BodyZone.HELMET, 100);
        addDamage(Weapon.SR8, BodyZone.TORSO, 100);
        addDamage(Weapon.SR8, BodyZone.VEST, 100);
        addDamage(Weapon.SR8, BodyZone.ARM, 50);
        addDamage(Weapon.SR8, BodyZone.GROIN, 100);
        addDamage(Weapon.SR8, BodyZone.BUTT, 100);
        addDamage(Weapon.SR8, BodyZone.UPPER_LEG, 60);
        addDamage(Weapon.SR8, BodyZone.LOWER_LEG, 50);
        addDamage(Weapon.SR8, BodyZone.FOOT, 40);

        // NEGEV
        addDamage(Weapon.NEGEV, BodyZone.HEAD, 50);
        addDamage(Weapon.NEGEV, BodyZone.HELMET, 34);
        addDamage(Weapon.NEGEV, BodyZone.TORSO, 30);
        addDamage(Weapon.NEGEV, BodyZone.VEST, 22);
        addDamage(Weapon.NEGEV, BodyZone.ARM, 11);
        addDamage(Weapon.NEGEV, BodyZone.GROIN, 24);
        addDamage(Weapon.NEGEV, BodyZone.BUTT, 22);
        addDamage(Weapon.NEGEV, BodyZone.UPPER_LEG, 13);
        addDamage(Weapon.NEGEV, BodyZone.LOWER_LEG, 11);
        addDamage(Weapon.NEGEV, BodyZone.FOOT, 9);

        // MP5K
        addDamage(Weapon.MP5K, BodyZone.HEAD, 50);
        addDamage(Weapon.MP5K, BodyZone.HELMET, 34);
        addDamage(Weapon.MP5K, BodyZone.TORSO, 30);
        addDamage(Weapon.MP5K, BodyZone.VEST, 22);
        addDamage(Weapon.MP5K, BodyZone.ARM, 13);
        addDamage(Weapon.MP5K, BodyZone.GROIN, 24);
        addDamage(Weapon.MP5K, BodyZone.BUTT, 22);
        addDamage(Weapon.MP5K, BodyZone.UPPER_LEG, 15);
        addDamage(Weapon.MP5K, BodyZone.LOWER_LEG, 13);
        addDamage(Weapon.MP5K, BodyZone.FOOT, 11);

        // UMP
        addDamage(Weapon.UMP, BodyZone.HEAD, 100);
        addDamage(Weapon.UMP, BodyZone.HELMET, 51);
        addDamage(Weapon.UMP, BodyZone.TORSO, 44);
        addDamage(Weapon.UMP, BodyZone.VEST, 29);
        addDamage(Weapon.UMP, BodyZone.ARM, 17);
        addDamage(Weapon.UMP, BodyZone.GROIN, 31);
        addDamage(Weapon.UMP, BodyZone.BUTT, 29);
        addDamage(Weapon.UMP, BodyZone.UPPER_LEG, 20);
        addDamage(Weapon.UMP, BodyZone.LOWER_LEG, 17);
        addDamage(Weapon.UMP, BodyZone.FOOT, 14);

        // P90
        addDamage(Weapon.P90, BodyZone.HEAD, 50);
        addDamage(Weapon.P90, BodyZone.HELMET, 40);
        addDamage(Weapon.P90, BodyZone.TORSO, 33);
        addDamage(Weapon.P90, BodyZone.VEST, 27);
        addDamage(Weapon.P90, BodyZone.ARM, 16);
        addDamage(Weapon.P90, BodyZone.GROIN, 27);
        addDamage(Weapon.P90, BodyZone.BUTT, 25);
        addDamage(Weapon.P90, BodyZone.UPPER_LEG, 17);
        addDamage(Weapon.P90, BodyZone.LOWER_LEG, 15);
        addDamage(Weapon.P90, BodyZone.FOOT, 12);

        // BERETTA
        addDamage(Weapon.BERETTA, BodyZone.HEAD, 100);
        addDamage(Weapon.BERETTA, BodyZone.HELMET, 20);
        addDamage(Weapon.BERETTA, BodyZone.TORSO, 40);
        addDamage(Weapon.BERETTA, BodyZone.VEST, 22);
        addDamage(Weapon.BERETTA, BodyZone.ARM, 13);
        addDamage(Weapon.BERETTA, BodyZone.GROIN, 24);
        addDamage(Weapon.BERETTA, BodyZone.BUTT, 22);
        addDamage(Weapon.BERETTA, BodyZone.UPPER_LEG, 15);
        addDamage(Weapon.BERETTA, BodyZone.LOWER_LEG, 13);
        addDamage(Weapon.BERETTA, BodyZone.FOOT, 11);

        // DEAGLE
        addDamage(Weapon.DEAGLE, BodyZone.HEAD, 100);
        addDamage(Weapon.DEAGLE, BodyZone.HELMET, 66);
        addDamage(Weapon.DEAGLE, BodyZone.TORSO, 57);
        addDamage(Weapon.DEAGLE, BodyZone.VEST, 38);
        addDamage(Weapon.DEAGLE, BodyZone.ARM, 22);
        addDamage(Weapon.DEAGLE, BodyZone.GROIN, 42);
        addDamage(Weapon.DEAGLE, BodyZone.BUTT, 38);
        addDamage(Weapon.DEAGLE, BodyZone.UPPER_LEG, 25);
        addDamage(Weapon.DEAGLE, BodyZone.LOWER_LEG, 22);
        addDamage(Weapon.DEAGLE, BodyZone.FOOT, 18);

        // KNIFE
        addDamage(Weapon.KNIFE, BodyZone.HEAD, 100);
        addDamage(Weapon.KNIFE, BodyZone.HELMET, 60);
        addDamage(Weapon.KNIFE, BodyZone.TORSO, 44);
        addDamage(Weapon.KNIFE, BodyZone.VEST, 35);
        addDamage(Weapon.KNIFE, BodyZone.ARM, 20);
        addDamage(Weapon.KNIFE, BodyZone.GROIN, 40);
        addDamage(Weapon.KNIFE, BodyZone.BUTT, 35);
        addDamage(Weapon.KNIFE, BodyZone.UPPER_LEG, 25);
        addDamage(Weapon.KNIFE, BodyZone.LOWER_LEG, 20);
        addDamage(Weapon.KNIFE, BodyZone.FOOT, 15);


	}

    private void addDamage(Weapon weapon, BodyZone body, int damage) {
		damages.put(weapon.name() + ":" + body.name(), damage);
	}

	public static DamageManager getInstance() {
		return instance;
	}

    public int getDamage(Weapon weapon, BodyZone zone) {
        Integer damage = damages.get(weapon.name() + ":" + zone.name());
		if (damage == null) {
			damage = 0;
		}
		return damage;
	}

}
