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
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
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


        response.setHeader("Content-Disposition", "attachment; filename=\"presenters.json\"");
        response.getWriter().print(json);
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

            String speakerId = request.getParameter("speakerId");
            Presenter presenter;
            if(speakerId == null) {
                presenter = new Presenter();
                String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
                Integer conferenceId = Integer.parseInt(conferenceIdString);
                Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);
                presenter.setConference(conference);
            } else {
                presenter = em.find(Presenter.class, Integer.parseInt(speakerId));
            }


            presenter.setName(request.getParameter("name"));
            presenter.setImageURL(request.getParameter("imageURL"));
            presenter.setSocialLink(request.getParameter("socialURL"));
            presenter.setShortBiography(request.getParameter("shortBio"));
            presenter.setLongBiography(request.getParameter("longBio"));

            if(speakerId == null) {
                em.persist(presenter);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard#speakers");
    }
}
