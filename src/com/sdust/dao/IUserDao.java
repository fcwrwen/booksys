package com.sdust.dao;

import com.sdust.base.IBaseDao;
import com.sdust.entity.User;

public interface IUserDao extends IBaseDao<User>{
	
	//根据用户名查询用户信息
    User findByName(String name);

	//上锁和解锁
	void changeLockState(User user);

	//根据用户名查找订单数量
    int countOrderByName(String name);
}
