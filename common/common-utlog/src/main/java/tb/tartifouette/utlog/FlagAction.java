package tb.tartifouette.utlog;

public enum FlagAction {
	// according to
	// http://www.urbanterror.info/forums/topic/13546-server-source-where-are-the-logs/
	// 2 : capture
	// 1 : return to base
	// 0 : flag dropped

	FLAG_CAPTURED("2"), FLAG_RETURNED("1"), FLAG_DROPPED("0");
	private final String value;

	FlagAction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static FlagAction fromValue(String value) {
		for (FlagAction action : values()) {
			if (action.getValue().equals(value)) {
				return action;
			}
		}
		throw new IllegalArgumentException("unknown flag action " + value);
	}

}
