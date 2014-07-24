<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="tb.tartifouette.weblive.Statistics"%>
<%@page import="java.util.Map"%>
<%@page import="tb.tartifouette.utlog.values.*"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css"/>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="refresh" content="20" />
	<title>Urban Terror live stats</title>
</head>
<body>
	<div class="navbar navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Urban Terror Live Stats</a>
			</div>
			<div class="navbar-right">
			  <ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#about">About</a></li>
			  </ul>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<table class="table table-bordered table-hover">
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
					for (Map.Entry<String, UserScore> entry : Statistics.getStats().entrySet()) {
						UserScore score = entry.getValue();
						String selectedUser = request.getParameter("user");
						String user = StringEscapeUtils.escapeHtml(entry.getKey());
						boolean selected = entry.getKey().equals(selectedUser);
						String style = "";
						if(selected){
							style = " class=\"info\"";
						}
				%>
					<tr <%=style%>>
						<td><a href="?user=<%=user%>"><%=user%></a></td>
						<td><%=score.getTotalScore()%></td>
						<td><%=score.getTotalFrags()%></td>
						<td><%=score.getTotalDeaths()%></td>
						<td><%=score.getFlagsCaptured()%></td>
						<td><%=score.getHitsGiven()%></td>
						<td><%=score.getHitsReceived()%></td>
						<td><%=score.getDamageGiven()%></td>
						<td><%=score.getDamageReceived()%></td>
					</tr>
				<% } %>
			</table>
		</div>

		<div class="row">
			<div class="col-md-1 col-md-offset-8">
				<form action="<%=request.getContextPath()%>/Stats" method="get">
					<input type="submit" value="Refresh" />
				</form>
			</div>
			<div class="col-md-1">
				<form action="<%=request.getContextPath()%>/Download" method="post">
					<input type="submit" value="Download" />
				</form>
			</div>
		</div>

		<footer>
			<div class="row">Copyright 2013-2014 - chburd</div>
			<div class="row"> project info : <a href="https://github.com/chburd/utloganalyzer">https://github.com/chburd/utloganalyzer</a></div>
		</footer>
	</div>
</body>
</html>