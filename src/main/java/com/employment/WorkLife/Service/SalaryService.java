package com.employment.WorkLife.Service;

import com.employment.WorkLife.Dao.DepartmentsDao;
import com.employment.WorkLife.Dao.SalaryDao;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Models.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    SalaryDao salaryDao;
    public List<Salary> getAllSalary() {
        return salaryDao.findAll();
    }

    public Salary getBySalaryId(Integer salaryId) {
        Optional<Salary> salaryOptional = salaryDao.findById(salaryId);
        return salaryOptional.orElseThrow(() -> new NoSuchElementException("Salary not found with ID: " + salaryId));
    }



    public String deleteBySalary_id(Integer salary_id) {
        Optional<Salary> salaryOptional = salaryDao.findById(salary_id);

        if (salaryOptional.isPresent()) {
              salaryDao.deleteById(salary_id);
            return "Department with ID " + salary_id + " successfully deleted.";
        } else {
            return "Department with ID " +salary_id+ " not found.";
        }
    }

    public String addSalary(Salary salary) {
        salaryDao.save(salary);
        return "success";
    }
    public Salary updateSalary(Salary updatedSalary) {
        // You might want to add validation or additional logic here before updating
        // For example, you can check if the updatedSalary has valid data

        // Save the updated salary to the database
        return salaryDao.save(updatedSalary);
    }
}
