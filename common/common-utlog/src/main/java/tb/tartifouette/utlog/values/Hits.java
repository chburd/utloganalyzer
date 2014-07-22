package tb.tartifouette.utlog.values;

public class Hits implements Comparable<Hits> {

	private int count;
	private int hp;

	public void add(Hits value) {
		count += value.count;
		hp += value.hp;
	}

	@Override
	public int compareTo(Hits h) {
		return count - h.getCount();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

}
