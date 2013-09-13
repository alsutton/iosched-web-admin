package com.conferenceengineer.iosched.server.exporters;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.TalkLocation;
import com.conferenceengineer.iosched.server.datamodel.Track;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Exporter for talk slots in the JSON format iosched wants
 */
public final class TalkLocationsJSON {

    private TalkLocationsJSON() {
        super();
    }

    /**
     * Create the export and place it in a string.
     */

    public static String export(final Conference conference) {
        JSONObject root = new JSONObject();
        JSONArray locations = new JSONArray();
        List<TalkLocation> talkLocations = conference.getTalkLocationList();
        for(TalkLocation location : talkLocations) {
            JSONObject trackJSON = new JSONObject();
            trackJSON.put("id", Integer.toString(location.getId()));
            trackJSON.put("name", location.getName());
            trackJSON.put("floor", location.getAddress());
            locations.put(trackJSON);
        }
        root.put("rooms", locations);
        return root.toString();
    }
}
