package com.gtan.singerdream.controller;

import com.gtan.singerdream.model.Manager;
import com.gtan.singerdream.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 15-7-31.
 */
@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping("/api/backend/cunrrentManager")
    public String findCurrentManager(){
        return ((Manager)SecurityContextHolder.getContext().getAuthentication().getDetails()).getRole().toString();
    }

    @RequestMapping("/api/backend/addManager")
    public Manager addManager(@RequestParam("name") String name){
        return managerService.addManager(name);
    }

    @RequestMapping("/api/backend/teachers/findByCurrentManager")
    public Page<Manager> findByCurrentManager (Pageable pageable){
        return managerService.findByFranchisee(pageable);
    }
}
