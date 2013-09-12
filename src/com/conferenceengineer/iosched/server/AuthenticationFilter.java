package com.conferenceengineer.iosched.server;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to verify the user is authenticated.
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ; // No init required
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(!"True".equals(((HttpServletRequest) servletRequest).getSession().getAttribute("Authenticated"))) {
            ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        ; // No destruction required
    }
}
