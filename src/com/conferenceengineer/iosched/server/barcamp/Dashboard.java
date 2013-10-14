package com.conferenceengineer.iosched.server.barcamp;

import com.conferenceengineer.iosched.server.datamodel.*;
import com.conferenceengineer.iosched.server.utils.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * The welcoming page for the barcamp system.
 */
public class Dashboard extends HttpServlet {
    /**
     * The marker used to indicate votes
     */

    private static final Object VOTE_MARKER = new Object();

    /**
     * The counter used to ensure each voter gets a unique ID
     */

    private static int counter;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        try {

            String URI = request.getRequestURI();
            if(URI.endsWith("/")) {
                URI = URI.substring(0, URI.length()-1);
            }

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

            Tracker.setLocation(request, response, "barcamp_"+conference.getId());
            Query q =
                    em.createQuery("SELECT x FROM Talk x WHERE x.conference = :conference AND x.slot IS NULL AND x.type = "+ Talk.TYPE_PROPOSED);
            q.setParameter("conference", conference);
            List<Talk> talks = (List<Talk>)q.getResultList();

            request.setAttribute("conference", conference);
            SystemUser user = LoginUtils.getInstance().getUserFromCookie(request, em);
            if(user != null) {
                request.setAttribute("user", user);
            }

            Voter voter = VoterUtils.getVoter(request, response, em, user);
            Map<Integer, Object> votes = getUserVotes(em, voter, conference);

/*            List<Talk> randomSource = new ArrayList<Talk>(talks);
            List<TalkHolder> randomisedList = new ArrayList<TalkHolder>(talks.size());
            Random random = new Random();
            while(!randomSource.isEmpty()) {
                Talk thisTalk = randomSource.remove(random.nextInt(randomSource.size()));
                boolean votedFor = votes.get(thisTalk.getId()) != null;
                TalkHolder holder = new TalkHolder(thisTalk, votedFor);
                randomisedList.add(holder);
            }
*/
            List<TalkHolder> randomisedList = new ArrayList<TalkHolder>(talks.size());
            for(Talk thisTalk : talks) {
                boolean votedFor = votes.get(thisTalk.getId()) != null;
                TalkHolder holder = new TalkHolder(thisTalk, votedFor);
                randomisedList.add(holder);
            }
            request.setAttribute("talks", randomisedList);

            request.getRequestDispatcher("/barcamp/dashboard.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    /**
     * Get the list of votes the voter has cast
     */

    private Map<Integer,Object> getUserVotes(final EntityManager em, final Voter voter, final Conference conference) {
        Query q =
                em.createQuery("SELECT x FROM TalkVote x WHERE x.talk.conference = :conference AND x.voter = :voter");
        q.setParameter("voter", voter);
        q.setParameter("conference", conference);

        Map<Integer, Object> votes = new HashMap<Integer,Object>();
        for(TalkVote vote : (List<TalkVote>)q.getResultList()) {
            if(vote.getVote() != 0) {
                votes.put(vote.getTalk().getId(), VOTE_MARKER);
            }
        }

        return votes;
    }


    public class TalkHolder {
        public Talk talk;
        public boolean votedFor;

        TalkHolder(final Talk talk, final boolean votedFor) {
            this.talk = talk;
            this.votedFor = votedFor;
        }

        public Talk getTalk() {
            return talk;
        }

        public boolean isVotedFor() {
            return votedFor;
        }
    }

}
