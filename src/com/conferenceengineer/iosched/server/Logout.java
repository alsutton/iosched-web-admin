package com.conferenceengineer.iosched.server;

import com.conferenceengineer.iosched.server.utils.LoginUtils;
import com.conferenceengineer.iosched.server.utils.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Log the user out.
 */
public class Logout extends HttpServlet {

    /**
     * Handle a users request to logout
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */

    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        LoginUtils.getInstance().removeCookie(response);
        ServletUtils.redirectToIndex(request, response);
    }

}
