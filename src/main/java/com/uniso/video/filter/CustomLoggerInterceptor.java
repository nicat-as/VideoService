package com.uniso.video.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CustomLoggerInterceptor implements HandlerInterceptor {

    private Long start;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        start = System.currentTimeMillis();
        System.out.println(
                request.getRequestURI()
                        + "\n"
                        + getHeaders(request)
                        + "\n"
                        + request.getMethod()
                        + "\n"
                        + getRequestBody(request)
                        + "\n"
        );
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long end = System.currentTimeMillis();
        end -= this.start;
        System.out.println(TimeUnit.SECONDS.convert(end, TimeUnit.MILLISECONDS));
        System.out.println(
                response.getStatus()
                        + "\n"
                        + getHeaders(response)
                +"\n"
        );
    }

    private Map<String, String> getHeaders(Object object) {
        Map<String, String> map = new HashMap<>();
        if (object instanceof HttpServletRequest) {
            Enumeration<String> enumeration = ((HttpServletRequest) object).getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String element = enumeration.nextElement();
                map.put(element, ((HttpServletRequest) object).getHeader(element));
            }
        } else if (object instanceof HttpServletResponse) {
            Collection<String> headers = ((HttpServletResponse) object).getHeaderNames();
            for (String h : headers) {
                map.put(h, ((HttpServletResponse) object).getHeader(h));
            }
        }
        return map;
    }

    private String getRequestBody(HttpServletRequest request) {
        String body = null;
        try {
            if (request.getMethod().equalsIgnoreCase("post")) {
                body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            } else if (request.getMethod().equalsIgnoreCase("get")) {
                body = request.getQueryString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return body;
        }

    }


}
