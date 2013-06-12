package tb.tartifouette.web.init;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// InputStream is = null;
		// try {
		// is = config.getServletContext().getResourceAsStream(
		// "/WEB-INF/classes/alias.properties");
		// Properties props = new Properties();
		//
		// props.load(is);
		// AliasManager.getInstance().reinit(props);
		//
		// } catch (IOException e) {
		// throw new ServletException(e);
		// } finally {
		// IOUtils.closeQuietly(is);
		// }

	}

}
