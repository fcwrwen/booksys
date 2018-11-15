package com.sdust.service;

import com.sdust.entity.User;

public interface ILoginService {
	void login(String name,String pwd);		//µÇÂ¼
	void register(User user);				//×¢²á
	boolean userIsExist(String name);
	boolean userIsLogin(String name);
	void lock(User user);
}
