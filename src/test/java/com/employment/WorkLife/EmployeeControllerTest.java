package com.employment.WorkLife;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.employment.WorkLife.Controller.EmployeeController;
import com.employment.WorkLife.DTO.EmployeeDTO;
import com.employment.WorkLife.Models.Employee;
import com.employment.WorkLife.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        // Mocking data
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> employees = Arrays.asList(employee1, employee2);

        // Mocking service method
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Perform GET request and assert the response
        mockMvc.perform(get("/employees/allEmployees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2)) // Assuming response is a JSON array
                .andExpect(jsonPath("$[0].employee_id").value(employee1.getEmployee_id()))
                .andExpect(jsonPath("$[0].first_name").value(employee1.getFirst_name()))
                .andExpect(jsonPath("$[0].last_name").value(employee1.getLast_name()))
                .andExpect(jsonPath("$[0].email").value(employee1.getEmail()))
                .andExpect(jsonPath("$[0].phone_no").value(employee1.getPhone_no()))
                .andExpect(jsonPath("$[1].employee_id").value(employee2.getEmployee_id()))
                .andExpect(jsonPath("$[1].first_name").value(employee2.getFirst_name()))
                .andExpect(jsonPath("$[1].last_name").value(employee2.getLast_name()))
                .andExpect(jsonPath("$[1].email").value(employee2.getEmail()))
                .andExpect(jsonPath("$[1].phone_no").value(employee2.getPhone_no()));
    }
    @Test
    public void testGetEmployeeById() {
        // Mocking necessary objects
        EmployeeService employeeServiceMock = mock(EmployeeService.class);
        EmployeeController employeeController = new EmployeeController(employeeServiceMock);

        // Creating a sample employee
        Employee sampleEmployee = new Employee();
        sampleEmployee.setEmployee_id(1);
        sampleEmployee.setFirst_name("John");
        sampleEmployee.setLast_name("Doe");
        // Add more properties as needed

        // Stubbing the behavior of employeeService.getEmployeeById() method
        when(employeeServiceMock.getEmployeeById(1)).thenReturn(sampleEmployee);
        when(employeeServiceMock.getEmployeeById(2)).thenReturn(null);

        // Testing the controller method for an existing employee
        ResponseEntity<?> responseEntity = employeeController.getEmployeeById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(convertToDTO(sampleEmployee), responseEntity.getBody());

        // Testing the controller method for a non-existing employee
        ResponseEntity<?> notFoundResponseEntity = employeeController.getEmployeeById(2);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponseEntity.getStatusCode());
        assertEquals("{Employee not found with ID: }2", notFoundResponseEntity.getBody());
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployee_id(employee.getEmployee_id());
        dto.setFirst_name(employee.getFirst_name());
        dto.setLast_name(employee.getLast_name());
        // Map other properties as needed
        return dto;
    }
}

