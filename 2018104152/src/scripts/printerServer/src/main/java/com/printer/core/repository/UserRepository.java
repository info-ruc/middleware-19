package com.printer.core.repository;

import com.printer.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 不需要实现，简直神奇！
    User findByUserName(String userName);
}
