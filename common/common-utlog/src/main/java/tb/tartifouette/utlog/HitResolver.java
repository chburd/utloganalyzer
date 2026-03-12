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
        KNIFE("1"),
        BERETTA("2"),
        DEAGLE("3"),
        SPAS("4"),
        MP5K("5"),
        UMP("6"),
        HK69("7"),
        LR300("8"),
        G36("9"),
        PSG1("10"),
        HE_GREN("11"),
        FLASH_GREN("12"),
        SMOKE_GREN("13"),
        SR8("14"),
        AK("15"),
        BOMB("16"),
        NEGEV("17"),
        P90("18"),
        M4("19"),
        GOOMBA("20"),
        UNKNOWN21("21"),
        UNKNOWN22("22"),
        UNKNOWN("-1");

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
        HEAD("1"),
        HELMET("2"),
        TORSO("3"),
        VEST("4"),
        LEFT_ARM("5"),
        RIGHT_ARM("6"),
        GROIN("7"),
        BUTT("8"),
        LEFT_UPPER_LEG("9"),
        RIGHT_UPPER_LEG("10"),
        LEFT_LOWER_LEG("11"),
        RIGHT_LOWER_LEG("12"),
        LEFT_FOOT("13"),
        RIGHT_FOOT("14"),
        UNKNOWN("-1");

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

    public BodyZone toZone(BodyPart bodyPart) {
        switch (bodyPart) {
            case HEAD:
                return BodyZone.HEAD;
            case HELMET:
                return BodyZone.HELMET;
            case TORSO:
                return BodyZone.TORSO;
            case VEST:
                return BodyZone.VEST;
            case LEFT_ARM:
            case RIGHT_ARM:
                return BodyZone.ARM;
            case GROIN:
                return BodyZone.GROIN;
            case BUTT:
                return BodyZone.BUTT;
            case LEFT_UPPER_LEG:
            case RIGHT_UPPER_LEG:
                return BodyZone.UPPER_LEG;
            case LEFT_LOWER_LEG:
            case RIGHT_LOWER_LEG:
                return BodyZone.LOWER_LEG;
            case LEFT_FOOT:
            case RIGHT_FOOT:
                return BodyZone.FOOT;
            default:
                return BodyZone.UNKNOWN;
        }
    }

    public enum BodyZone {
        HEAD,
        HELMET,
        TORSO,
        VEST,
        ARM,
        GROIN,
        BUTT,
        UPPER_LEG,
        LOWER_LEG,
        FOOT,
        UNKNOWN
    }

    private static final HitResolver instance = new HitResolver();

    private HitResolver() {
        // singleton
    }

    public static HitResolver getInstance() {
        return instance;
    }


    public BodyZone resolveBodyZone(String bodyPartId) {
        BodyPart part = BodyPart.findFromId(bodyPartId);
        return toZone(part);
    }

    public Weapon resolveWeapon(String weaponId) {
        return Weapon.findFromId(weaponId);
    }

}