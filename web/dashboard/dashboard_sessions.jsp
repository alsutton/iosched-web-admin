<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<script type="text/javascript">
    <!--
    function showHide(id) {
        var content = document.getElementById(id+"_content");
        var hiddenHint = document.getElementById(id+"_hiddenhint");
        if(content.style.display == 'block') {
            content.style.display = 'none';
            hiddenHint.style.display = 'block';
        } else {
            content.style.display = 'block';
            hiddenHint.style.display = 'none';
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
            <a class="navbar-brand" href="/">${conference.name}</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value='/dashboard/Dashboard' />">Admin</a></li>
                <li><a href="<c:url value='/dashboard/DashboardTracks' />">Tracks</a></li>
                <li><a href="<c:url value='/dashboard/DashboardLocations' />">Locations</a></li>
                <li><a href="<c:url value='/dashboard/DashboardSpeakers' />">Speakers</a></li>
                <li class="active"><a href="#">Schedule</a></li>
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

    <div class="modal fade" id="addDayModal" tabindex="-1" role="dialog" aria-labelledby="addDayModal" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Add Day</h4>
                </div>
                <form accept-charset="utf-8" action="conferenceDays" role="form" method="POST">
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

    <div class="row">
        <div class="col-md-12">
            <a data-toggle="modal" href="#addDayModal" class="btn btn-primary btn-sm">Add Day</a>
        </div>
    </div>

    <c:forEach var="conferenceDay" items="${conference.dateList}">
        <fmt:formatDate var="conferenceDate" value="${conferenceDay.date}" pattern="dd MMMM yyyy"/>
        <div class="row">
            <div class="col-md-12"><h2><c:out value="${conferenceDate}" /></h2></div>
        </div>

        <fmt:formatDate var="dateCode" value="${conferenceDay.date}" pattern="yyyyMMdd"/>

        <div class="modal fade" id="addSessionModal${dateCode}" tabindex="-1" role="dialog" aria-labelledby="addSessionModal${dateCode}" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Add Session Slot</h4>
                    </div>
                    <form accept-charset="utf-8" action="talkSlots" role="form" method="POST">
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
                <div class="panel-heading" onclick="showHide(${slot.id});">
                    <h3 class="panel-title">
                        <fmt:formatDate var="startTime" value="${slot.start.time}" pattern="HH:mm"/>
                        <fmt:formatDate var="endTime" value="${slot.end.time}" pattern="HH:mm"/>
                        <c:out value="${startTime}" />&nbsp;-&nbsp;<c:out value="${endTime}" />
                    </h3>
                </div>
                <div class="panel-body">
                    <div id="${slot.id}_hiddenhint" onclick="showHide(${slot.id});">
                        &hellip;
                    </div>
                    <div id="${slot.id}_content" style="display:none">
                        <div class="modal fade" id="newTalkModal${slot.id}" tabindex="-1" role="dialog" aria-labelledby="newTalkModal${slot.id}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title">Add A Session</h4>
                                    </div>
                                    <form accept-charset="utf-8" action="talks" role="form" method="POST">
                                        <input type="hidden" name="slot" value="${slot.id}" />
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label for="newTalkSlot_${slot.id}">Time Slot</label>
                                                <input type="text" id="newTalkSlot_${slot.id}" class="form-control" readonly="readonly" value="${conferenceDate}, ${startTime}-${endTime}" />
                                            </div>
                                            <div class="form-group">
                                                <label for="newTalkTitle_${slot.id}">Title</label>
                                                <input type="text" name="title" id="newTalkTitle_${slot.id}" class="form-control" placeholder="A talk about something" />
                                            </div>
                                            <div class="form-group">
                                                <label for="newTalkPresenter_${slot.id}">Speaker</label>
                                                <select name="presenter" id="newTalkPresenter_${slot.id}" class="form-control">
                                                    <c:forEach var="presenter" items="${conference.presenterList}">
                                                        <option value="${presenter.id}">${presenter.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="newTalkTrack_${slot.id}">Track</label>
                                                <select name="track" id="newTalkTrack_${slot.id}" class="form-control">
                                                    <c:forEach var="track" items="${conference.trackList}">
                                                        <option value="${track.id}">${track.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="newTalkLocation_${slot.id}">Location</label>
                                                <select name="location" id="newTalkLocation_${slot.id}" class="form-control">
                                                <c:forEach var="location" items="${conference.talkLocationList}">
                                                    <option value="${location.id}">${location.name}</option>
                                                </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="newTalkInfoURL_${slot.id}">More Info URL</label>
                                                <input type="text" name="infoURL" id="newTalkInfoURL_${slot.id}" class="form-control" placeholder="http://somewhere/page" />
                                            </div>
                                            <div class="form-group">
                                                <label for="newTalkDescription_${slot.id}">Description</label>
                                                <textarea name="description" id="newTalkDescription_${slot.id}" class="form-control" placeholder="This talk will cover something really interesting." ></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">Save</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="row"><div class="col-md-12"><a data-toggle="modal" href="#newTalkModal${slot.id}" class="btn btn-default btn-sm">Add a session</a></div></div>

                        <div style="padding-top:5px">&nbsp;</div>

                        <c:forEach var="talk" items="${slot.talkList}">
                            <div class="modal fade" id="editTalkModal${talk.id}" tabindex="-1" role="dialog" aria-labelledby="editTalkModal${talk.id}" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title">Edit Session</h4>
                                        </div>
                                        <form accept-charset="utf-8" action="talks" role="form" method="POST">
                                            <input type="hidden" name="talkId" value="${talk.id}" />
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label for="editTalkSlot_${talk.id}">Time Slot</label>
                                                    <input type="text" id="editTalkSlot_${talk.id}" class="form-control" readonly="readonly" value="${conferenceDate}, ${startTime}-${endTime}" />
                                                </div>
                                                <div class="form-group">
                                                    <label for="editTalkTitle_${talk.id}">Title</label>
                                                    <input type="text" name="title" id="editTalkTitle_${talk.id}" class="form-control" value="${talk.name}" />
                                                </div>
                                                <div class="form-group">
                                                    <label for="editTalkTrack_${talk.id}">Track</label>
                                                    <select name="track" id="editTalkTrack_${talk.id}" class="form-control">
                                                        <c:forEach var="track" items="${conference.trackList}">
                                                            <c:choose>
                                                                <c:when test="${talk.track.id == track.id}">
                                                                    <option value="${track.id}" selected="selected">${track.name}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${track.id}">${track.name}</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="editTalkLocation_${talk.id}">Location</label>
                                                    <select name="location" id="editTalkLocation_${talk.id}" class="form-control">
                                                        <c:forEach var="location" items="${conference.talkLocationList}">
                                                            <c:choose>
                                                                <c:when test="${talk.location.id == location.id}">
                                                                    <option value="${location.id}" selected="selected">${location.name}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${location.id}">${location.name}</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="editTalkInfoURL_${talk.id}">More Info URL</label>
                                                    <input type="text" name="infoURL" id="editTalkInfoURL_${talk.id}" class="form-control" value="${talk.informationURL}" />
                                                </div>
                                                <div class="form-group">
                                                    <label for="editTalkDescription_${talk.id}">Description</label>
                                                    <textarea name="description" id="editTalkDescription_${slot.id}" class="form-control" >${talk.shortDescription}</textarea>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" class="btn btn-primary">Save</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="addPresenterModal${talk.id}" tabindex="-1" role="dialog" aria-labelledby="addPresenterModal${talk.id}" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title">Add A Presenter</h4>
                                        </div>
                                        <form accept-charset="utf-8" action="talks" role="form" method="POST">
                                            <input type="hidden" name="talkId" value="${talk.id}" />
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <label for="addPresenterModal${slot.id}">Speaker</label>
                                                    <select name="presenter" id="addPresenterModal${slot.id}" class="form-control">
                                                        <c:forEach var="presenter" items="${conference.presenterList}">
                                                            <option value="${presenter.id}">${presenter.name}</option>
                                                        </c:forEach>
                                                    </select>
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
                                        <h4><a data-toggle="modal" href="#editTalkModal${talk.id}">[${talk.track.name}] ${talk.name}</a></h4>

                                        <p>Location: ${talk.location.name}</p>
                                        <p>Presenters:
                                        <c:forEach var="presenter" items="${talk.presenters}" varStatus="status">
                                            <div class="modal fade" id="removePresenterModal${talk.id}${presenter.id}" tabindex="-1" role="dialog" aria-labelledby="removePresenterModal${talk.id}${presenter.id}" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                            <h4 class="modal-title">Remove A Presenter</h4>
                                                        </div>
                                                        <form accept-charset="utf-8" action="talks" role="form" method="POST">
                                                            <input type="hidden" name="talkId" value="${talk.id}">
                                                            <input type="hidden" name="presenter" value="${presenter.id}">
                                                            <input type="hidden" name="delete" value="true">
                                                            <div class="modal-body">
                                                                <p>Remove ${presenter.name} from ${talk.name}.</p>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn btn-primary">Confirm</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>

                                            <a data-toggle="modal" href="#removePresenterModal${talk.id}${presenter.id}">${presenter.name}</a><c:if test="${not status.last}">,</c:if>&nbsp;
                                        </form>
                                        </c:forEach>
                                            <a data-toggle="modal" href="#addPresenterModal${talk.id}" class="btn btn-default btn-xs">Add presenter</a>
                                        </p>
                                        <p>${talk.shortDescription}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:forEach>
    <div class="row">&nbsp;</div>
</div>

<script src="http://dcuk2013.funkyandroid.net/js/modal.js"></script>

</body>
</html>