package com.sdust.controller.admin;

import com.sdust.dao.IUserDao;
import com.sdust.entity.Order;
import com.sdust.entity.OrderItem;
import com.sdust.service.ILoginService;
import com.sdust.service.IOrderService;
import com.sdust.service.admin.IOrderAdminService;
import com.sdust.vo.JsonBean;
import com.sdust.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderAdminController {
    @Autowired
    private IOrderAdminService orderAdminService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ILoginService loginService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public @ResponseBody JsonBean orderDetail(String orderNum){
        JsonBean bean=new JsonBean();

        try {
            List<OrderItem> items=orderAdminService.findByNum(orderNum);
            bean.setCode(1);
            bean.setMsg(items);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

    //使用前端接口
    //public @ResponseBody JsonBean updateState (Order order){}

    @RequestMapping(value="/adminorders/page/{page}", method= RequestMethod.GET)
    public @ResponseBody JsonBean findOrderInfo(@PathVariable("page") int page, String name, int state){
        JsonBean bean=new JsonBean();

        try {
            //判断用户是否存在
            boolean judge=loginService.userIsExist(name);
            if(!judge)
                throw new RuntimeException("用户不存在");
            PageBean<OrderItem> pageBean=orderAdminService.findItemByIndex(name, page, state);
            bean.setCode(1);
            bean.setMsg(pageBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }
        return bean;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.DELETE)
    public @ResponseBody JsonBean deleteOrder(String orderNum){
        JsonBean bean=new JsonBean();

        try {
            orderAdminService.deleteByNum(orderNum);
            bean.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }
}
