package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Presenter;
import com.conferenceengineer.iosched.server.datamodel.Talk;
import com.conferenceengineer.iosched.server.datamodel.TalkSlot;
import com.conferenceengineer.iosched.server.exporters.SpeakersJSON;
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
public class BarcampServlet extends HttpServlet {

    /**
     * Post is add!!!
     */

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();

            Talk talk = em.find(Talk.class, Integer.parseInt(request.getParameter("talkId")));
            TalkSlot slot = em.find(TalkSlot.class, Integer.parseInt(request.getParameter("slot")));
            talk.setSlot(slot);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

        response.sendRedirect("DashboardBarcamp");
    }
}
