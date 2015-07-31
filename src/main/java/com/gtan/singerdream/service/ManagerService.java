package com.gtan.singerdream.service;

import com.gtan.singerdream.model.Manager;
import com.gtan.singerdream.model.constant.ManagerRole;
import com.gtan.singerdream.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 15-7-30.
 */
@Service
public class ManagerService  extends BaseService<Manager,Long>{

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    JpaRepository<Manager, Long> getDao() {
        return managerRepository;
    }

    @Transactional
    public Manager findByName(String name){return managerRepository.findByName(name);}
    @Transactional
    public Page<Manager> findByFranchisee(Pageable pageable){
        Manager manager = (Manager)SecurityContextHolder.getContext().getAuthentication().getDetails();
        return managerRepository.findByFranchisee(manager,pageable);
    }

    @Transactional
    public PreAuthenticatedAuthenticationToken authenticate(String name,String pwd){
        Manager manager = findByName(name);
        if(manager!=null&& BCrypt.checkpw(pwd,manager.getPwd())){
            //TODO 为什么 if else 走的一样
            return new PreAuthenticatedAuthenticationToken(manager.getName(),manager.getPwd(),manager.getAuthorities());
        }else{
            return new PreAuthenticatedAuthenticationToken(manager.getName(),manager.getPwd(),manager.getAuthorities());
        }
    }

    public Manager addManager(String name) {

        Manager manager = managerRepository.findByName(name);
        if(manager==null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            manager = new Manager();
            manager.setFranchisee((Manager)SecurityContextHolder.getContext().getAuthentication().getDetails());
            manager.setName(name);
            manager.setPhoneNumber("0333333333");
            manager.setPwd(encoder.encode("123456"));
            manager.setRole(ManagerRole.TEACHER);
            save(manager);
            return manager;
        }
        throw new IllegalArgumentException("用户名已经被使用");
    }
}
