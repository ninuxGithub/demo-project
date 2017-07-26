package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.User;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/users")
@RestController
public class UserController {

    private static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @ApiOperation(value = "获取用户列表")
    @GetMapping(value = "/")
    public List<User> listUser() {
        List<User> user = new ArrayList<>(users.values());
        if (log.isDebugEnabled()) {
            log.debug(users.toString());
        }
        return user;
    }

    @ApiOperation(value = "创建新用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PostMapping(value = "/")
    public String saveUser(@RequestBody User user) {
        users.put(user.getId(), user);
        if (log.isDebugEnabled()) {
            log.debug(users.toString());
        }
        return "success";
    }

    @ApiOperation(value = "获取用户信息", notes = "根据id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID",paramType = "path",required = true, dataType = "Long")
    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        if (log.isDebugEnabled()) {
            log.debug(users.toString());
        }
        return users.get(id);
    }

    @ApiOperation(value = "更新用户信息", notes = "根据ID更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID",paramType = "path", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")
    })
    @PutMapping(value = "/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setAge(user.getAge());
        u.setName(user.getName());
        users.put(id, u);
        if (log.isDebugEnabled()) {
            log.debug(users.toString());
        }
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID",paramType = "path", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        if (log.isDebugEnabled()) {
            log.debug(users.toString());
        }
        return "success";
    }
}
