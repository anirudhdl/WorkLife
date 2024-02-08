package com.employment.WorkLife.Controller;

import com.employment.WorkLife.DTO.DepartmentDTO;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("departments")
public class DepartmentsController {
    @Autowired
    DepartmentsService departmentsService;
    @GetMapping(path="allDepartments",produces = "application/json")
    public List<DepartmentDTO> getAllDepartments(){
        List<Departments> departments = departmentsService.getAllDepartments();
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DepartmentDTO convertToDTO(Departments departments) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(departments.getDepartment_id());
        dto.setName(departments.getDepartment_name());
        // Map other fields as needed
        return dto;
    }

    @GetMapping("department_id/{department_id}")
    public ResponseEntity<?> getDepartmentsByDepartment_id(@PathVariable Integer department_id) {
        Departments department = departmentsService.getDepartmentsByDepartment_id(department_id);

        if (department == null) {
            String errorMessage = "{Department not found with ID: }" + department_id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        return ResponseEntity.ok(convertToDTO(department));
    }
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @DeleteMapping("department_id/{department_id}")
    public ResponseEntity<String> deleteByDepartment_id(@PathVariable Integer department_id) {
        Departments department = departmentsService.getDepartmentsByDepartment_id(department_id);

        if (department != null) {
            String deletionMessage = departmentsService.deleteByDepartment_id(department_id);
            if (deletionMessage != null && deletionMessage.equals("deleted")) {
                String message = "Department with ID " + department_id + " has been deleted";
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
            } else {
                String errorMessage = "Department ID " + department_id+ "has been successfully deleted";
                return ResponseEntity.status(HttpStatus.OK).body(errorMessage);
            }
        } else {
            String errorMessage = "Department with ID " + department_id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping("add")
    public String addDepartment(@RequestBody Departments departments){
        System.out.println("============printing employee==============");
        System.out.println(departments);
        return departmentsService.addDepartment(departments);
    }
    @PutMapping("department_id/{department_id}")
    public ResponseEntity<String> updateDepartment(@PathVariable Integer department_id, @RequestBody Departments updatedDepartment) {
        Departments existingDepartment = departmentsService.getDepartmentsByDepartment_id(department_id);

        if (existingDepartment != null) {
            // Set the department_id of the updated department to match the path variable
            updatedDepartment.setDepartment_id(department_id);

            // Update the department using the service method
            departmentsService.updateDepartment(updatedDepartment);

            // Return a success message
            return ResponseEntity.ok("Department with ID " + department_id + " has been updated");
        } else {
            // Return a not found message if the department with the given ID doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with ID " + department_id + " not found");
        }
    }
}
