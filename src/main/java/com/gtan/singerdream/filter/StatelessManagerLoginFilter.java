package com.gtan.singerdream.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtan.singerdream.model.Manager;
import com.gtan.singerdream.model.json.ManagerLoginBody;
import com.gtan.singerdream.model.security.ManagerAuthentication;
import com.gtan.singerdream.service.ManagerService;
import com.gtan.singerdream.service.ManagerTokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by user on 15-7-30.
 */
public class StatelessManagerLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final ManagerTokenAuthenticationService managerTokenAuthenticationService;
    private final ManagerService managerService;

    public StatelessManagerLoginFilter(
            String urlMapping,
            ManagerTokenAuthenticationService managerTokenAuthenticationService,
            ManagerService managerService) {
        super(new AntPathRequestMatcher(urlMapping));
        this.managerTokenAuthenticationService = managerTokenAuthenticationService;
        this.managerService = managerService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        ManagerLoginBody loginBody = new ObjectMapper().readValue(request.getInputStream(), ManagerLoginBody.class);
        Manager manager = managerService.findByName(loginBody.getName());
        Manager dbManager = Optional.ofNullable(manager).
                filter(m -> BCrypt.checkpw(loginBody.getPassword(), m.getPwd())).
                orElseThrow(() -> new AuthenticationServiceException("用户名或者密码错误"));
        return new PreAuthenticatedAuthenticationToken(loginBody.getName(), loginBody.getPassword(), dbManager.getAuthorities());
    }

    @Override
    protected void successfulAuthentication
            (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        final Manager authenticationManager = managerService.findByName(authResult.getName());
        final ManagerAuthentication managerAuthentication = new ManagerAuthentication(authenticationManager);
        logger.info("add ManagerAuthentication------->"+managerAuthentication);
        managerTokenAuthenticationService.addManagerAuthentication(response,managerAuthentication);

        SecurityContextHolder.getContext().setAuthentication(managerAuthentication);
    }
}
