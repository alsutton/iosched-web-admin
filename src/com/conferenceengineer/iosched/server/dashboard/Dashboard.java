package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.LoginUtils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Display the conference dashboard to the user.
 */
public class Dashboard extends DashboardBase {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        request.setAttribute("serverStatus", "All servers are operational");
        request.setAttribute("serverStatusType", "Good");


        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            request.setAttribute(
                    "user",
                    LoginUtils.getInstance().getUserFromCookie(request, em));
        } finally {
            em.close();
        }

        super.doGet(request, response);
    }

    /**
     * Get the next page to send the user to.
     */

    @Override
    protected String getNextPage() {
        return "dashboard.jsp";
    }
}
