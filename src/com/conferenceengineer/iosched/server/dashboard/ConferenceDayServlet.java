package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.ConferenceDay;
import com.conferenceengineer.iosched.server.datamodel.TalkSlot;
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
     * Handle adds and deletes
     */

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        String action = request.getParameter("action");
        String append = "";
        if(action == null || action.isEmpty()) {
            append = add(request, response);
        } else if("delete".equals(action)) {
            delete(request, response);
        }

        response.sendRedirect("DashboardSessions"+append);
    }

    private String add(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();
            String date = request.getParameter("date");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            ConferenceDay day = new ConferenceDay(ConferenceUtils.getCurrentConference(request, em), sdf.parse(date));
            em.persist(day);
            em.getTransaction().commit();

            return "#day_"+day.getId();
        } catch (ParseException e) {
            throw new ServletException(e);
        } finally {
            em.close();
        }
    }

    /**
     * Delete a day
     */

    private void delete(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException{
        String id = request.getParameter("id");
        if(id == null || id.isEmpty()) {
            request.getSession().setAttribute("error", "Day ID not specified");
            return;
        }

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();
            ConferenceDay day = em.find(ConferenceDay.class, Integer.parseInt(id));
            em.remove(day);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
