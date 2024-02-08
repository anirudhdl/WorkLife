package com.employment.WorkLife.Dao;

import com.employment.WorkLife.Models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryDao extends JpaRepository<Salary,Integer> {
}
