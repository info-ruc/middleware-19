package com.printer.core.service;

import com.printer.core.entity.User;

public interface UserService {
    User getUserByName(String name);
    User addUser(User user);
}
