package com.dao;

import com.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月21日
 */
public interface UserDao extends JpaRepository<User,String> {

    List<User> findAll();
}
