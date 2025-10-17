package com.spring.bluestone.service.impl;

import com.spring.bluestone.entity.Role;
import com.spring.bluestone.exception.type.InvalidRequestException;
import com.spring.bluestone.exception.type.RoleNotFoundException;
import com.spring.bluestone.repo.RoleRepository;
import com.spring.bluestone.service.api.IRoleService;
import com.spring.bluestone.util.ErrorMessageUtil;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> saveRole(List<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            throw new InvalidRequestException("Role name list must not be empty");
        }
        return roleNames.stream().map(roleName -> {
            Role newRole = new Role();
            newRole.setName(roleName);
            return roleRepository.save(newRole);
        }).toList();
    }

    @Override
    public Role updateRole(Long id, String roleName) {
        if (id == null || roleName == null || roleName.isBlank()) {
            throw new InvalidRequestException("Invalid id or role name");
        }

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(ErrorMessageUtil.ROLE_NOT_FOUND_PREFIX + id));

        existingRole.setName(roleName);
        return roleRepository.save(existingRole);
    }

    @Override
    public Role deleteRole(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Invalid id");
        }

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(ErrorMessageUtil.ROLE_NOT_FOUND_PREFIX + id));

        existingRole.setEnabled(false);
        return roleRepository.save(existingRole);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(ErrorMessageUtil.ROLE_NOT_FOUND_PREFIX + id));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}