package com.employment.WorkLife.Dao;

import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleModel,Integer> {
}
