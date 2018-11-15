package com.sdust.service.admin.impl;

import com.sdust.dao.IUserDao;
import com.sdust.entity.User;
import com.sdust.service.admin.IUserAdminService;
import com.sdust.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminService implements IUserAdminService {
    @Autowired
    private IUserDao userDao;

    @Override
    public void delete(String name) {
        //查找用户是否存在
        User user;
        try {
            user=this.findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        //查询订单信息
        int count=0;
        try {
            count=userDao.countOrderByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        //删除用户
        if(count==0){
            try {
                userDao.delete(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }else {
            throw new RuntimeException("请先删除此用户下的订单信息再进行操作");
        }


    }

    @Override
    public User findByName(String name) {
        if(StringUtil.isNullOrEmpty(name))
            throw new RuntimeException("用户名为空");
        User user;
        try {
            user=userDao.findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        if(user==null)
            throw new RuntimeException("用户不存在");
        return user;
    }

    @Override
    public void unlock(User user) {
        if(user.getIsLocked()==1)
            user.setIsLocked(0);
        else
            throw new RuntimeException("用户状态有误");

        try {
            userDao.changeLockState(user);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        try {
            users=userDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return users;
    }
}
