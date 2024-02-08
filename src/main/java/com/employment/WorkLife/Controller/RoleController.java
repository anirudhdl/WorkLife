package com.employment.WorkLife.Controller;

import com.employment.WorkLife.DTO.RoleDTO;
import com.employment.WorkLife.Models.Departments;
import com.employment.WorkLife.Models.RoleModel;
import com.employment.WorkLife.Service.DepartmentsService;
import com.employment.WorkLife.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("roles")
public class RoleController {
    @Autowired
    RoleService roleService;
    @GetMapping(path="allRoles", produces = "application/json")
    public List<RoleDTO> getAllRoles() {
        List<RoleModel> roles = roleService.getAllRoles();
        return roles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RoleDTO convertToDTO(RoleModel role) {
        RoleDTO dto = new RoleDTO();
        dto.setRole_id(role.getRole_id());
        dto.setRole_name(role.getRole_name());
        // Map other fields as needed
        return dto;
    }


    @GetMapping("role_id/{role_id}")
    public ResponseEntity<?> getRoleByRole_Id(@PathVariable Integer role_id) {
        RoleModel role = roleService.getRolesByRole_id(role_id);

        if (role == null) {
            String errorMessage = "Role not found with ID: " + role_id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        return ResponseEntity.ok(convertToDTO(role));
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @DeleteMapping("role_id/{role_id}")
    public ResponseEntity<String> deleteByRoleId(@PathVariable Integer role_id) {
        RoleModel role = roleService.getRolesByRole_id(role_id);

        if (role != null) {
            roleService.deleteByRole_id(role_id);
            String message = "Role with ID " + role_id + " has been deleted";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            String errorMessage = "Role with ID " + role_id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
    @PostMapping("add")
    public String addRole(@RequestBody RoleModel roleModel){
        return roleService.addRole(roleModel);
    }
    @PutMapping("role_id/{role_id}")
    public ResponseEntity<String> updateRoleModel(@PathVariable Integer role_id, @RequestBody RoleModel updatedRoleModel) {
        RoleModel existingRoleModel = roleService.getRolesByRole_id(role_id);

        if (existingRoleModel != null) {
            // Set the role_id of the updated role model to match the path variable
            updatedRoleModel.setRole_id(role_id);

            // Update the role model using the service method
            roleService.updateRoleModel(updatedRoleModel);

            // Return a success message
            return ResponseEntity.ok("RoleModel with ID " + role_id + " has been updated");
        } else {
            // Return a not found message if the role model with the given ID doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RoleModel with ID " + role_id + " not found");
        }
    }
}
