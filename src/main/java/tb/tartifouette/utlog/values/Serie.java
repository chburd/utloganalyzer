package tb.tartifouette.utlog.values;

public class Serie {

    private int startTime;
    private int count;
    private int lastKillTime;
    private int lastFragTime;

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void decreaseCount() {
        count--;
    }

    public void increaseCount() {
        count++;
    }

    public int getLastKillTime() {
        return lastKillTime;
    }

    public void setLastKillTime(int lastKillTime) {
        this.lastKillTime = lastKillTime;
    }

    public int getLastFragTime() {
        return lastFragTime;
    }

    public void setLastFragTime(int lastFragTime) {
        this.lastFragTime = lastFragTime;
    }

    public boolean isInFragSerie(){
        return count>0;
    }

}
