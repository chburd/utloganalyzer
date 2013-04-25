package tb.tartifouette.utlog.keys;

public class TeamMapKey {

	private String map;
	private String team;

	public TeamMapKey(String team, String map) {
		this.team = team;
		this.map = map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
		TeamMapKey other = (TeamMapKey) obj;
		if (map == null) {
			if (other.map != null) {
				return false;
			}
		} else if (!map.equals(other.map)) {
			return false;
		}
		if (team == null) {
			if (other.team != null) {
				return false;
			}
		} else if (!team.equals(other.team)) {
			return false;
		}
		return true;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}
