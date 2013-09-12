package com.conferenceengineer.iosched.server;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet which bootstraps the database with the neccessary users.
 */
public class BootstrapServlet extends HttpServlet {

    /**
     * Create the initial required users.
     *
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();
            SystemUserDAO suDAO = SystemUserDAO.getInstance();

            SystemUser al = suDAO.getByEmail(em, "al@funkyandroid.com");
            if(al != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            suDAO.store(em, "al@funkyandroid.com");
            al = suDAO.getByEmail(em, "al@funkyandroid.com");
            al.setFlags(SystemUserDAO.FLAG_IS_ADMIN);

            UserAuthenticationInformationDAO.getInstance().createInternalAuthenticationEntry(em, al, "admin");

            em.persist(new Conference(request.getServletContext().getInitParameter("conferenceName")));

            response.sendRedirect(request.getContextPath());

            em.getTransaction().commit();
        } catch(Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Problem with bootstrapping system", ex);
        } finally {
            em.close();
        }
    }
}
