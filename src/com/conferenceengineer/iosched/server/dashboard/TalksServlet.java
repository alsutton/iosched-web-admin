package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.exporters.SessionsJSON;
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
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
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

        response.setHeader("Content-Disposition", "attachment; filename=\"sessions.json\"");
        response.getOutputStream().write(json.getBytes("UTF-8"));
    }


    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();
            String talkId = request.getParameter("talkId");
            if(talkId == null) {
                add(em, request);
            } else {
                edit(em, request, talkId);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard#tracks");
    }

    /**
     * Handle an add
     */

    private void add(final EntityManager em, final HttpServletRequest request) {
        Talk talk = new Talk();
        populateObject(em, talk, request);
        em.persist(talk);
        addPresenter(em, talk, request);
    }

    /**
     * Handle an edit
     */

    private void edit(final EntityManager em, final HttpServletRequest request, final String slotId) {
        Talk talk = em.find(Talk.class, Integer.parseInt(slotId));
        String presenterId = request.getParameter("presenter");
        if(presenterId != null) {
            // We're just altering a presenter
            editPresenter(em, request, talk, presenterId);
            return;
        }

        // We're updating the object.
        populateObject(em, talk, request);
    }


    private void editPresenter(final EntityManager em, final HttpServletRequest request, final Talk talk,
                               final String presenterId) {
        if(request.getParameter("delete") != null) {
            deletePresenter(em, talk, presenterId);
        } else {
            addPresenter(em, talk, presenterId);
        }
    }

    /**
     * Populate the object with the information from the request.
     */

    private void populateObject(final EntityManager em, final Talk talk, final HttpServletRequest request) {
        TalkLocation location = em.find(TalkLocation.class, Integer.parseInt(request.getParameter("location")));
        talk.setLocation(location);

        TalkSlot slot = em.find(TalkSlot.class, Integer.parseInt(request.getParameter("slot")));
        talk.setSlot(slot);

        Track track = em.find(Track.class, Integer.parseInt(request.getParameter("track")));
        talk.setTrack(track);

        talk.setName(request.getParameter("title"));
        talk.setShortDescription(request.getParameter("description"));
    }

    /**
     * Add a presenter to a talk
     */

    private void addPresenter(final EntityManager em, final Talk talk, final HttpServletRequest request) {
        addPresenter(em,talk,request.getParameter("presenter"));
    }

    private void addPresenter(final EntityManager em, final Talk talk, final String presenterId) {
        Presenter presenter = em.find( Presenter.class, Integer.parseInt(presenterId));
        presenter.getTalks().add(talk);
    }

    private void deletePresenter(final EntityManager em, final Talk talk, final String presenterId) {
        Presenter presenter = em.find( Presenter.class, Integer.parseInt(presenterId));
        presenter.getTalks().remove(talk);
    }
}
