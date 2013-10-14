package com.conferenceengineer.iosched.server.barcamp;

import com.conferenceengineer.iosched.server.datamodel.SystemUser;
import com.conferenceengineer.iosched.server.datamodel.Talk;
import com.conferenceengineer.iosched.server.datamodel.TalkVote;
import com.conferenceengineer.iosched.server.datamodel.Voter;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.LoginUtils;
import com.conferenceengineer.iosched.server.utils.ServletUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to handle a vote
 */
public class VoteServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        String vote = request.getParameter("vote");
        String talkId = request.getParameter("talk");
        if(vote == null || vote.isEmpty() || talkId == null || talkId.isEmpty()) {
            ServletUtils.redirectTo(request, response, "/barcamp/view/");
            return;
        }


        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            SystemUser user = LoginUtils.getInstance().getUserFromCookie(request, em);
            Voter voter = VoterUtils.getVoter(request, response, em, user);
            Talk talk = em.find(Talk.class, Integer.parseInt(talkId));

            em.getTransaction().begin();
            Query q = em.createQuery("SELECT x FROM TalkVote x WHERE x.talk = :talk AND x.voter = :voter");
            q.setParameter("talk", talk);
            q.setParameter("voter", voter);
            List<TalkVote> votes = q.getResultList();
            TalkVote talkVote;
            if(votes.isEmpty()) {
                talkVote = new TalkVote();
                talkVote.setTalk(talk);
                talkVote.setVoter(voter);
            } else {
                talkVote = votes.get(0);
            }
            talkVote.setVote(Integer.parseInt(vote));
            if(votes.isEmpty()) {
                em.persist(talkVote);
            }
            em.getTransaction().commit();

            ServletUtils.redirectTo(request, response, "/barcamp/view/");
        } finally {
            em.close();
        }
    }
}
