package com.employment.WorkLife.Controller;

import com.employment.WorkLife.DTO.EmployeeDTO;
import com.employment.WorkLife.Dao.DepartmentsDao;
import com.employment.WorkLife.Models.Employee;
import com.employment.WorkLife.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {
         @Autowired
         EmployeeService employeeService;

        @Autowired
        private DepartmentsDao departmentDao;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
          @GetMapping(path="allEmployees",produces = "application/json")
          public List<EmployeeDTO> getAllEmployees(){
              List<Employee> employees = employeeService.getAllEmployees();
                  return employees.stream()
                                  .map(this::convertToDTO)
                                   .collect(Collectors.toList());
         }
         private EmployeeDTO convertToDTO(Employee employee) {
           EmployeeDTO dto = new EmployeeDTO();
           dto.setEmployee_id(employee.getEmployee_id());
           dto.setFirst_name(employee.getFirst_name());
           dto.setLast_name(employee.getLast_name());
           dto.setEmail(employee.getEmail());
           dto.setPhone_no(employee.getPhone_no());

           return dto;
        }

    @GetMapping("employee_id/{employee_id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer employee_id) {
        Employee employee = employeeService.getEmployeeById(employee_id);

        if (employee == null) {
            String errorMessage = "{Employee not found with ID: }" + employee_id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        return ResponseEntity.ok(convertToDTO(employee));
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @DeleteMapping("employee_id/{employee_id}")
    public ResponseEntity<String> deleteByEmployee_id(@PathVariable Integer employee_id) {
        try {
            ResponseEntity<String> response = employeeService.deleteByEmployee_id(employee_id);
            return response;
        } catch (Exception e) {
            String errorMessage = "An error occurred while deleting Employee with ID " + employee_id;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("add")
          public String addEmployee(@RequestBody Employee employee){
                 return employeeService.addDepartment(employee);
          }

    @PutMapping("employee_id/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee updatedEmployee) {
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);

        if (existingEmployee != null) {
            // Set the employee_id of the updated employee to match the path variable
            updatedEmployee.setEmployee_id(employeeId);

            // Update the employee using the service method
            employeeService.updateEmployee(updatedEmployee);

            // Return a success message
            return ResponseEntity.ok("Employee with ID " + employeeId + " has been updated");
        } else {
            // Return a not found message if the employee with the given ID doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + employeeId + " not found");
        }
    }

}
