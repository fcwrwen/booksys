package com.sdust.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdust.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sdust.entity.User;
import com.sdust.service.ILoginService;
import com.sdust.vo.JsonBean;

import java.util.Map;

@Controller
public class LoginController {
	@Autowired
	private ILoginService loginService;
	@Autowired
    private IUserDao userDao;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody JsonBean login(@RequestParam("userName") String username, @RequestParam("password") String pwd,
                                        HttpSession session, HttpServletResponse response, HttpServletRequest request){
		JsonBean bean=new JsonBean(); 
		try {
			loginService.login(username, pwd);
			//登录成功，将用户名存放到session对象中
			session.setAttribute("loginname", username);
			String sessionId=session.getId();
			Cookie cookie=new Cookie("JSESSIONID", sessionId);
			cookie.setMaxAge(1800);

			//清空密码错误输入重试次数
			Cookie tryCookie=new Cookie(username,"");
			tryCookie.setMaxAge(0);

            response.addCookie(tryCookie);
            response.addCookie(cookie);

			bean.setCode(1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
			//三次密码错误锁定账号
			if(bean.getMsg()=="密码错误"){
			    User user=null;
                int tryTimes=0;
			    Cookie[] cookies=request.getCookies();
			    Cookie cookie = null;
                for (Cookie c : cookies ) {
                    if(c.getName().equals(username)) {
                        cookie=c;
                        break;
                    }
                }
                if(cookie==null) {
                    cookie = new Cookie(username, "1");
                    tryTimes = 1;
                }
                else{
                    tryTimes=Integer.parseInt(cookie.getValue())+1;
                    cookie.setValue(String.valueOf(tryTimes));
                    try {
                        //超过三次尝试，锁定账号
                        if(tryTimes>=3){
                            user=userDao.findByName(username);
                            if(user.getIsLocked()==0) {
                                loginService.lock(user);
                                cookie.setValue("");
                                cookie.setMaxAge(0);
                            }
                        }
                    } catch (Exception e1) {
                        throw e1;
                    }

                }
                response.addCookie(cookie);
                String msg=bean.getMsg()+",还可尝试"+(3-tryTimes)+"次";
                bean.setMsg(msg);
            }
		}
		return bean;	
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody JsonBean register(User user){
		JsonBean bean=new JsonBean();
		try {
			loginService.register(user);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/check")
	public @ResponseBody JsonBean checkUser(String userName){
		JsonBean bean=new JsonBean();
		try {
			boolean ret=loginService.userIsExist(userName);
			if(ret==true){
				bean.setCode(-1);
			}else{
				bean.setCode(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
		}
		return bean;
	}

	@RequestMapping("/logout")
    public @ResponseBody JsonBean logout(HttpSession session, HttpServletResponse response){
	    JsonBean bean=new JsonBean();

        //获取session对象中存放的用户名
        String name=(String)session.getAttribute("loginname");
        try {
            boolean login=loginService.userIsLogin(name);
            if(login){
            	session.removeAttribute("loginname");
				Cookie cookie=new Cookie("JSESSIONID", "");
				cookie.setMaxAge(0);
				response.addCookie(cookie);

				bean.setCode(1);
			}else{
                bean.setCode(-1);
			}

        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

}
