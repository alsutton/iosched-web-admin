<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
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

<ul class="nav nav-tabs" id="myTab">
    <li class="active"><a href="#schedule" data-toggle="tab">Schedule</a></li>
    <li><a href="#tracks" data-toggle="tab">Tracks</a></li>
    <li><a href="#speakers" data-toggle="tab">Speakers</a></li>
    <li><a href="#locations" data-toggle="tab">Locations</a></li>
</ul>

<div class="tab-content" id="myTabContent">
    <div class="tab-pane active" id="schedule">
        <div class="row">
            <div class="page-header">
                <h1>Sessions</h1>
            </div>
        </div>

        <div class="modal fade" id="addDayModal" tabindex="-1" role="dialog" aria-labelledby="addDayModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Add Day</h4>
                    </div>
                    <form action="conferenceDays" role="form" method="POST">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="newDay">Please enter date (dd-mm-yyyy);</label>
                                <input type="text" name="date" id="newDay" class="form-control" placeholder="dd-mm-yyyy" />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="row"><div class="col-md-12"><a data-toggle="modal" href="#addDayModal" class="btn btn-primary btn-sm">Add Day</a></div></div>

        <c:forEach var="conferenceDay" items="${conference.dateList}">
            <div class="row">
                <div class="col-md-12"><h2><fmt:formatDate value="${conferenceDay.date}" pattern="dd MMMM yyyy"/></h2></div>
            </div>

            <fmt:formatDate var="dateCode" value="${conferenceDay.date}" pattern="yyyyMMdd"/>

            <div class="modal fade" id="addSessionModal${dateCode}" tabindex="-1" role="dialog" aria-labelledby="addSessionModal${dateCode}" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Add Session Slot</h4>
                        </div>
                        <form action="talkSlots" role="form" method="POST">
                        <input type="hidden" name="day" value="${conferenceDay.id}" />
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="newStart_${dateCode}">Please enter the start time (hh:mm);</label>
                                    <input type="text" name="start" id="newStart_${dateCode}" class="form-control" placeholder="hh:mm" />
                                </div>
                                <div class="form-group">
                                    <label for="newEnd_${dateCode}">Please enter the end time (hh:mm);</label>
                                    <input type="text" name="end" id="newEnd_${dateCode}" class="form-control" placeholder="hh:mm"/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row"><div class="col-md-12"><a data-toggle="modal" href="#addSessionModal${dateCode}" class="btn btn-default btn-sm">Add Session Slot</a></div></div>

            <div style="padding-top:5px">&nbsp;</div>

            <c:forEach var="slot" items="${conferenceDay.talkSlotList}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <fmt:formatDate value="${slot.start.time}" pattern="hh:mm"/>&nbsp;-&nbsp;<fmt:formatDate value="${slot.end.time}" pattern="hh:mm"/>
                        </h3>
                    </div>
                    <div class="panel-body">

                        <div class="modal fade" id="addTalkModal${slot.id}" tabindex="-1" role="dialog" aria-labelledby="addTalkModal${slot.id}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title">Add A Session</h4>
                                    </div>
                                    <form action="talk" role="form" method="POST">
                                        <input type="hidden" name="day" value="${conferenceDay.id}" />
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label for="newStart_${dateCode}">Please enter the start time (hh:mm);</label>
                                                <input type="text" name="start" id="newStart_${dateCode}" class="form-control" placeholder="hh:mm" />
                                            </div>
                                            <div class="form-group">
                                                <label for="newEnd_${dateCode}">Please enter the end time (hh:mm);</label>
                                                <input type="text" name="end" id="newEnd_${dateCode}" class="form-control" placeholder="hh:mm"/>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">Save</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="row"><div class="col-md-12"><a data-toggle="modal" href="#addTalkModal${slot.id}" class="btn btn-default btn-sm">Add a session</a></div></div>

                    </div>
                </div>
            </c:forEach>
        </c:forEach>
    </div>

    <div class="tab-pane" id="tracks">
        <div class="row">
            <div class="page-header">
                <h1>Tracks</h1>
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
                <a data-toggle="modal" href="#addTrackModal" class="btn btn-primary btn-sm">Add Track</a>
                <a href="tracks" target="_blank" class="btn btn-default btn-sm">Download JSON</a>
            </div>
        </div>

        <div style="padding-top:5px">&nbsp;</div>

        <div class="row">
            <div class="col-md-12">
                <c:forEach var="track" items="${conference.trackList}">
                    <p>${track.name}</p>
                </c:forEach>
            </div>
        </div>
    </div>

    <div class="tab-pane" id="speakers">
    </div>

    <div class="tab-pane" id="locations">
            <div class="row">
                <div class="page-header">
                    <h1>Locations</h1>
                </div>
            </div>

            <div class="modal fade" id="addLocation" tabindex="-1" role="dialog" aria-labelledby="addLocation" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Add Location</h4>
                        </div>
                        <form action="talkLocations" role="form" method="POST">
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
                    <a data-toggle="modal" href="#addLocation" class="btn btn-primary btn-sm">Add Location</a>
                    <a href="talkLocations" target="_blank" class="btn btn-default btn-sm">Download JSON</a>
                </div>
            </div>

        <div class="row">
            <div class="col-md-12">
                <c:forEach var="location" items="${conference.talkLocationList}">
                    <p>${location.name}</p>
                </c:forEach>
            </div>
        </div>

    </div>
</div>

<script src="http://dcuk2013.funkyandroid.net/js/modal.js"></script>
<script src="http://dcuk2013.funkyandroid.net/js/tab.js"></script>

<script>
    $('#myTab a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    })
</script>
</body>
</html>