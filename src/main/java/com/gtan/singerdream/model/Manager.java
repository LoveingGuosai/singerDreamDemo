package com.gtan.singerdream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.gtan.singerdream.model.constant.ManagerRole;
import com.gtan.singerdream.model.security.ManagerAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by user on 15-7-30.
 */
@Entity
@Table
public class Manager {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;
    @NotNull
    private String pwd;
    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private ManagerRole role;

    @ManyToOne()
    @JsonIgnore
    private Manager franchisee;

    @Transient
    private long expires;

    public Manager() {
    }

    public Manager(String name, String pwd, String phoneNumber, ManagerRole role, Manager franchisee, long expires) {
        this.name = name;
        this.pwd = pwd;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.franchisee = franchisee;
        this.expires = expires;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ManagerRole getRole() {
        return role;
    }

    public void setRole(ManagerRole role) {
        this.role = role;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public Manager getFranchisee() {
        return franchisee;
    }

    public void setFranchisee(Manager franchisee) {
        this.franchisee = franchisee;
    }

    public Set<ManagerAuthority> getAuthorities(){
        //TODO 不太理解
        return Sets.newHashSet(new ManagerAuthority(this,"ROLE"+role.toString()));
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager)) return false;

        Manager manager = (Manager) o;

        if (getId() != manager.getId()) return false;
        if (getExpires() != manager.getExpires()) return false;
        if (getName() != null ? !getName().equals(manager.getName()) : manager.getName() != null) return false;
        if (getPwd() != null ? !getPwd().equals(manager.getPwd()) : manager.getPwd() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(manager.getPhoneNumber()) : manager.getPhoneNumber() != null)
            return false;
        if (getRole() != manager.getRole()) return false;
        return !(getFranchisee() != null ? !getFranchisee().equals(manager.getFranchisee()) : manager.getFranchisee() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPwd() != null ? getPwd().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (getFranchisee() != null ? getFranchisee().hashCode() : 0);
        result = 31 * result + (int) (getExpires() ^ (getExpires() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", franchisee=" + franchisee +
                ", expires=" + expires +
                '}';
    }
}
