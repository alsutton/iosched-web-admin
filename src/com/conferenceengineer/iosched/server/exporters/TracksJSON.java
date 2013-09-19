package com.conferenceengineer.iosched.server.exporters;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.Talk;
import com.conferenceengineer.iosched.server.datamodel.Track;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exporter for talk slots in the JSON format iosched wants
 */
public final class TracksJSON {

    // Hack to keep colours for IOsched tracks
    private static final Map<Integer,String> COLOUR_MAP = new HashMap<Integer,String>();
    static {
        COLOUR_MAP.put(14, "#fe4a9e");
        COLOUR_MAP.put(18, "#d35400");
        COLOUR_MAP.put(19, "#45699e");
        COLOUR_MAP.put(20, "#8fb655");
        COLOUR_MAP.put(21, "#cecece");
        COLOUR_MAP.put(22, "#000000");
    }


    private TracksJSON() {
        super();
    }

    /**
     * Create the export and place it in a string.
     */

    public static String export(final Conference conference) {
        JSONObject root = new JSONObject();
        JSONArray tracks = new JSONArray();

        for(Track track : conference.getTrackList()) {
            JSONObject trackJSON = new JSONObject();
            trackJSON.put("id", Integer.toString(track.getId()));
            trackJSON.put("title", track.getName());
            trackJSON.put("description", track.getDescription());
            String colour = COLOUR_MAP.get(track.getId());
            if(colour == null) {
                colour = "#336699";
            }
            trackJSON.put("colour", colour);
            trackJSON.put("level", "1");
            trackJSON.put("meta", 1);   // Sessions only

            JSONArray sessions = new JSONArray();
            for(Talk talk : track.getTalkList()) {
                sessions.put(Integer.toString(talk.getId()));
            }
            trackJSON.put("sessions", sessions);

            tracks.put(trackJSON);
        }

        root.put("tracks", tracks);
        return root.toString();
    }
}
