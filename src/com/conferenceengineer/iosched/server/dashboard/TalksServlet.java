package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.exporters.SessionsJSON;
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
public class TalksServlet extends HttpServlet {

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
            json = SessionsJSON.export(conference);
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


    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();

            Talk talk;
            String slotId = request.getParameter("slotId");
            if(slotId == null) {
                talk = new Talk();
            } else {
                talk = em.find(Talk.class, Integer.parseInt(slotId));
            }

            TalkLocation location = em.find(TalkLocation.class, Integer.parseInt(request.getParameter("location")));
            Track track = em.find(Track.class, Integer.parseInt(request.getParameter("track")));
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            talk.setLocation(location);
            talk.setTrack(track);
            talk.setName(title);
            talk.setShortDescription(description);

            // Slot ID present means it's an add
            if( slotId == null) {
                Presenter presenter = em.find( Presenter.class, Integer.parseInt(request.getParameter("presenter")));
                em.persist(talk);
                presenter.getTalks().add(talk);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard#tracks");
    }
}
