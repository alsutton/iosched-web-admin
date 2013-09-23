package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
import com.conferenceengineer.iosched.server.datamodel.Track;
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
            String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
            Integer conferenceId = Integer.parseInt(conferenceIdString);

            Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);
            json = TracksJSON.export(conference);
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
            String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
            Integer conferenceId = Integer.parseInt(conferenceIdString);

            Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);

            String name = request.getParameter("name");
            String description = request.getParameter("description");
            em.persist(new Track(conference, name, description));
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("DashboardTracks");
    }
}
