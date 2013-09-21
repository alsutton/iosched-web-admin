<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="description" content="Enter your order for Enterprise Password Safe licenses and agreements into this page.">
    <meta name="keywords" content="password management, password safe, password vault, password storage, enterprise password safe, passwords">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Funky Android Ltd.">
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" type="text/css" href="http://dcuk2013.funkyandroid.net/css/bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="http://dcuk2013.funkyandroid.net/css/style.css" >
    <title>${initParam.conferenceName}</title>
    <script src="http://dcuk2013.funkyandroid.net/js/jquery-1.10.2.min.js"></script>
    <script src="http://dcuk2013.funkyandroid.net/js/bootstrap.min.js"></script>
	<decorator:head />
</head>
<body>
<div id="wrap">
    <decorator:body/>
</div>
<div id="footer">
    <div class="container">
        <p class="text-center">&copy;Copyright 2013 Funky Android Ltd., All rights reserved.</p>
    </div>
</div>
</body>
</html>
