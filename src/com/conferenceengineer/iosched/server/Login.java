package com.conferenceengineer.iosched.server;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.LoginUtils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Login Servlet for handling conference organiser logins.
 */
public class Login extends HttpServlet {

    /**
     * Handle a users login
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */

    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws ServletException, IOException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();

            String name = request.getParameter("username");
            if(name == null || name.isEmpty()) {
                reportLoginError(request, response);
                return;
            }

            SystemUser user = SystemUserDAO.getInstance().getByEmail(em, name);
            if(user == null) {
                reportLoginError(request, response);
                return;
            }

            List<UserAuthenticationInformation> authenticators =
                    UserAuthenticationInformationDAO.getInstance().getAuthenticators(em, user);
            if(authenticators == null || authenticators.isEmpty()) {
                reportLoginError(request, response);
                return;
            }

            String password = request.getParameter("password");
            if(password == null || password.isEmpty()) {
                reportLoginError(request, response);
                return;
            }

            LoginUtils loginUtils = LoginUtils.getInstance();
            for(UserAuthenticationInformation authenticator : authenticators) {
                if(authenticator.getAuthenticatorType() != UserAuthenticationInformationDAO.AUTHENTICATOR_INTERNAL) {
                    continue;
                }

                if(loginUtils.isUserValid(name, password, authenticator)) {
                    loginUtils.addCookie(response, user);

                    List<ConferencePermission> permissions = user.getPermissions();
                    if(permissions != null && !permissions.isEmpty()) {
                        request.getSession().setAttribute("conferenceId", permissions.get(0).getConference().getId());
                    } else {
                        // Bootstrap the permissions
                        ConferencePermission permission = new ConferencePermission();
                        permission.setSystemUser(user);
                        permission.setConference(em.find(Conference.class, 1));
                        em.persist(permission);
                        request.getSession().setAttribute("conferenceId", 1);
                    }

                    response.sendRedirect("dashboard/Dashboard");
                    em.getTransaction().commit();
                    return;
                }
            }

            reportLoginError(request, response);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Add an error to indicate the login was incorrect.
     */

    private void reportLoginError(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        request.setAttribute("error", "The login details were incorrect");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
