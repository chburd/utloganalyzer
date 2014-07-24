package tb.tartifouette.weblive.init;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

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
    }

}
