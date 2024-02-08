package com.employment.WorkLife.Service;

import com.employment.WorkLife.Dao.AddressDao;
import com.employment.WorkLife.Dao.DepartmentsDao;
import com.employment.WorkLife.Dao.EmployeeDao;
import com.employment.WorkLife.Models.Address;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressDao addressDao;
    private final EmployeeDao employeeDao;

    public AddressService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    public  List<Address> getAllAddress() {
        return addressDao.findAll();
    }

    public Address getAddress_id(Integer address_id) {
        return addressDao.findById(address_id)
                .orElseThrow(() -> new NoSuchElementException("Department not found with ID: " + address_id));
    }

    public String deleteByAddress_id(Integer address_id) {
        Optional<Address> addressOptional = addressDao.findById(address_id);

        if (addressOptional.isPresent()) {
            addressDao.deleteById(address_id);
            return "Department with ID " + address_id + " successfully deleted.";
        } else {
            return "Department with ID " + address_id + " not found.";
        }
    }

    public String addAddress(Address address) {
        // Fetch the employee object based on the provided employee_id
        Employee employee = address.getEmployee();
        if (employee != null) {
            employee = employeeDao.findById(employee.getEmployee_id()).orElse(null);
            if (employee == null) {
                return "Employee with ID " + address.getEmployee().getEmployee_id() + " not found";
            }
            address.setEmployee(employee);
        }

        // Save the address
        addressDao.save(address);

        return "success";

    }
    public Address updateAddress(Address updatedAddress) {
        // You might want to add validation or additional logic here before updating
        // For example, you can check if the updatedAddress has valid data

        // Save the updated address to the database
        return addressDao.save(updatedAddress);
    }
}
