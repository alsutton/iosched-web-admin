package com.conferenceengineer.iosched.server.dashboard;

import com.conferenceengineer.iosched.server.utils.EntityManagerWrapperBridge;
import com.conferenceengineer.iosched.server.utils.LoginUtils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Display the conference dashboard to the user.
 */
public class Dashboard extends DashboardBase {

    /**
     * Overrideable method to add elements to the request object.
     */
    @Override
    protected void populateRequest(final HttpServletRequest request, final EntityManager em) {
        request.setAttribute(
                "user",
                LoginUtils.getInstance().getUserFromCookie(request, em));

        request.setAttribute("serverStatus", "All servers are operational");
        request.setAttribute("serverStatusType", "Good");

        List<String> timezones = Arrays.asList(TimeZone.getAvailableIDs());
        Collections.sort(timezones);
        request.setAttribute("timezones", timezones);
    }


    /**
     * Get the next page to send the user to.
     */

    @Override
    protected String getNextPage() {
        return "dashboard.jsp";
    }
}
