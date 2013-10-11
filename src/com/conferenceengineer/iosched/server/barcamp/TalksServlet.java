package com.conferenceengineer.iosched.server.barcamp;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.exporters.SessionsJSON;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.LoginUtils;
import com.conferenceengineer.iosched.server.utils.ServletUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Servlet to handle barcamp talks
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
            json = SessionsJSON.export(ConferenceUtils.getCurrentConference(request, em));
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

            Conference conference = ConferenceUtils.getCurrentConference(request, em);
            if(conference == null) {
                ServletUtils.redirectToIndex(request, response);
                return;
            }

            SystemUser user = LoginUtils.getInstance().getUserFromCookie(request, em);
            if(user != null) {
                String talkId = request.getParameter("talkId");
                if(talkId == null) {
                    add(em, conference, request);
                } else {
                    edit(em, request, talkId);
                }
                em.getTransaction().commit();

                request.getSession().setAttribute("message", "Your submission has been accepted.");
            }
        } finally {
            em.close();
        }

        ServletUtils.redirectTo(request, response, "/barcamp/view/");
    }

    /**
     * Handle an add
     */

    private void add(final EntityManager em, final Conference conference, final HttpServletRequest request) {
        Talk talk = new Talk();
        populateObject(talk, request);
        talk.setConference(conference);
        em.persist(talk);
        addPresenter(em, conference, talk, request);
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
        populateObject(talk, request);
    }


    private void editPresenter(final EntityManager em, final HttpServletRequest request, final Talk talk,
                               final String presenterId) {
        if(request.getParameter("delete") != null) {
            deletePresenter(em, talk, presenterId);
        }
    }

    /**
     * Populate the object with the information from the request.
     */

    private void populateObject(final Talk talk, final HttpServletRequest request) {
        talk.setName(request.getParameter("title"));
        talk.setShortDescription(request.getParameter("description"));
        talk.setType(Talk.TYPE_PROPOSED);
    }

    /**
     * Add a presenter to a talk
     */

    private void addPresenter(final EntityManager em, final Conference conference,
                              final Talk talk, final HttpServletRequest request) {
        SystemUser user = LoginUtils.getInstance().getUserFromCookie(request, em);

        Query q = em.createQuery("SELECT x FROM Presenter x WHERE x.conference = :conference AND x.user = :user");
        q.setParameter("conference", conference);
        q.setParameter("user", user);
        q.setMaxResults(1);
        List<Presenter> presenterList = (List<Presenter>)q.getResultList();
        Presenter presenter;
        if(presenterList.isEmpty()) {
            presenter = new Presenter();
            presenter.setConference(conference);
            presenter.setName(user.toString());
            Set<Talk> talks = new HashSet<Talk>();
            talks.add(talk);
            presenter.setTalks(talks);
            em.persist(presenter);
        } else {
            presenter = presenterList.get(0);
            presenter.getTalks().add(talk);
        }

    }

    private void deletePresenter(final EntityManager em, final Talk talk, final String presenterId) {
        Presenter presenter = em.find( Presenter.class, Integer.parseInt(presenterId));
        presenter.getTalks().remove(talk);
    }
}
