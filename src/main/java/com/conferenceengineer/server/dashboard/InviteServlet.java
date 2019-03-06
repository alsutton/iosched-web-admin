package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

/**
 * Handle inviting a user to collaborate on a conference.
 */
public class InviteServlet extends HttpServlet {

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        final String invitedEmail = request.getParameter("email");
        if(invitedEmail == null || invitedEmail.isEmpty()) {
            request.getSession().setAttribute("error", "You must enter an email address for the user you wish to invite.");
            response.sendRedirect("Dashboard");
            return;
        }

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            em.getTransaction().begin();
            Conference conference = ConferenceUtils.getCurrentConference(request, em);
            SystemUser invitedUser = SystemUserDAO.getInstance().getByEmail(em, invitedEmail);
            if(invitedUser == null) {
                createUser(em, conference, invitedEmail);
            } else {
                addUser(em, conference, invitedUser);
            }
            em.getTransaction().commit();

            request.getSession().setAttribute("message", "An invite has been sent to the user");
        } catch (NoSuchAlgorithmException e) {
            request.getSession().setAttribute("error", "There was a problem inviting that person, please try again later.");
            log("Problem sending invite", e);
        } catch (MessagingException e) {
            request.getSession().setAttribute("error", "There was a problem inviting that person, please try again later.");
            log("Problem sending invite", e);
        } finally {
            EntityTransaction transaction = em.getTransaction();
            if(transaction.isActive()) {
                transaction.rollback();
            }
            em.close();
        }

        response.sendRedirect("Dashboard");
    }

    private void createUser(final EntityManager em, final Conference conference, final String email)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException {
        SystemUser user = new SystemUser();
        user.setEmail(email);
        em.persist(user);

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
        message.setFrom(new InternetAddress("support@funkyandroid.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Conference Engineer Invite");
        message.setText(
                "Your have been invited to work on the schedule for "+conference.getName()+"\n"+
                "You can log in at http://conferenceengineer.com/ using your email address " +
                "("+email+") and the password "+password);
        message.setHeader("X-Mailer", "ConferenceEngineerAutomailer");
        message.setSentDate(new Date());

        Transport.send(message);
    }


    private void addUser(final EntityManager em, final Conference conference, final SystemUser user)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException {
        ConferencePermission permission = new ConferencePermission();
        permission.setConference(conference);
        permission.setSystemUser(user);
        em.persist(permission);

        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        Session session = Session.getDefaultInstance(props);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("support@funkyandroid.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        message.setSubject("Conference Engineer Invite");
        message.setText(
                "You have been invited to work on the schedule for "+conference.getName()+".\n\n"+
                "The next time you log in to Conference Engineer (https://conferenceenginer.com/)\n"+
                "you'll have access to its schedule.");
        message.setHeader("X-Mailer", "ConferenceEngineerAutomailer");
        message.setSentDate(new Date());

        Transport.send(message);
    }
}
