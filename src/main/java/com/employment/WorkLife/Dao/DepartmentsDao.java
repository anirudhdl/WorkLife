package com.employment.WorkLife.Dao;

import com.employment.WorkLife.Models.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentsDao extends JpaRepository <Departments,Integer>{

    /*List<Departments> findByDepartmentName(String departmentName);*/

}
