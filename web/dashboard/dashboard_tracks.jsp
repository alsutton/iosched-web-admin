<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>
<div class="row">&nbsp;</div>
<c:if test="${not empty error}">
    <div class="row"><div class="alert alert-danger text-center">${error}</div></div>
</c:if>

<ul class="nav nav-tabs nav-justified">
    <li><a href="<c:url value='/dashboard/Dashboard' />">Admin</a></li>
    <li class="active"><a href="#">Tracks</a></li>
    <li><a href="<c:url value='/dashboard/DashboardLocations' />">Locations</a></li>
    <li><a href="<c:url value='/dashboard/DashboardSpeakers' />">Speakers</a></li>
    <li><a href="<c:url value='/dashboard/DashboardSessions' />">Schedule</a></li>
</ul>

<div class="row">&nbsp;</div>

<div class="modal fade" id="addTrackModal" tabindex="-1" role="dialog" aria-labelledby="addTrackModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Add Track</h4>
            </div>
            <form accept-charset="utf-8" action="<c:url value='/dashboard/tracks' />" role="form" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="newTrackName">Please enter the new track name;</label>
                        <input type="text" name="name" id="newTrackName" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label for="newTrackDescription">Please enter a description;</label>
                        <input type="text" name="description" id="newTrackDescription" class="form-control" />
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
        <c:forEach var="track" items="${conference.trackList}">
            <p>${track.name}</p>
        </c:forEach>
    </div>
</div>

<div class="row">&nbsp;</div>

<div class="row">
    <div class="col-md-12">
        <a data-toggle="modal" href="#addTrackModal" class="btn btn-primary btn-sm">Add Track</a>
    </div>
</div>

<div class="row">&nbsp;</div>

<script src="http://dcuk2013.funkyandroid.net/js/modal.js"></script>
</body>
</html>