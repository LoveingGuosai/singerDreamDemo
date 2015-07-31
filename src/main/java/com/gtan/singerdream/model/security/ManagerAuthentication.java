package com.gtan.singerdream.model.security;

import com.gtan.singerdream.model.Manager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by user on 15-7-30.
 */
public class ManagerAuthentication  implements Authentication{

    private final Manager manager;

    private boolean authenticated = true;

    public ManagerAuthentication(Manager manager) {
        this.manager = manager;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return manager.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return manager.getPwd();
    }

    @Override
    public Manager getDetails() {
        return manager;
    }

    @Override
    public Object getPrincipal() {
        return manager.getName();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated){
        this.authenticated=authenticated;
    }

    @Override
    public String getName() {
        return manager.getName();
    }
}
