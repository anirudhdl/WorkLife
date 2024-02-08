package com.employment.WorkLife.Controller;

import com.employment.WorkLife.DTO.SalaryDTO;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Models.Salary;
import com.employment.WorkLife.Service.DepartmentsService;
import com.employment.WorkLife.Service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("salary")
public class SalaryController {
    @Autowired
    SalaryService salaryService;
    @GetMapping(path="allSalary")
    public List<Salary> getAllSalary(){
        return salaryService.getAllSalary();
    }
    @GetMapping("salary_id/{salary_id}")
    public ResponseEntity<?> getBySalaryId(@PathVariable Integer salary_id) {
        // Fetch the salary here
        Salary salary = salaryService.getBySalaryId(salary_id);

        // Check if the salary is null
        if (salary == null) {
            String errorMessage = "Address not found with ID: " + salary_id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        // If salary is found, return a ResponseEntity with 200 status and the salary object
        return ResponseEntity.ok(convertToDTO(salary));
    }
    private SalaryDTO convertToDTO(Salary salary) {
        SalaryDTO dto = new SalaryDTO();
        dto.setEmployeeId(salary.getEmployee().getEmployee_id());
        dto.setEndDate(salary.getEnd_date());
        dto.setStartDate(salary.getStart_date());
        dto.setAmount(salary.getAmount());
        // Map other fields as needed
        return dto;
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @DeleteMapping("salary_id/{salary_id}")
    public ResponseEntity<String> deleteBySalary_id(@PathVariable Integer salary_id) {
        Salary salary = salaryService.getBySalaryId(salary_id);

        if (salary != null) {
            salaryService.deleteBySalary_id(salary_id);
            String message = "Salary with ID " + salary_id + " has been deleted";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            String errorMessage = "Salary with ID " + salary_id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
    @PostMapping("add")
    public String addSalary(@RequestBody Salary salary){
        return salaryService.addSalary(salary);
    }
    @PutMapping("salary_id/{salary_id}")
    public ResponseEntity<String> updateSalary(@PathVariable Integer salary_id, @RequestBody Salary updatedSalary) {
        Salary existingSalary = salaryService.getBySalaryId(salary_id);

        if (existingSalary != null) {
            // Set the salary_id of the updated salary to match the path variable
            updatedSalary.setSalary_id(salary_id);

            // Update the salary using the service method
            salaryService.updateSalary(updatedSalary);

            // Return a success message
            return ResponseEntity.ok("Salary with ID " + salary_id + " has been updated");
        } else {
            // Return a not found message if the salary with the given ID doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salary with ID " + salary_id + " not found");
        }
    }
}
