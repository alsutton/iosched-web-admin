<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<script type="text/javascript">
    <!--
    function showHide(id) {
        var content = document.getElementById(id+"_content");
        if(content.style.display == 'block') {
            content.style.display = 'none';
        } else {
            content.style.display = 'block';
        }
    }
    //-->
</script>
</head>
<body>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value='/dashboard/Dashboard'/>"><c:out value="${conference.name}"/></a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value='/dashboard/Dashboard' />">Admin</a></li>
                <li><a href="<c:url value='/dashboard/DashboardTracks' />">Tracks</a></li>
                <li><a href="<c:url value='/dashboard/DashboardLocations' />">Locations</a></li>
                <li><a href="<c:url value='/dashboard/DashboardSpeakers' />">Speakers</a></li>
                <li><a href="<c:url value='/dashboard/DashboardSessions' />">Schedule</a></li>
                <li class="active"><a href="#">Barcamp</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<c:url value='/dashboard/conference'/>">Build</a></li>
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

    <c:forEach var="talkholder" items="${sortedTalks}" varStatus="talkStatus">
        <div class="panel panel-default">
            <div class="panel-heading">[${talkholder.votes}]&nbsp;
            <c:set var="talk" value="${talkholder.talk}"/>
            <b onclick="showHide(${talk.id});"><c:out value="${talk.name}" /></b> by <c:forEach var="presenter" items="${talk.presenters}" varStatus="status">
                <c:out value="${presenter}"/><c:if test="${not status.last}">,</c:if>&nbsp;
            </c:forEach></div>
            <div  id="${talk.id}_content" onclick="showHide(${talk.id});" class="panel-body" style="display:none">${talk.shortDescription}</div>
        </div>
        <c:if test="${not talkStatus.last}"><div style="height:10px"></div></c:if>
    </c:forEach>
</div>
</body>
</html>