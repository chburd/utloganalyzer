package tb.tartifouette.weblive.tail;

import java.util.List;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.log4j.Logger;

import tb.tartifouette.utlog.AliasManager;
import tb.tartifouette.utlog.LineParser;
import tb.tartifouette.utlog.event.Event;
import tb.tartifouette.utlog.event.HeadShotEvent;
import tb.tartifouette.weblive.Statistics;
import tb.tartifouette.weblive.gameserver.Server;
import tb.tartifouette.weblive.gameserver.ServerQuery;

public class UtLogTailListener implements TailerListener {

    private static final Logger log = Logger.getLogger(UtLogTailListener.class);

    private LineParser lineParser;

    @Override
    public void init(Tailer tailer) {
        AliasManager mgr = new AliasManager();
        lineParser = new LineParser(Statistics.getGlobalStats(), mgr);

    }

    @Override
    public void fileNotFound() {
        log.error("File not found");

    }

    @Override
    public void fileRotated() {
        log.warn("File rotated event not handled");

    }

    @Override
    public void handle(String line) {
        try {
            List<Event> events = lineParser.parseLine(line);
            events.add(new HeadShotEvent("test de thomas"));
            if (!events.isEmpty()) {
                sendEvents(events);
            }
        } catch (Exception e) {
            e.printStackTrace();//FIXME remove me
            log.error("Error during line handling [" + line + "]", e);
        }

    }

    private void sendEvents(List<Event> events) throws Exception {
        Server server = new Server("local server", "localhost", "27960", "rcon", "");
        ServerQuery query = new ServerQuery(server);
        for (Event event : events) {
            query.say(event.getTextToDisplay());
        }

    }

    @Override
    public void handle(Exception ex) {
        log.error("Error", ex);

    }
}
