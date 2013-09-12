package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDay;
import com.conferenceengineer.iosched.server.datamodel.TalkSlot;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Servlet to handle tracks
 */
public class TalkSlotServlet extends HttpServlet {

    /**
     * Post is add!!!
     */

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();
            String conferenceDayIdString = request.getParameter("day");
            Integer conferenceDayId = Integer.parseInt(conferenceDayIdString);

            ConferenceDay conferenceDay = em.find(ConferenceDay.class, conferenceDayId);

            Calendar start = Calendar.getInstance();
            start.setTime(conferenceDay.getDate());
            setCalendarWithTime(start, request.getParameter("start"));

            Calendar end = Calendar.getInstance();
            end.setTime(conferenceDay.getDate());
            setCalendarWithTime(end, request.getParameter("end"));

            em.persist(new TalkSlot(conferenceDay, start, end));
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard");
    }

    /**
     * Set a calendar with the time from a string.
     *
     * @param calendar The calendar to set
     * @param string The string holding the time in hh:mm format.
     */

    private void setCalendarWithTime(final Calendar calendar, final String string) {
        int idx = string.indexOf(':');
        if(idx == -1) {
            throw new RuntimeException("Invalid time format "+string);
        }

        int hours = Integer.parseInt(string.substring(0, idx));
        int mins = Integer.parseInt(string.substring(idx+1));
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, mins);
    }
}
