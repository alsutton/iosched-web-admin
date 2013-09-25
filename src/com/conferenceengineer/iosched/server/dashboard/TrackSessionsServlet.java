package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.exporters.TrackSessionsJSON;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to handle tracks
 */
public class TrackSessionsServlet extends HttpServlet {

    /**
     * Get the JSON in a format suitable for IOSched
     */

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        String json;

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            json = TrackSessionsJSON.export(ConferenceUtils.getCurrentConference(request, em));
        } finally {
            em.close();
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"session_tracks.json\"");
        response.getOutputStream().write(json.getBytes("UTF-8"));
    }
}
