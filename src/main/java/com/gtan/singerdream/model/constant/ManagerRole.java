package com.gtan.singerdream.model.constant;

import com.gtan.singerdream.model.Manager;
import com.gtan.singerdream.model.security.ManagerAuthority;

/**
 * Created by user on 15-7-30.
 */
public enum ManagerRole {
    ADMIN,FRANCHISEE,TEACHER;

    public ManagerAuthority asAuthorityFor(final Manager manager){
        final ManagerAuthority managerAuthority = new ManagerAuthority();
        managerAuthority.setManager(manager);
        managerAuthority.setAuthority("ROLE_"+toString());
        return managerAuthority;
    }

    public static ManagerRole valueOf(final ManagerAuthority managerAuthority){
        switch (managerAuthority.getAuthority()){
            case "ROLE_ADMIN":return ADMIN;
            case "ROLE_FRANCHISEE":return FRANCHISEE;
            case "ROLE_TEACHER" :return  TEACHER;
        }
        throw new IllegalArgumentException("no role finded for authority"+managerAuthority.getAuthority());
    }
}
