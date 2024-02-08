package com.employment.WorkLife.Service;

import com.employment.WorkLife.Dao.EmployeeDao;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Models.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeService  {
    @Autowired
    EmployeeDao employeeDao;
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public Employee getEmployeeById(Integer employee_id) {
        return employeeDao.findById(employee_id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + employee_id));
    }
    @Transactional
    public ResponseEntity<String> deleteByEmployee_id(Integer employee_id) {
        Optional<Employee> employeeOptional = employeeDao.findById(employee_id);

        if (employeeOptional.isPresent()) {
            employeeDao.deleteById(employee_id);
            String message = "Employee with ID " + employee_id + " successfully deleted.";
            return ResponseEntity.ok(message); // Changed to HttpStatus.OK
        } else {
            String errorMessage = "Employee with ID " + employee_id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }



    public String addDepartment(Employee employee) {
        employeeDao.save(employee);
        return "Employee has been added successfully";
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeDao.save(updatedEmployee);
    }
}
