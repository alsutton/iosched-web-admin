package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Track;
import com.conferenceengineer.iosched.server.exporters.TracksJSON;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to handle tracks
 */
public class TracksServlet extends HttpServlet {

    /**
     * Get the JSON in a format suitable for IOSched
     */

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        String json;

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            json = TracksJSON.export(ConferenceUtils.getCurrentConference(request, em));
        } finally {
            em.close();
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"tracks.json\"");
        response.getOutputStream().write(json.getBytes("UTF-8"));
    }

    /**
     * Post is add!!!
     */

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();

            Track track;
            String trackId = request.getParameter("id");
            boolean isNew = (trackId == null || trackId.isEmpty());
            if(isNew) {
                track = new Track();
                track.setConference(ConferenceUtils.getCurrentConference(request, em));
            } else {
                track = em.find(Track.class, Integer.parseInt(trackId));
            }

            track.setName(request.getParameter("name"));
            track.setDescription(request.getParameter("description"));

            if(isNew) {
                em.persist(track);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("DashboardTracks");
    }
}
