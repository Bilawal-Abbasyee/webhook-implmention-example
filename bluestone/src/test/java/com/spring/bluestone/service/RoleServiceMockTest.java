package com.spring.bluestone.service;

import com.spring.bluestone.entity.Role;
import com.spring.bluestone.repo.RoleRepository;
import com.spring.bluestone.service.impl.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

class RoleServiceMockTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoleById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role(1L, "ADMIN", true)));
        assertNotNull(roleService.getRoleById(1L));
    }

    @Test
    void testSaveRole() {
        List<String> roleNames = List.of("TestRole1", "TestRole2");

        for (String roleName : roleNames) {
            Role savedRole = new Role();
            savedRole.setName(roleName);
            when(roleRepository.save(argThat(role -> role != null && roleName.equals(role.getName()))))
                    .thenReturn(savedRole);
        }

        List<Role> result = roleService.saveRole(roleNames);

        assertEquals(2, result.size());
        assertEquals("TestRole1", result.get(0).getName());
        assertEquals("TestRole2", result.get(1).getName());
    }


}
