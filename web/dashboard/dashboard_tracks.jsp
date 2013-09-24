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
                <li class="active"><a href="#">Tracks</a></li>
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
                            <textarea name="description" id="newTrackDescription" class="form-control"></textarea>
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
                <div class="modal fade" id="editTrackModal${track.id}" tabindex="-1" role="dialog" aria-labelledby="editTrackModal${track.id}" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title">Edit Track</h4>
                            </div>
                            <form accept-charset="utf-8" action="<c:url value='/dashboard/tracks' />" role="form" method="POST">
                                <input type="hidden" name="id" value="${track.id}" />
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="editTrack${track.id}Name">Please enter the new track name;</label>
                                        <input type="text" name="name" id="editTrack${track.id}Name" class="form-control" value="${track.name}"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="editTrack${track.id}Description">Please enter a description;</label>
                                        <textarea name="description" id="editTrack${track.id}Description" class="form-control">${track.description}</textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <h4><a data-toggle="modal" href="#editTrackModal${track.id}">${track.name}</a></h4>
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
</div>

<script src="<c:url value='/js/modal.js' />" />

</body>
</html>