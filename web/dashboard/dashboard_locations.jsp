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
                <li><a href="<c:url value='/dashboard/Dashboard' />">Admin</a></li>
                <li><a href="<c:url value='/dashboard/DashboardTracks' />">Tracks</a></li>
                <li class="active"><a href="#">Locations</a></li>
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

    <div class="modal fade" id="addLocation" tabindex="-1" role="dialog" aria-labelledby="addLocation" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Add Location</h4>
                </div>
                <form accept-charset="utf-8" action="talkLocations" role="form" method="POST">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="newLocationName">Please enter the name of the location;</label>
                            <input type="text" name="name" id="newLocationName" class="form-control" placeholder="Room 1" />
                        </div>
                        <div class="form-group">
                            <label for="newLocationAddress">Please any helpful location hint;</label>
                            <input type="text" name="address" id="newLocationAddress" class="form-control" placeholder="Level 2" />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <c:forEach var="location" items="${conference.talkLocationList}">
                <p>${location.name}</p>
            </c:forEach>
        </div>
    </div>

    <div class="row">&nbsp;</div>

    <div class="row">
        <div class="col-md-12">
            <a data-toggle="modal" href="#addLocation" class="btn btn-primary btn-sm">Add Location</a>
        </div>
    </div>

    <div class="row">&nbsp;</div>
</div>

<script src="<c:url value='/js/modal.js' />" />

</body>
</html>