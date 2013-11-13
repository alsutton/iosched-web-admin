package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceMetadata;
import com.conferenceengineer.iosched.server.exporters.SessionsJSON;
import com.conferenceengineer.iosched.server.exporters.SpeakersJSON;
import com.conferenceengineer.iosched.server.exporters.TrackSessionsJSON;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Servlet for handling timezone changes
 */
public class TimezoneServlet extends HttpServlet{

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            Conference conference = ConferenceUtils.getCurrentConference(request, em);

            em.getTransaction().begin();
            conference.setTimezone(request.getParameter("timezone"));
            em.getTransaction().commit();

            request.getSession().setAttribute("message", "The time zone has been updated.");
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard");
    }
}
