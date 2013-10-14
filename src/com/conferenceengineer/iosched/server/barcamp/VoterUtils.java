package com.conferenceengineer.iosched.server.barcamp;

import com.conferenceengineer.iosched.server.datamodel.SystemUser;
import com.conferenceengineer.iosched.server.datamodel.Voter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Utilities for dealing with Voters
 */
public final class VoterUtils {
    /**
     * The name of the Voter ID cookie
     */

    private static final String VOTER_ID_COOKIE = "vid";

    private VoterUtils() {
        super();
    }

    /**
     * Get the voter ID
     */

    static Voter getVoter(final HttpServletRequest request, final HttpServletResponse response,
                           final EntityManager em, final SystemUser user) {
        try {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie : request.getCookies()) {
                    if(VOTER_ID_COOKIE.equals(cookie.getName())) {
                        Integer id = Integer.parseInt(cookie.getValue());
                        Voter voter = em.find(Voter.class, id);
                        if(voter != null) {
                            if(voter.getUser() == null && user != null) {
                                voter.setUser(user);
                            }
                            return voter;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Do nothing, this just indicated a cookie issue.
        }

        if(user != null) {
            Query q = em.createQuery("SELECT x FROM Voter x WHERE x.user = :user");
            q.setParameter("user", user);
            q.setMaxResults(1);
            List<Voter> voters = q.getResultList();
            if(!voters.isEmpty()) {
                return voters.get(0);
            }
        }

        em.getTransaction().begin();
        Voter voter = new Voter();
        if(user != null) {
            voter.setUser(user);
        }
        em.persist(voter);
        em.getTransaction().commit();

        Cookie cookie = new Cookie(VOTER_ID_COOKIE, Integer.toString(voter.getId()));
        response.addCookie(cookie);
        return voter;
    }

}