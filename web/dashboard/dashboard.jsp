<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
                <a href="talks" target="_blank" class="btn btn-default btn-sm">Download Sessions JSON</a>
                <a href="trackSessions" target="_blank" class="btn btn-default btn-sm">Download Track/Sessions JSON</a>
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
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <fmt:formatDate var="startTime" value="${slot.start.time}" pattern="HH:mm"/>
                            <fmt:formatDate var="endTime" value="${slot.end.time}" pattern="HH:mm"/>
                            <c:out value="${startTime}" />&nbsp;-&nbsp;<c:out value="${endTime}" />
                        </h3>
                    </div>
                    <div class="panel-body">

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
                    <form accept-charset="utf-8" action="tracks" role="form" method="POST">
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
        <div class="row">
            <div class="page-header">
                <h1>Speakers</h1>
            </div>
        </div>

        <div class="modal fade" id="addSpeaker" tabindex="-1" role="dialog" aria-labelledby="addSpeaker" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Add Speaker</h4>
                    </div>
                    <form accept-charset="utf-8" action="speakers" role="form" method="POST">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="newSpeakerName">Speaker name;</label>
                                <input type="text" name="name" id="newSpeakerName" class="form-control" placeholder="Al Sutton" />
                            </div>
                            <div class="form-group">
                                <label for="newSpeakerImage">URL for the speakers picture;</label>
                                <input type="text" name="imageURL" id="newSpeakerImage" class="form-control" placeholder="http://www.xyz.com/pic_of_al.png" />
                            </div>
                            <div class="form-group">
                                <label for="newSpeakerSocialURL">URL for the speakers social network;</label>
                                <input type="text" name="socialURL" id="newSpeakerSocialURL" class="form-control" placeholder="http://www.twitter.com/alsutton" />
                            </div>
                            <div class="form-group">
                                <label for="newSpeakerShortBio">Short Biography;</label>
                                <textarea name="shortBio" id="newSpeakerShortBio" class="form-control" placeholder="Al has been talking for ages." rows="2"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="newSpeakerLongBio">Long Biography;</label>
                                <textarea name="longBio" id="newSpeakerLongBio" class="form-control" placeholder="Al has been talking for ages and is still talking :O" rows="5"></textarea>
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
                <a data-toggle="modal" href="#addSpeaker" class="btn btn-primary btn-sm">Add Speaker</a>
                <a href="speakers" target="_blank" class="btn btn-default btn-sm">Download JSON</a>
            </div>
        </div>

        <div style="padding-top:5px">&nbsp;</div>

        <div class="row">
            <div class="col-md-12">
                <c:forEach var="presenter" items="${conference.presenterList}">
                    <div class="modal fade" id="editSpeaker_${presenter.id}" tabindex="-1" role="dialog" aria-labelledby="editSpeaker_${presenter.id}" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Edit Speaker</h4>
                                </div>
                                <form accept-charset="utf-8" action="speakers" role="form" method="POST">
                                <input type="hidden" name="speakerId" value="${presenter.id}" />
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label for="editSpeaker_${presenter.id}_Name">Speaker name;</label>
                                            <input type="text" name="name" id="editSpeaker_${presenter.id}_Name" class="form-control" value="${presenter.name}" />
                                        </div>
                                        <div class="form-group">
                                            <label for="editSpeaker_${presenter.id}_Image">URL for the speakers picture;</label>
                                            <input type="text" name="imageURL" id="editSpeaker_${presenter.id}_Image" class="form-control" value="${presenter.imageURL}" />
                                        </div>
                                        <div class="form-group">
                                            <label for="editSpeaker_${presenter.id}_SocialURL">URL for the speakers social network;</label>
                                            <input type="text" name="socialURL" id="editSpeaker_${presenter.id}_SocialURL" class="form-control" value="${presenter.socialLink}" />
                                        </div>
                                        <div class="form-group">
                                            <label for="editSpeaker_${presenter.id}_ShortBio">Short Biography;</label>
                                            <textarea name="shortBio" id="editSpeaker_${presenter.id}_ShortBio" class="form-control" rows="2">${presenter.shortBiography}</textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="editSpeaker_${presenter.id}_LongBio">Long Biography;</label>
                                            <textarea name="longBio" id="editSpeaker_${presenter.id}_LongBio" class="form-control" rows="5">${presenter.longBiography}</textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <h4><a data-toggle="modal" href="#editSpeaker_${presenter.id}">${presenter.name}</a></h4>
                    <p>${presenter.shortBiography}</p>
                </c:forEach>
            </div>
        </div>
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
                <a data-toggle="modal" href="#addLocation" class="btn btn-primary btn-sm">Add Location</a>
                <a href="talkLocations" target="_blank" class="btn btn-default btn-sm">Download JSON</a>
            </div>
        </div>

        <div style="padding-top:5px">&nbsp;</div>

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