package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDay;
import com.conferenceengineer.iosched.server.datamodel.TrackDAO;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Servlet to handle tracks
 */
public class ConferenceDayServlet extends HttpServlet {

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

            String date = request.getParameter("date");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            em.persist(new ConferenceDay(conference, sdf.parse(date)));
            em.getTransaction().commit();
        } catch (ParseException e) {
            throw new ServletException(e);
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard");
    }
}
