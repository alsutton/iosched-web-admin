package com.conferenceengineer.iosched.server.exporters;

import com.conferenceengineer.iosched.server.datamodel.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Exporter for speakers in the JSON format iosched wants
 */
public final class SessionsJSON {

    private SessionsJSON() {
        super();
    }

    /**
     * Create the export and place it in a string.
     */

    public static String export(final Conference conference) {
        JSONObject root = new JSONObject();
        JSONArray sessions = new JSONArray();

        for(ConferenceDay day : conference.getDateList()) {
            for(TalkSlot slot : day.getTalkSlotList()) {
                for(Talk talk : slot.getTalkList()) {
                    JSONObject json = new JSONObject();
                    json.put("id", Integer.toString(talk.getId()));
                    json.put("title", talk.getName());
                    json.put("description", talk.getShortDescription());
                    json.put("startTimestamp", slot.getStart().getTimeInMillis()/1000);
                    json.put("endTimestamp", slot.getEnd().getTimeInMillis()/1000);

                    JSONArray presenterIds = new JSONArray();
                    for(Presenter presenter : talk.getPresenters()) {
                        presenterIds.put(Integer.toString(presenter.getId()));
                    }
                    json.put("presenterIds", presenterIds);

                    Integer type = talk.getType();
                    String typeString;
                    if(type != null && type.intValue() == Talk.TYPE_KEYNOTE) {
                        typeString = "KEYNOTE";
                    } else {
                        typeString = "SESSION";
                    }
                    json.put("subtype", typeString);
                    json.put("location", Integer.toString(talk.getLocation().getId()));

                    String link = talk.getInformationURL();
                    if(link != null) {
                        json.put("webLink", link);
                    }
                    sessions.put(json);
                }
            }
        }

        root.put("sessions", sessions);
        return root.toString();
    }
}
