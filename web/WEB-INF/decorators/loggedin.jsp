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
                <a class="navbar-brand" href="/">${initParam.conferenceName}</a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="http://funkyandroid.com/eps/index.html">About the EPS</a></li>
                    <li><a href="http://funkyandroid.com/eps/demo.html">Demo</a></li>
                    <li><a href="http://funkyandroid.com/eps/download.html">Download</a></li>
                    <li class="active"><a href="https://funkyandroid.com/eps/store/">Buy</a></li>
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
        <p class="text-center"><a href="http://www.linkedin.com/company/funky-android-limited" rel="author" style="text-decoration:none;" target="_blank">
            <img src="https://528be3620adf65cf9552-6ff5bf27bb9e4d87d2fb856504d6886d.ssl.cf3.rackcdn.com/linkedin.png" alt="LinkedIn" />
        </a>
            &nbsp;
            <a href="https://twitter.com/funkyandroid" rel="author" style="text-decoration:none;" target="_blank">
                <img src="https://528be3620adf65cf9552-6ff5bf27bb9e4d87d2fb856504d6886d.ssl.cf3.rackcdn.com/twitter.png" alt="Twitter" />
            </a>
            &nbsp;
            <a href="https://www.facebook.com/FunkyAndroid" rel="author" style="text-decoration:none;" target="_blank">
                <img src="https://528be3620adf65cf9552-6ff5bf27bb9e4d87d2fb856504d6886d.ssl.cf3.rackcdn.com/facebook.png" alt="Facebook" />
            </a></p>
    </div>
</div>
<script src="https://a58cb323b3f25c8f2f77-eec300142ae6d0545b31737a10fa357c.ssl.cf3.rackcdn.com/bootstrap-3_0_0/js/jquery.js"></script>
<script src="https://a58cb323b3f25c8f2f77-eec300142ae6d0545b31737a10fa357c.ssl.cf3.rackcdn.com/bootstrap-3_0_0/js/bootstrap.min.js"></script>
</body>
</html>
