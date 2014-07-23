<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="tb.tartifouette.weblive.Statistics"%>
<%@page import="java.util.Map"%>
<%@page import="tb.tartifouette.utlog.values.*"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="refresh" content="20" />
<title>Urban Terror live stats</title>
</head>
<body>
	<div class="UTlogCSS">
		<table>
			<tr>
				<th>User</th>
				<th>Score</th>
				<th>Frags</th>
				<th>Deaths</th>
				<th>Flags captured</th>
				<th>Hits given</th>
				<th>Hits received</th>
				<th>Damage given</th>
				<th>Damage received</th>
			</tr>
			<%
				for (Map.Entry<String, UserScore> entry : Statistics.getStats()
						.entrySet()) {
					UserScore score = entry.getValue();
					String selectedUser = request.getParameter("user");
					String user = StringEscapeUtils.escapeHtml(entry.getKey());
					boolean selected = entry.getKey().equals(selectedUser);
					String style = "";
					if(selected){
						style = " id=\"selectedRow\"";
					}
			%>
			
				<tr <%=style%>>
				<td <%=style%>>
					<a href="?user=<%=user%>"><%=user%></a></td>
				<td <%=style%>><%=score.getTotalScore()%></td>
				<td <%=style%>><%=score.getTotalFrags()%></td>
				<td <%=style%>><%=score.getTotalDeaths()%></td>
				<td <%=style%>><%=score.getFlagsCaptured()%></td>
				<td <%=style%>><%=score.getHitsGiven()%></td>
				<td <%=style%>><%=score.getHitsReceived()%></td>
				<td <%=style%>><%=score.getDamageGiven()%></td>
				<td <%=style%>><%=score.getDamageReceived()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<br />
		<form action="<%=request.getContextPath()%>/Stats" method="get">
		<input type="submit" value="Refresh" />
	</form>
	<form action="<%=request.getContextPath()%>/Download" method="post">
		<input type="submit" value="Download" />
	</form>
	<br />
	<br /> Copyright 2013-2014 - chburd
	<br /> project info :
	<a href="https://github.com/chburd/utloganalyzer">https://github.com/chburd/utloganalyzer</a>
</body>
</html>