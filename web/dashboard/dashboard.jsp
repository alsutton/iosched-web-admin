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
            <a class="navbar-brand" href="/">${conference.name}</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Admin</a></li>
                <li><a href="<c:url value='/dashboard/DashboardTracks' />">Tracks</a></li>
                <li><a href="<c:url value='/dashboard/DashboardLocations' />">Locations</a></li>
                <li><a href="<c:url value='/dashboard/DashboardSpeakers' />">Speakers</a></li>
                <li><a href="<c:url value='/dashboard/DashboardSessions' />">Schedule</a></li>
            </ul>
        </div>
        <c:if test="${requestScope.user != null}">
            <div class="navbar-right">
                <c:out value="${requestScope.username}" />&nbsp;|&nbsp; <a href="<c:url value='/Logout' />">Logout</a>
            </div>
        </c:if>
    </div>
</div>
<div class="container">
    <div class="row">&nbsp;</div>
    <c:if test="${not empty error}">
        <div class="row"><div class="alert alert-danger text-center">${error}</div></div>
        <div class="row">&nbsp;</div>
    </c:if>

    <div class="row">
        <c:choose>
            <c:when test='${serverStatusType eq "Good"}'><c:set var="statusClass">alert-success</c:set></c:when>
            <c:when test='${serverStatusType eq "Bad"}'><c:set var="statusClass">alert-warning</c:set></c:when>
            <c:when test='${serverStatusType eq "Ugly"}'><c:set var="statusClass">alert-danger</c:set></c:when>
            <c:otherwise><c:set var="statusClass">alert-info</c:set></c:otherwise>
        </c:choose>
        <div class="alert ${statusClass} text-center">${serverStatus}</div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <h4>OTA update information</h4>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            Last published :&nbsp;
            <c:choose>
                <c:when test="${conference.metadata == null || conference.metadata.lastPublished == null}"><i>Never</i></c:when>
                <c:otherwise><fmt:formatDate value="${conference.metadata.lastPublished.time}" pattern="MMM dd, yyyy 'at' HH:mm"/></c:otherwise>
            </c:choose>
            <br /><a href="<c:url value='/dashboard/publish' />" class="btn btn-default btn-xs">Publish Now</a>
        </div>
    </div>

    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-md-12">
            <h4>Downloads for Conference App Development (iosched)</h4>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <ul>
                <li><a href="<c:url value='/dashboard/talkLocations' />" target="_blank">Rooms JSON</a></li>
                <li><a href="<c:url value='/dashboard/searchSuggestions' />" target="_blank">Search Suggestions JSON</a></li>
                <li><a href="<c:url value='/dashboard/talks' />" target="_blank">Download Sessions JSON</a></li>
                <li><a href="<c:url value='/dashboard/speakers' />" target="_blank">Speakers JSON</a></li>
                <li><a href="<c:url value='/dashboard/tracks' />" target="_blank">Tracks JSON</a></li>
                <li><a href="<c:url value='/dashboard/trackSessions' />" target="_blank">Track/Sessions JSON</a></li>
            </ul>
        </div>
    </div>
    <div class="row">&nbsp;</div>
</div>
</body>
</html>