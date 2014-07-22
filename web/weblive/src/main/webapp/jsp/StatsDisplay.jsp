<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="tb.tartifouette.weblive.Statistics"%>
<%@page import="java.util.Map"%>
<%@page import="tb.tartifouette.utlog.values.*"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
.UTlogCSS {
	margin: 0px;
	padding: 0px;
	width: 100%;
	box-shadow: 10px 10px 5px #888888;
	border: 1px solid #000000;
	-moz-border-radius-bottomleft: 0px;
	-webkit-border-bottom-left-radius: 0px;
	border-bottom-left-radius: 0px;
	-moz-border-radius-bottomright: 0px;
	-webkit-border-bottom-right-radius: 0px;
	border-bottom-right-radius: 0px;
	-moz-border-radius-topright: 0px;
	-webkit-border-top-right-radius: 0px;
	border-top-right-radius: 0px;
	-moz-border-radius-topleft: 0px;
	-webkit-border-top-left-radius: 0px;
	border-top-left-radius: 0px;
}

.UTlogCSS table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
}

.UTlogCSS tr:last-child td:last-child {
	-moz-border-radius-bottomright: 0px;
	-webkit-border-bottom-right-radius: 0px;
	border-bottom-right-radius: 0px;
}

.UTlogCSS table tr:first-child td:first-child {
	-moz-border-radius-topleft: 0px;
	-webkit-border-top-left-radius: 0px;
	border-top-left-radius: 0px;
}

.UTlogCSS table tr:first-child td:last-child {
	-moz-border-radius-topright: 0px;
	-webkit-border-top-right-radius: 0px;
	border-top-right-radius: 0px;
}

.UTlogCSS tr:last-child td:first-child {
	-moz-border-radius-bottomleft: 0px;
	-webkit-border-bottom-left-radius: 0px;
	border-bottom-left-radius: 0px;
}

.UTlogCSS tr:hover td {
	
}

.UTlogCSS tr:nth-child(odd) {
	background-color: #e5e5e5;
}

.UTlogCSS tr:nth-child(even) {
	background-color: #ffffff;
}

.UTlogCSS td {
	vertical-align: middle;
	border: 1px solid #000000;
	border-width: 0px 1px 1px 0px;
	text-align: center;
	padding: 8px;
	font-size: 10px;
	font-family: Arial;
	font-weight: normal;
	color: #000000;
}

.UTlogCSS tr:last-child td {
	border-width: 0px 1px 0px 0px;
}

.UTlogCSS tr td:last-child {
	border-width: 0px 0px 1px 0px;
}

.UTlogCSS tr:last-child td:last-child {
	border-width: 0px 0px 0px 0px;
}

.UTlogCSS tr:first-child td {
	background: -o-linear-gradient(bottom, #cccccc 5%, #b2b2b2 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #cccccc
		), color-stop(1, #b2b2b2));
	background: -moz-linear-gradient(center top, #cccccc 5%, #b2b2b2 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#cccccc",
		endColorstr="#b2b2b2");
	background: -o-linear-gradient(top, #cccccc, b2b2b2);
	background-color: #cccccc;
	border: 0px solid #000000;
	text-align: center;
	border-width: 0px 0px 1px 1px;
	font-size: 14px;
	font-family: Arial;
	font-weight: bold;
	color: #000000;
}

.UTlogCSS tr:first-child:hover td {
	background: -o-linear-gradient(bottom, #cccccc 5%, #b2b2b2 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #cccccc
		), color-stop(1, #b2b2b2));
	background: -moz-linear-gradient(center top, #cccccc 5%, #b2b2b2 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#cccccc",
		endColorstr="#b2b2b2");
	background: -o-linear-gradient(top, #cccccc, b2b2b2);
	background-color: #cccccc;
}

.UTlogCSS tr:first-child td:first-child {
	border-width: 0px 0px 1px 0px;
}

.UTlogCSS tr:first-child td:last-child {
	border-width: 0px 0px 1px 1px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="refresh" content="5" />
<title>Urban Terror live stats</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/Stats" method="get">
		<input type="submit" value="Refresh" />
	</form>
	<div class="UTlogCSS">
		<table>
			<thead>
				<th>User</th>
				<th>Score</th>
				<th>Frags</th>
				<th>Deaths</th>
				<th>Flags captured</th>
				<th>Hits given</th>
				<th>Hits received</th>
				<th>Damage given</th>
				<th>Damage received</th>
			</thead>
			<%
				for (Map.Entry<String, UserScore> entry : Statistics.getStats()
						.entrySet()) {
					UserScore score = entry.getValue();
			%>
			<tr>
				<td><%=StringEscapeUtils.escapeHtml(entry.getKey())%></td>
				<td><%=score.getTotalScore()%></td>
				<td><%=score.getTotalFrags()%></td>
				<td><%=score.getTotalDeaths()%></td>
				<td><%=score.getFlagsCaptured()%></td>
				<td><%=score.getHitsGiven()%></td>
				<td><%=score.getHitsReceived()%></td>
				<td><%=score.getDamageGiven()%></td>
				<td><%=score.getDamageReceived()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<br />
	<br />
	<br />
	<br />
	<br /> Copyright 2013-2014 - chburd
	<br /> project info :
	<a href="https://github.com/chburd/utloganalyzer">https://github.com/chburd/utloganalyzer</a>
</body>
</html>