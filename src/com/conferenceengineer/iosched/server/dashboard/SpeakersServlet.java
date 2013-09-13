package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
import com.conferenceengineer.iosched.server.datamodel.Presenter;
import com.conferenceengineer.iosched.server.datamodel.Track;
import com.conferenceengineer.iosched.server.exporters.SpeakersJSON;
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
public class SpeakersServlet extends HttpServlet {

    /**
     * Get the JSON in a format suitable for IOSched
     */

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        String json;

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
            Integer conferenceId = Integer.parseInt(conferenceIdString);

            Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);
            json = SpeakersJSON.export(conference);
        } finally {
            em.close();
        }

        PrintWriter output = null;
        try {
            output = response.getWriter();
            output.print(json);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
            String imageURL = request.getParameter("imageURL");
            String socialURL = request.getParameter("socialURL");
            String shortBio = request.getParameter("shortBio");
            String longBio = request.getParameter("longBio");
            em.persist(new Presenter(conference, name, imageURL, socialURL, shortBio, longBio));
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard#speakers");
    }
}
