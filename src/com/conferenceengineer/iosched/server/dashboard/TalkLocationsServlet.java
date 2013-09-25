package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.exporters.TalkLocationsJSON;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to handle tracks
 */
public class TalkLocationsServlet extends HttpServlet {

    /**
     * Get the JSON in a format suitable for IOSched
     */

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        String json;

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            json = TalkLocationsJSON.export(ConferenceUtils.getCurrentConference(request, em));
        } finally {
            em.close();
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"rooms.json\"");
        response.getOutputStream().write(json.getBytes("UTF-8"));
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

            TalkLocation location;
            String id = request.getParameter("id");
            boolean isNew = id == null || id.isEmpty();
            if(isNew) {
                location = new TalkLocation();
                location.setConference(ConferenceUtils.getCurrentConference(request, em));
            } else {
                location = em.find(TalkLocation.class, Integer.parseInt(id));
            }

            location.setName(request.getParameter("name"));
            location.setAddress(request.getParameter("address"));

            if(isNew) {
                em.persist(location);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("DashboardLocations");
    }
}
