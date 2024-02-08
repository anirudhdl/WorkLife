package com.employment.WorkLife.Service;

import com.employment.WorkLife.Dao.DepartmentsDao;
import com.employment.WorkLife.Dao.RoleDao;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Models.RoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;
    public List<RoleModel> getAllRoles() {
        return roleDao.findAll();
    }

    public RoleModel getRolesByRole_id(Integer role_id) {
        return roleDao.findById(role_id)
                .orElseThrow(() -> new NoSuchElementException("Department not found with ID: " + role_id));
    }

    public String deleteByRole_id(Integer role_id) {
        Optional<RoleModel> roleModelOptional = roleDao.findById(role_id);

        if (roleModelOptional.isPresent()) {
            roleDao.deleteById(role_id);
            return "Department with ID " + role_id+ " successfully deleted.";
        } else {
            return "Department with ID " +role_id + " not found.";
        }
    }
    public String addRole(RoleModel roleModel) {
        roleDao.save(roleModel);
        return "success";
    }
    public RoleModel updateRoleModel(RoleModel updatedRoleModel) {
        // You might want to add validation or additional logic here before updating
        // For example, you can check if the updatedRoleModel has valid data

        // Save the updated role model to the database
        return roleDao.save(updatedRoleModel);
    }
}
