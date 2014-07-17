package tb.tartifouette.utlog;

/**
 *
 * hit log is:
 *
 * Hit: A B C D
 *
 * Where:
 * A is the player hitted (clientnumber)
 * B the player who hits (clientnumber) //i dont know why they turn this viceversa
 * C is the position where he hits and is one of:
 * 0 head
 *  1 helmet
 * 2 torso
 * 3 kevlar
 * 4 arms
 * 5 legs
 *
 * and D the weapon.
 *
 */
public class HitResolver {

    public enum Weapon {
        //TODO find the list of weapons with their ID
    }

    //would be great to have a matrix of weapon + bodypart returning the damage points

    public enum BodyPart {
        HEAD("0"), HELMET("1"), TORSO("2"), KEVLAR("3"), ARMS("4"), LEGS("5");
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
            return result;
        }
    }

    private static final HitResolver instance = new HitResolver();

    private HitResolver() {
        //singleton
    }

    public static HitResolver getInstance() {
        return instance;
    }

    public BodyPart resolveBodyPart(String bodyPartId) {
        return BodyPart.findFromId(bodyPartId);
    }

    public Weapon resolveWeapon(String weaponId) {
        // TODO Auto-generated method stub
        return null;
    }

}
