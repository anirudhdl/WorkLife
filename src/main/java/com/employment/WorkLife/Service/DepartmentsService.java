package com.employment.WorkLife.Service;

import com.employment.WorkLife.Dao.DepartmentsDao;
import com.employment.WorkLife.Models.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DepartmentsService {
    @Autowired
    DepartmentsDao departmentsDao;
    public List<Departments> getAllDepartments() {
       return departmentsDao.findAll();
    }

    public Departments getDepartmentsByDepartment_id(Integer department_id){
        return departmentsDao.findById(department_id)
                .orElseThrow(() -> new NoSuchElementException("Department not found with ID: " + department_id));
    }

    public String deleteByDepartment_id(Integer department_id) {
        Optional<Departments> departmentOptional = departmentsDao.findById(department_id);

        if (departmentOptional.isPresent()) {
            departmentsDao.deleteById(department_id);
            return "Department with ID " + department_id + " successfully deleted.";
        } else {
            return "Department with ID " + department_id + " not found.";
        }
    }

    public String addDepartment(Departments departments) {
        departmentsDao.save(departments);
        return "The Given Department has been added successfully";
    }

    public Departments updateDepartment(Departments updatedDepartment) {
        return departmentsDao.save(updatedDepartment);
    }
}
