package com.spring.bluestone.service.api;

import com.spring.bluestone.dto.request.UserRequest;
import com.spring.bluestone.entity.User;

import java.util.List;

public interface IUserService {
    User saveOrUpdateUser(UserRequest req, Long id);
    User deleteUser(Long id);
    User getUserById(Long id);
    List<User> getAllUsers();
}
