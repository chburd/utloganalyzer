package tb.tartifouette.utlog.keys;

public class WeaponPerKiller {

	private String killer;
	private String weapon;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((killer == null) ? 0 : killer.hashCode());
		result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WeaponPerKiller other = (WeaponPerKiller) obj;
		if (killer == null) {
			if (other.killer != null) {
				return false;
			}
		} else if (!killer.equals(other.killer)) {
			return false;
		}
		if (weapon == null) {
			if (other.weapon != null) {
				return false;
			}
		} else if (!weapon.equals(other.weapon)) {
			return false;
		}
		return true;
	}

	public String getKiller() {
		return killer;
	}

	public void setKiller(String killer) {
		this.killer = killer;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

}
