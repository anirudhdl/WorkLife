package com.employment.WorkLife;

import com.employment.WorkLife.Dao.EmployeeDao;
import com.employment.WorkLife.Models.Employee;
import com.employment.WorkLife.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testDeleteByEmployeeId() {
        // Arrange
        Integer employeeId = 1;
        Employee employee = new Employee();

        // Mock the behavior of the employeeDao
        when(employeeDao.findById(employeeId)).thenReturn(Optional.of(employee));
        doNothing().when(employeeDao).deleteById(employeeId);

        // Act
        // Act
        // Act
        ResponseEntity<String> responseEntity = employeeService.deleteByEmployee_id(1);

        // Assert
        assertEquals("Employee with ID 1 successfully deleted.", responseEntity.getBody());

        // Verify that the deleteById method is called with the correct argument
        verify(employeeDao, times(1)).deleteById(1);
    }

}

