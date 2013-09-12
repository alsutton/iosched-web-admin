<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <script src="http://dcuk2013.funkyandroid.net/js/modal.js"></script>
</head>
<body>
<div class="row">&nbsp;</div>
<c:if test="${not empty error}">
    <div class="row"><div class="alert alert-danger text-center">${error}</div></div>
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
    <div class="page-header">
        <h1>Tracks</h1>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <c:forEach var="track" items="${conference.trackList}">
            <h3>${track.name}</h3>
        </c:forEach>
    </div>
</div>


<div class="modal fade" id="addTrackModal" tabindex="-1" role="dialog" aria-labelledby="addTrackModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Add Track</h4>
            </div>
            <form action="tracks" role="form" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="newTrackName">Please enter the new track name;</label>
                        <input type="text" name="name" id="newTrackName" class="form-control" />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="row"><div class="col-md-12 text-center"><a data-toggle="modal" href="#addTrackModal" class="btn btn-primary btn-lg">Add Track</a></div></div>


<div class="row">
    <div class="page-header">
        <h1>Sessions</h1>
    </div>
</div>

<c:forEach var="date" items="${dates}">
    <div class="row">
        <div class="col-md-12 text-center"><h2><fmt:formatDate value="${date}" pattern="dd MMMM yyyy"/></h2></div>
    </div>

    <fmt:formatDate var="dateCode" value="${date}" pattern="yyyyMMdd"/>

    <div class="modal fade" id="addSessionModal${dateCode}" tabindex="-1" role="dialog" aria-labelledby="addSessionModal${dateCode}" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Add Track</h4>
                </div>
                <form action="tracks" role="form">
                    <div class="modal-body">
                        <p>Please enter the new track name;</p>
                        <input type="text" name="trackName" id="trackName" class="form-control" />
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="row"><div class="col-md-12 text-center"><a data-toggle="modal" href="#addSessionModal${dateCode}" class="btn btn-primary btn-lg">Add Session</a></div></div>
</c:forEach>


</body>
</html>