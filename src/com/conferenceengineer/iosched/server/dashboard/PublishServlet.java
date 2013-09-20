package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
import com.conferenceengineer.iosched.server.datamodel.ConferenceMetadata;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Display the conference dashboard to the user.
 */
public class PublishServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
            Integer conferenceId = Integer.parseInt(conferenceIdString);

            em.getTransaction().begin();
            Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);
            ConferenceMetadata metadata = conference.getMetadata();
            if(metadata == null) {
                metadata = new ConferenceMetadata();
                metadata.setConference(conference);
                em.persist(metadata);
                conference.setMetadata(metadata);
            }
            metadata.setLastPublished(Calendar.getInstance());
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard");
    }
}
