package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Display the conference dashboard to the user.
 */
public abstract class DashboardBase extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            request.setAttribute("conference", ConferenceUtils.getCurrentConference(request, em));
            request.getRequestDispatcher("/dashboard/"+getNextPage()).forward(request, response);
        } finally {
            em.close();
        }
    }

    /**
     * Get the next page to send the user to.
     */

    protected abstract String getNextPage();
}
