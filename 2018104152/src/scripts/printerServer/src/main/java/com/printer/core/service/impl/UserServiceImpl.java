package com.printer.core.service.impl;

import com.printer.core.entity.User;
import com.printer.core.repository.UserRepository;
import com.printer.core.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public User getUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
