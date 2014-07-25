package tb.tartifouette.utlog.event;

public class HeadShotEvent implements Event {

    private final String user;

    public HeadShotEvent(String user){
        this.user = user;
    }

    @Override
    public String getTextToDisplay() {
        return "Headshot from "+user;
    }

}
