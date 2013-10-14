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
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap.min.css' />" >
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />" >
    <title>Conference Engineer</title>
    <decorator:head />
</head>
<body>
<div id="wrap">
    <div class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Conference Engineer</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="<c:url value='/login.jsp'/>">Login</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="container">
        <decorator:body/>
    </div>
</div>
<div id="footer">
    <div class="container">
        <p class="text-center">Website and back-end software : &copy;Copyright 2013 <a target="_blank" href="http://funkyandroid.com/">Funky Android Ltd.</a>, All rights reserved.</p>
    </div>
</div>
<script src="<c:url value='/js/jquery-1.10.2.min.js' />"></script>
<script src="<c:url value='/js/bootstrap.min.js' />"></script>
</body>
</html>
