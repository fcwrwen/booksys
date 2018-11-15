package com.sdust.controller.admin;

import com.sdust.dao.IUserDao;
import com.sdust.entity.User;
import com.sdust.service.ILoginService;
import com.sdust.service.admin.IUserAdminService;
import com.sdust.vo.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserAdminController {
    @Autowired
    private IUserAdminService userAdminService;

    @Autowired
    private ILoginService loginService;

    @RequestMapping(value="/user", method = RequestMethod.DELETE)
    public @ResponseBody JsonBean deleteUser(String userName){
        JsonBean bean=new JsonBean();

        try {
            userAdminService.delete(userName);
            bean.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setMsg(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody JsonBean findUser(String userName){
        JsonBean bean=new JsonBean();

        try {
            User user=userAdminService.findByName(userName);
            bean.setCode(1);
            bean.setMsg(user);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public @ResponseBody JsonBean changeUserLockState(String userName, boolean isLocked){
        JsonBean bean=new JsonBean();

        //查找用户
        try {
            User user=userAdminService.findByName(userName);
            if(isLocked)    //isLocked==true，账户属于锁定状态，解锁
                userAdminService.unlock(user);
            else            //isLocked==false，账户未锁定，锁定
                loginService.lock(user);
            bean.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public @ResponseBody JsonBean findAll(){
        JsonBean bean=new JsonBean();

        try {
            List<User> users=userAdminService.findAll();
            bean.setCode(1);
            bean.setMsg(users);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }
}
