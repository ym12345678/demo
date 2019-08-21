package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月21日
 */
@RestController(value = "api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "findAll")
    public Object findAll(HttpServletRequest request, HttpServletResponse response){
       return userService.findAll();
    }
}
