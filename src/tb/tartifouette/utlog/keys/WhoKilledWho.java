package tb.tartifouette.utlog.keys;

public class WhoKilledWho {

    private String killer;
    private String killed;

    @Override
    public String toString() {
        return killer + " killed " + killed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((killed == null) ? 0 : killed.hashCode());
        result = prime * result + ((killer == null) ? 0 : killer.hashCode());
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
        WhoKilledWho other = (WhoKilledWho)obj;
        if (killed == null) {
            if (other.killed != null) {
                return false;
            }
        } else if (!killed.equals(other.killed)) {
            return false;
        }
        if (killer == null) {
            if (other.killer != null) {
                return false;
            }
        } else if (!killer.equals(other.killer)) {
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

    public String getKilled() {
        return killed;
    }

    public void setKilled(String killed) {
        this.killed = killed;
    }

}
