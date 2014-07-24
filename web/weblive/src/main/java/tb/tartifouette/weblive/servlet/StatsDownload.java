package tb.tartifouette.weblive.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tb.tartifouette.utlog.AliasManager;
import tb.tartifouette.utlog.Stats;
import tb.tartifouette.utlog.report.ReportGenerator;
import tb.tartifouette.weblive.Statistics;

public class StatsDownload extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Stats stats = Statistics.getGlobalStats();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		ReportGenerator generator = new ReportGenerator(stats, Locale.ENGLISH);
		generator.generateReport(new AliasManager());
		byte[] content = generator.getBaos().toByteArray();

		response.setContentType("application/zip");
		if (date != null) {
			response.setHeader("Content-Disposition",
					"attachment; filename=stats-" + date + ".zip");
		} else {
			response.setHeader("Content-Disposition",
					"attachment; filename=stats.zip");
		}
		ServletOutputStream op = response.getOutputStream();
		op.write(content);
		op.close();
	}
}
