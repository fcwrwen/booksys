package com.sdust.service;

import com.sdust.entity.Order;
import com.sdust.entity.OrderItem;
import com.sdust.vo.PageBean;

import javax.servlet.http.HttpSession;

public interface IOrderService {
	//添加订单信息
    Order addOrderInfo(String[] ids, String[] nums, Double price, String name);
	//添加订单明细
    void addOrderItems(String[] ids, String[] nums, Order order);
	//根据页码查询订单明细(按订单数据分页)
    PageBean<OrderItem> findItemByIndex(String name, int page);
	//改变订单状态
	void changeState(Order order);
}
