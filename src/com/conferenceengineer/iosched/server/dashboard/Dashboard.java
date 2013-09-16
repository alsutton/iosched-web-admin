package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
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
public class Dashboard extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("serverStatus", "All servers are operational");
        request.setAttribute("serverStatusType", "Good");

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
            Integer conferenceId = Integer.parseInt(conferenceIdString);

            Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);
            request.setAttribute("conference", conference);

            request.getRequestDispatcher("/dashboard/dashboard.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}
