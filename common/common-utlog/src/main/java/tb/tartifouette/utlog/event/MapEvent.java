package tb.tartifouette.utlog.event;

public class MapEvent implements Event {

    private final String map;

    public MapEvent(String map){
        this.map = map;
    }

    @Override
    public String getTextToDisplay() {
        return "Map changed to "+map;
    }

}
