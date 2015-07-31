package com.gtan.singerdream.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gtan.singerdream.model.Manager;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by user on 15-7-30.
 */
@Entity
@IdClass(ManagerAuthority.class)
public class ManagerAuthority implements GrantedAuthority {
    @NotNull
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Manager manager;

    @NotNull
    @Id
    private String authority;

    public ManagerAuthority() {
    }

    public ManagerAuthority(Manager manager, String authority) {
        this.manager = manager;
        this.authority = authority;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerAuthority)) return false;

        ManagerAuthority that = (ManagerAuthority) o;

        if (getManager() != null ? !getManager().equals(that.getManager()) : that.getManager() != null) return false;
        return !(getAuthority() != null ? !getAuthority().equals(that.getAuthority()) : that.getAuthority() != null);

    }

    @Override
    public int hashCode() {
        int result = getManager() != null ? getManager().hashCode() : 0;
        result = 31 * result + (getAuthority() != null ? getAuthority().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ManagerAuthority{" +
                "manager=" + manager +
                ", authority='" + authority + '\'' +
                '}';
    }
}
