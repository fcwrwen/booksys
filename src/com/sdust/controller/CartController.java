package com.sdust.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sdust.entity.Book;
import com.sdust.service.ICartService;
import com.sdust.vo.JsonBean;

@Controller
public class CartController {

	@Autowired
	private ICartService cartService;
	
	//向购物车添加数据，将相关数据存至cookie
	@RequestMapping(value="/carts",method=RequestMethod.POST)
	//@CookieValue读指定的Cookie
	public @ResponseBody JsonBean addCart(String[] bookIds, HttpServletResponse response, @CookieValue(value="cartids", defaultValue="") String cartId){
		JsonBean bean=new JsonBean();
		try {
			String info = cartService.addCart(bookIds, cartId);
			Cookie cookie=new Cookie("cartids",info);
			cookie.setMaxAge(30*24*3600);
			response.addCookie(cookie);

			bean.setCode(1);
		} catch (Exception e) {
			// TODO: handle exception
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	
	@RequestMapping(value="/carts",method=RequestMethod.GET)
	public @ResponseBody JsonBean carts(@CookieValue(value="cartids", defaultValue="") String cartId){
		JsonBean bean=new JsonBean();
		try {
			List<Book> infos=cartService.findCartInfo(cartId);
			bean.setCode(1);
			bean.setMsg(infos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}

    //更新购物车信息
	@RequestMapping(value="/carts", method = RequestMethod.PUT)
    public @ResponseBody JsonBean updateCarts(String[] ids, HttpServletResponse response, @CookieValue(value="cartids", defaultValue="") String cartId){
	    JsonBean bean=new JsonBean();
        try {
            String info = cartService.updateCart(ids);
            Cookie cookie=new Cookie("cartids",info);
            //cookie.setPath("/");        //如果put方法的url为"carts/?"，则此时创建的cookie路径为"/carts"，无法覆盖旧cookie，需要重新设置路径
            cookie.setMaxAge(30*24*3600);
            response.addCookie(cookie);

            bean.setCode(1);
        } catch (Exception e) {
            // TODO: handle exception
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }
        return bean;
    }
}
