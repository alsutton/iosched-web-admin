package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.datamodel.Conference;
import com.conferenceengineer.iosched.server.datamodel.ConferenceMetadata;
import com.conferenceengineer.iosched.server.exporters.SessionsJSON;
import com.conferenceengineer.iosched.server.exporters.SpeakersJSON;
import com.conferenceengineer.iosched.server.exporters.TrackSessionsJSON;
import com.conferenceengineer.iosched.server.utils.ConferenceUtils;
import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Display the conference dashboard to the user.
 */
public class PublishServlet extends HttpServlet {

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {

        String dataRoot = request.getServletContext().getInitParameter("data_root");
        if(dataRoot == null) {
            dataRoot = System.getProperty("user.home");
        }

        EntityManager em = EntityManagerWrapperBridge.getEntityManager(request);
        try {
            Conference conference = ConferenceUtils.getCurrentConference(request, em);

            publishUpdate(dataRoot, conference, "presenters", SpeakersJSON.export(conference));
            publishUpdate(dataRoot, conference, "tracks", TrackSessionsJSON.export(conference));
            publishUpdate(dataRoot, conference, "sessions", SessionsJSON.export(conference));

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

    /**
     * Publish the data for clients to download.
     *
     * @param dataRoot The root of all conference engineer data files.
     * @param conference The conference the data is being exported for.
     * @param name The name of the file being exported.
     * @param data The data to be exported.
     *
     * @throws IOException
     */
    private void publishUpdate(final String dataRoot, final Conference conference,
                               final String name, final String data)
            throws IOException {
        /*
            Old server publishing code

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost("https://app3.funkyandroid.net/receiver/receiver");
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
        */


        File dataRootDir = new File(dataRoot);
        File eventBase = new File(dataRootDir, "events/"+conference.getHashtag());
        if(!eventBase.exists()) {
            if(!eventBase.mkdirs()) {
                log("Unable to make directories for "+eventBase.getCanonicalPath());
            }
        }
        File exportFile = new File(eventBase, name+".json");
        File exportTempFile = new File(eventBase, name+".tmp");
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(exportTempFile));
        try {
            printWriter.println(data);
        } finally {
            printWriter.close();
        }

        if(exportFile.exists()) {
            if(!exportFile.delete()) {
                log("Unable to delete existing file "+exportFile.getCanonicalPath());
            }
        }
        if(!exportTempFile.renameTo(exportFile)) {
            log("Unable to rename "+exportTempFile.getCanonicalPath()+" to "+exportFile.getCanonicalPath());
        }

    }
}
