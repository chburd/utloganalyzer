package tb.tartifouette.utlog;

/**
 * 
 * hit log is:
 * 
 * Hit: A B C D
 * 
 * Where: A is the player hitted (clientnumber) B the player who hits
 * (clientnumber) //i dont know why they turn this viceversa C is the position
 * where he hits and is one of: 0 head 1 helmet 2 torso 3 kevlar 4 arms 5 legs
 * 
 * and D the weapon.
 * 
 */
public class HitResolver {

	public enum Weapon {
		KNIFE("1"), BERETTA("2"), DEAGLE("3"), SPAS("4"), MP5K("5"), UMP("6"), HK69(
				"7"), LR300("8"), G36("9"), PSG1("10"), HE_GREN("11"), SMOKE_GREN(
				"13"), SR8("14"), AK("15"), BOMB("16"), NEGEV("17"), M4("19"), UNKNOWN21(
				"21"), UNKNOWN22("22"), UNKNOWN("-1");
		private final String id;

		Weapon(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public static Weapon findFromId(String id) {
			Weapon result = null;
			for (Weapon weapon : values()) {
				if (weapon.getId().equals(id)) {
					result = weapon;
				}
			}
			if (result == null) {
				result = Weapon.UNKNOWN;
			}
			return result;
		}

	}

	// would be great to have a matrix of weapon + bodypart returning the damage
	// points

	public enum BodyPart {
		HEAD("0"), HELMET("1"), TORSO("2"), KEVLAR("3"), ARMS("4"), LEGS("5"), UNKNOWN(
				"-1");
		private final String id;

		BodyPart(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public static BodyPart findFromId(String id) {
			BodyPart result = null;
			for (BodyPart bodyPart : values()) {
				if (bodyPart.getId().equals(id)) {
					result = bodyPart;
				}
			}
			if (result == null) {
				result = BodyPart.UNKNOWN;
			}
			return result;
		}
	}

	private static final HitResolver instance = new HitResolver();

	private HitResolver() {
		// singleton
	}

	public static HitResolver getInstance() {
		return instance;
	}

	public BodyPart resolveBodyPart(String bodyPartId) {
		return BodyPart.findFromId(bodyPartId);
	}

	public Weapon resolveWeapon(String weaponId) {
		return Weapon.findFromId(weaponId);
	}

}
