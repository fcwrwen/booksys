package com.sdust.service.admin;

import com.sdust.entity.User;

import java.util.List;

public interface IUserAdminService {
    //删除用户
    void delete(String name);

    //查询用户
    User findByName(String name);

    //用户解锁
    void unlock(User user);

    //查找所有用户
    List<User> findAll();
}
