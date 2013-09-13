package com.conferenceengineer.iosched.server.exporters;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.Track;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Exporter for talk slots in the JSON format iosched wants
 */
public final class TracksJSON {

    private TracksJSON() {
        super();
    }

    /**
     * Create the export and place it in a string.
     */

    public static String export(final Conference conference) {
        JSONObject root = new JSONObject();
        JSONArray tracks = new JSONArray();
        List<Track> trackList = conference.getTrackList();
        for(Track track : trackList) {
            JSONObject trackJSON = new JSONObject();
            trackJSON.put("id", Integer.toString(track.getId()));
            trackJSON.put("name", track.getName());
            trackJSON.put("abstract", track.getDescription());
            trackJSON.put("color", "#336699");
            trackJSON.put("level", "1");
            tracks.put(trackJSON);
        }
        root.put("track", tracks);
        return root.toString();
    }
}
