package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
import com.conferenceengineer.iosched.server.datamodel.Track;
import com.conferenceengineer.iosched.server.exporters.TrackSessionsJSON;
import com.conferenceengineer.iosched.server.exporters.TracksJSON;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
            String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
            Integer conferenceId = Integer.parseInt(conferenceIdString);

            Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);
            json = TrackSessionsJSON.export(conference);
        } finally {
            em.close();
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"session_tracks.json\"");
        response.getWriter().print(json);
    }
}
