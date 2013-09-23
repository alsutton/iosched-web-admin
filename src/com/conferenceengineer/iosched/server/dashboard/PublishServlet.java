package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceDAO;
import com.conferenceengineer.iosched.server.datamodel.ConferenceMetadata;
import com.conferenceengineer.iosched.server.exporters.SessionsJSON;
import com.conferenceengineer.iosched.server.exporters.SpeakersJSON;
import com.conferenceengineer.iosched.server.exporters.TrackSessionsJSON;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Display the conference dashboard to the user.
 */
public class PublishServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {
        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            String conferenceIdString = request.getServletContext().getInitParameter("conferenceId");
            Integer conferenceId = Integer.parseInt(conferenceIdString);

            Conference conference = ConferenceDAO.getInstance().get(em, conferenceId);

            publishUpdate("presenters", SpeakersJSON.export(conference));
            publishUpdate("tracks", TrackSessionsJSON.export(conference));
            publishUpdate("sessions", SessionsJSON.export(conference));

            em.getTransaction().begin();
            ConferenceMetadata metadata = conference.getMetadata();
            if(metadata == null) {
                metadata = new ConferenceMetadata();
                metadata.setConference(conference);
                em.persist(metadata);
                conference.setMetadata(metadata);
            }
            metadata.setLastPublished(Calendar.getInstance());
            em.getTransaction().commit();

            request.getSession().setAttribute("message", "The updates have been published");
        } catch(IOException e) {
            request.getSession().setAttribute("error", "Problem during publish : "+e.getMessage());
        } finally {
            em.close();
        }

        response.sendRedirect("Dashboard");
    }

    private void publishUpdate(final String name, final String data)
            throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost("https://ce-uk-1.funkyandroid.net/receiver/receiver");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("file", name));
        params.add(new BasicNameValuePair("data", data));
        post.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = httpClient.execute(post);
        try {
            if(response.getStatusLine().getStatusCode() != HttpServletResponse.SC_OK) {
                throw new IOException("The publishing server reported an error : "+response.getStatusLine());
            }
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
    }
}
