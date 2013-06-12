<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Urban Terror file upload</title>
</head>
<body>
	<%
		FileItem file = (FileItem) request.getAttribute("fileItem");
	%>
	file.getContentType()
	<%=file.getContentType()%><br /> file.getFieldName()
	<%=file.getFieldName()%><br /> file.getName()
	<%=file.getName()%><br /> file.getSize()
	<%=file.getSize()%><br />
</body>
</html>