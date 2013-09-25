package com.conferenceengineer.iosched.server;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.PasswordGenerator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

/**
 * Servlet handling registrations
 */
public class RegisterServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
        throws ServletException, IOException {
        String  name = request.getParameter("name"),
                email = request.getParameter("email"),
                conferenceName = request.getParameter("conference");

        if(name == null || name.isEmpty()
        || email == null || email.isEmpty()
        || conferenceName == null || conferenceName.isEmpty()) {
            request.getSession().setAttribute("error", "Please fill in all the registration fields.");
            doGet(request, response);
            return;
        }

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            if(SystemUserDAO.getInstance().getByEmail(em, email) != null) {
                request.getSession().setAttribute("error", "That email address is already registered.");
                doGet(request, response);
                return;
            }

            em.getTransaction().begin();

            createNew(em, name, email, conferenceName);

            em.getTransaction().commit();

            request.getRequestDispatcher("/register_thanks.jsp").forward(request, response);
        } catch (NoSuchAlgorithmException e) {
            request.getSession().setAttribute("error", "There was a problem creating your account, please try again later.");
            log("Problem creating account", e);
            doGet(request, response);
        } catch (MessagingException e) {
            request.getSession().setAttribute("error", "There was a problem creating your account, please try again later.");
            log("Problem creating account", e);
            doGet(request, response);
        } finally {
            EntityTransaction transaction = em.getTransaction();
            if(transaction.isActive()) {
                transaction.rollback();
            }
            em.close();
        }

    }


    private void createNew(final EntityManager em, final String name, final String email, final String conferenceName)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException {
        SystemUser user = new SystemUser();
        user.setEmail(email);
        user.setHumanName(name);

        Conference conference = new Conference();
        conference.setName(conferenceName);

        em.persist(user);
        em.persist(conference);

        ConferencePermission permission = new ConferencePermission();
        permission.setConference(conference);
        permission.setSystemUser(user);
        em.persist(permission);

        String password = PasswordGenerator.generatePassword();
        UserAuthenticationInformationDAO.
                getInstance().
                createInternalAuthenticationEntry(em, user, password);

        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        Session session = Session.getDefaultInstance(props);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("no-reply@funkyandroid.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Conference Engineer Password");
        message.setText("Your password for Conference Engineer is : "+password);
        message.setHeader("X-Mailer", "ConferenceEngineerAutomailer");
        message.setSentDate(new Date());

        Transport.send(message);
    }
}
