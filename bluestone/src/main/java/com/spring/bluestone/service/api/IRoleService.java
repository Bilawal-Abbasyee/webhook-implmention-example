package com.spring.bluestone.service.api;

import com.spring.bluestone.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> saveRole(List<String> roleNames);
    Role updateRole(Long id, String roleName);
    Role deleteRole(Long id);
    Role getRoleById(Long id);
    List<Role> getAllRoles();
}

