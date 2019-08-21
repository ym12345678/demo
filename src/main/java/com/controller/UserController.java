package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
