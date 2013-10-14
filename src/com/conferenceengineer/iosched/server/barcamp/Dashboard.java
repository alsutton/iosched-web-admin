package com.conferenceengineer.iosched.server.barcamp;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.SystemUser;
import com.conferenceengineer.iosched.server.datamodel.Talk;
import com.conferenceengineer.iosched.server.datamodel.TalkVote;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.LoginUtils;
import com.conferenceengineer.iosched.server.utils.ServletUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The welcoming page for the barcamp system.
 */
public class Dashboard extends HttpServlet {
    /**
     * The marker used to indicate votes
     */

    private static final Object VOTE_MARKER = new Object();


    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        try {

            String URI = request.getRequestURI();
            if(URI.endsWith("/")) {
                URI = URI.substring(0, URI.length()-1);
            }

            log(URI);
            if(!"/barcamp/view".equals(URI)) {
                int endIdx = URI.lastIndexOf('/');
                if(endIdx == -1) {
                    throw new RuntimeException("Unable to find conference ID");
                }

                String storedConferenceId = URI.substring(endIdx+1);
                request.getSession(true).setAttribute("conferenceId", Integer.parseInt(storedConferenceId));
            }
        } catch (Exception e) {
            log("Error, sending user to "+request.getContextPath(), e);
            ServletUtils.redirectToIndex(request, response);
            return;
        }

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            Conference conference = ConferenceUtils.getCurrentConference(request, em);
            if(conference == null) {
                ServletUtils.redirectToIndex(request, response);
                return;
            }

            Query q =
                    em.createQuery("SELECT x FROM Talk x WHERE x.conference = :conference AND x.slot IS NULL AND x.type = "+ Talk.TYPE_PROPOSED);
            q.setParameter("conference", conference);
            List<Talk> talks = (List<Talk>)q.getResultList();

            request.setAttribute("conference", conference);
            request.setAttribute("talks", talks);
            SystemUser user = LoginUtils.getInstance().getUserFromCookie(request, em);
            if(user != null) {
                request.setAttribute("user", user);
                request.setAttribute("votes", getUserVotes(em, user, conference));

            }

            request.getRequestDispatcher("/barcamp/dashboard.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    /**
     * Get the list of votes the user has cast
     */

    private Map<Integer,Object> getUserVotes(final EntityManager em, final SystemUser user, final Conference conference) {
        Query q =
                em.createQuery("SELECT x FROM TalkVote x WHERE x.talk.conference = :conference AND x.systemUser = :user");
        q.setParameter("user", user);
        q.setParameter("conference", conference);

        Map<Integer, Object> votes = new HashMap<Integer,Object>();
        for(TalkVote vote : (List<TalkVote>)q.getResultList()) {
            if(vote.getVote() != 0) {
                votes.put(vote.getTalk().getId(), VOTE_MARKER);
            }
        }

        return votes;
    }
}
