package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferencePermission;
import com.conferenceengineer.iosched.server.datamodel.SystemUser;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.LoginUtils;
import com.conferenceengineer.iosched.server.utils.ServletUtils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Display the conference selection list to the user.
 */
public class ConferenceServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        String id = request.getParameter("id");

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            SystemUser currentUser = LoginUtils.getInstance().getUserFromCookie(request, em);

            boolean selectionHandled = false;
            if(id != null) {
                selectionHandled = handleSelection(request, currentUser, id);
            }

            if(selectionHandled) {
                response.sendRedirect("Dashboard");
                return;
            }

            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/dashboard/conference_chooser.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        final String name = request.getParameter("name");
        if(name == null || name.isEmpty()) {
            ServletUtils.redirectToIndex(request, response);
            return;
        }

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            SystemUser user = LoginUtils.getInstance().getUserFromCookie(request, em);
            if(user == null) {
                ServletUtils.redirectToIndex(request, response);
                return;
            }

            em.getTransaction().begin();
            Conference conference = new Conference();
            conference.setName(name);
            em.persist(conference);

            ConferencePermission permission = new ConferencePermission();
            permission.setConference(conference);
            permission.setSystemUser(user);
            permission.setPermission(ConferencePermission.PERMISSION_ADMINISTER_COLLABORATORS);
            em.persist(permission);

            em.getTransaction().commit();

            handleSelection(request, user, conference.getId());
            response.sendRedirect("Dashboard");
        } finally {
            em.close();
        }
    }

    /**
     * Attempt to handle a users selection
     */

    private boolean handleSelection(final HttpServletRequest request, final SystemUser user, final String id) {
        return handleSelection(request, user, Integer.parseInt(id));
    }

    /**
     * Attempt to handle a users selection
     */

    private boolean handleSelection(final HttpServletRequest request, final SystemUser user, final int conferenceId) {
        for(ConferencePermission permission : user.getPermissions()) {
            if(permission.getConference().getId() == conferenceId) {
                request.getSession().setAttribute("conferenceId", conferenceId);
                return true;
            }
        }
        return false;
    }
}
