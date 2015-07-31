package com.gtan.singerdream.repository;

import com.gtan.singerdream.model.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by user on 15-7-30.
 */
public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Manager findByName(String name);
    Page<Manager> findByFranchisee(Manager manager,Pageable pageable);
}
