package com.gtan.singerdream.filter;

import com.gtan.singerdream.model.security.ManagerAuthentication;
import com.gtan.singerdream.service.ManagerTokenAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by qiyang on 15-7-30.
 * add Authentication to SecurityContext
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {
    private final Logger logger = LoggerFactory.getLogger(StatelessAuthenticationFilter.class);
    private final ManagerTokenAuthenticationService managerTokenAuthenticationService;

    public StatelessAuthenticationFilter(ManagerTokenAuthenticationService managerTokenAuthenticationService) {
        this.managerTokenAuthenticationService = managerTokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = managerTokenAuthenticationService.getAuthentication((HttpServletRequest) request);
        logger.info("authentication------------>"+authentication);
        SecurityContextHolder.getContext().setAuthentication(
                authentication
        );
        chain.doFilter(request,response);
    }
}
