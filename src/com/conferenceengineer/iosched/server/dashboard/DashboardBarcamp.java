package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.Talk;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Display the conference dashboard to the user.
 */
public class DashboardBarcamp extends DashboardBase {
    /**
     * Comparator to sort the talks by the number of votes with the highest voted coming first.
     */

    private static Comparator<TalkHolder> VOTE_SORTER = new Comparator<TalkHolder>() {
        @Override
        public int compare(TalkHolder talk1, TalkHolder talk2) {
            return talk2.getVotes() - talk1.getVotes();
        }
    };

    /**
     * Overrideable method to add elements to the request object.
     */
    @Override
    protected void populateRequest(final HttpServletRequest request, final EntityManager em) {
        Conference conference = (Conference) request.getAttribute("conference");
        Query q =
                em.createQuery("SELECT x FROM Talk x WHERE x.conference = :conference AND x.slot IS NULL AND x.type = "+ Talk.TYPE_PROPOSED);
        q.setParameter("conference", conference);
        List<Talk> talkJPAResults = (List<Talk>)q.getResultList();
        List<TalkHolder> talks = new ArrayList<TalkHolder>(talkJPAResults.size());
        if(talkJPAResults != null) {
            Query countQuery = em.createQuery("SELECT COUNT(x.id) FROM TalkVote x WHERE x.talk = :talk");
            for(Talk talk : talkJPAResults) {
                countQuery.setParameter("talk", talk);
                talks.add(new TalkHolder(talk, ((Number)countQuery.getSingleResult()).intValue()));
            }
        }
        Collections.sort(talks, VOTE_SORTER);
        request.setAttribute("sortedTalks", talks);

        request.setAttribute("serverStatus", "All servers are operational");
        request.setAttribute("serverStatusType", "Good");
    }

    /**
     * Get the next page to send the user to.
     */

    @Override
    protected String getNextPage() {
        return "dashboard_barcamp.jsp";
    }

    /**
     * Wrapper for the talk information
     */

    public class TalkHolder {
        private int votes;
        private Talk talk;

        TalkHolder(final Talk talk, int votes) {
            this.talk = talk;
            this.votes = votes;
        }

        public int getVotes() {
            return votes;
        }

        public Talk getTalk() {
            return talk;
        }
    }

}
