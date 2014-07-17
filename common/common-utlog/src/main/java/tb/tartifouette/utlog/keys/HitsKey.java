package tb.tartifouette.utlog.keys;

public class HitsKey {

	private final String shouter;
	private final String shouted;
	private final String weapon;
	private final String region;

	public HitsKey(String shouter, String shouted, String weapon, String region) {
		super();
		this.shouter = shouter;
		this.shouted = shouted;
		this.weapon = weapon;
		this.region = region;
	}

	public String getShouter() {
		return shouter;
	}

	public String getShouted() {
		return shouted;
	}

	public String getWeapon() {
		return weapon;
	}

	public String getRegion() {
		return region;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((shouted == null) ? 0 : shouted.hashCode());
		result = prime * result + ((shouter == null) ? 0 : shouter.hashCode());
		result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HitsKey other = (HitsKey) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (shouted == null) {
			if (other.shouted != null)
				return false;
		} else if (!shouted.equals(other.shouted))
			return false;
		if (shouter == null) {
			if (other.shouter != null)
				return false;
		} else if (!shouter.equals(other.shouter))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}

}
