package com.uniso.video.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final Logger logger = LogManager.getLogger(ApiKeyAuthFilter.class);

    private String requestHeader;

    public ApiKeyAuthFilter(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        logger.info("Getting request header's value ...");
        return request.getHeader(requestHeader);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
