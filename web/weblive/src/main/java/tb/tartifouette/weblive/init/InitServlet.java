package tb.tartifouette.weblive.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

import tb.tartifouette.utlog.event.Event;
import tb.tartifouette.utlog.event.HeadShotEvent;
import tb.tartifouette.weblive.gameserver.Server;
import tb.tartifouette.weblive.gameserver.ServerQuery;
import tb.tartifouette.weblive.tail.UtLogTailListener;

public class InitServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        TailerListener listener = new UtLogTailListener();
        Tailer tailer =
                new Tailer(new File(
                        "C:/Users/tburdairon/Documents/Perso/UrbanTerror/q3ut4/games.log"),
                        listener);
        Thread thread = new Thread(tailer);

        thread.start();

        List<Event> events = new ArrayList<Event>();
        events.add(new HeadShotEvent("test de thomas"));
        Server server = new Server("local server", "localhost", "27960", "rcon", "password");
        ServerQuery query;
        try {
            query = new ServerQuery(server);
            for (Event event : events) {
                query.say(event.getTextToDisplay());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
