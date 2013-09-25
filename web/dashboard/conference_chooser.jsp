<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>
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
            <ul class="nav navbar-nav">
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<c:url value='/Logout'/>">Logout</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">&nbsp;</div>
    <c:if test="${not empty sessionScope.error}">
        <div class="row"><div class="alert alert-danger text-center">${sessionScope.error}</div></div>
        <c:set scope="session" var="error" value="" />
    </c:if>
    <c:if test="${not empty sessionScope.message}">
        <div class="row"><div class="alert alert-success text-center">${sessionScope.message}</div></div>
        <c:set scope="session" var="message" value="" />
    </c:if>

    <div class="row">
        <div class="col-md-12">
            <h4>Select Conference</h4>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
           <p>Please select the conference you wish to work on;</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <c:forEach var="permission" items="${user.permissions}">
                <p><a href="ConferenceChooser?id=${permission.conference.id}">${permission.conference.name}</a></p>
            </c:forEach>
        </div>
    </div>


    <div class="row">&nbsp;</div>
</div>
</body>
</html>