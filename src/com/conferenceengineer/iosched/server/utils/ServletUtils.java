package com.conferenceengineer.iosched.server.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Simple utility routines.
 */
public class ServletUtils {

    private ServletUtils() {
        super();
    }

    public static void redirectToIndex(final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        response.sendRedirect("/"+request.getContextPath());
    }

    public static void redirectTo(final HttpServletRequest request, final HttpServletResponse response,
                                  final String path)
        throws IOException {
        String contextPath = request.getContextPath();
        if(contextPath.endsWith("/")) {
            contextPath = contextPath.substring(contextPath.length()-1);
        }
        response.sendRedirect(contextPath+path);
    }

}
