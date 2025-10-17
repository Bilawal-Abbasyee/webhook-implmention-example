package com.spring.bluestone.controller.internal;

import com.spring.bluestone.dto.response.ApiResponse;
import com.spring.bluestone.entity.Role;
import com.spring.bluestone.service.impl.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PutMapping("/updateRole/{id}/{roleName}")
    public ResponseEntity<ApiResponse<Role>> updateRole(@PathVariable Long id, @PathVariable String roleName) {
        Role result = roleService.updateRole(id, roleName);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Role updated successfully", result));
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<ApiResponse<Role>> deleteRole(@PathVariable Long id) {
        Role result = roleService.deleteRole(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Role deleted successfully", result));
    }

    @GetMapping("/getRoleById/{id}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Role retrieved successfully", role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/getAllRoles")
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "All roles retrieved successfully", roles));
    }
}