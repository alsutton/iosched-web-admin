package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.ConferenceDay;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            String date = request.getParameter("date");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            em.persist(new ConferenceDay(ConferenceUtils.getCurrentConference(request, em), sdf.parse(date)));
            em.getTransaction().commit();
        } catch (ParseException e) {
            throw new ServletException(e);
        } finally {
            em.close();
        }

        response.sendRedirect("DashboardSessions");
    }
}
