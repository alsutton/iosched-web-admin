package com.conferenceengineer.iosched.server.dashboard;

/**
 * Display the conference dashboard to the user.
 */
public class DashboardSpeakers extends DashboardBase {
    /**
     * Get the next page to send the user to.
     */

    @Override
    protected String getNextPage() {
        return "dashboard_speakers.jsp";
    }
}
