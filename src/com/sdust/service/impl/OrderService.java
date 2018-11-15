package com.sdust.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sdust.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdust.dao.IBookDao;
import com.sdust.dao.IOrderDao;
import com.sdust.dao.IOrderItemDao;
import com.sdust.dao.IUserDao;
import com.sdust.entity.Book;
import com.sdust.entity.Order;
import com.sdust.entity.OrderItem;
import com.sdust.entity.User;
import com.sdust.service.IOrderService;
import com.sdust.util.StringUtil;
import com.sdust.vo.PageBean;

import javax.servlet.http.HttpSession;

@Service
public class OrderService implements IOrderService{
	
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IOrderItemDao itemDao;
	@Autowired
    private IBookDao bookDao;


	@Override
	public Order addOrderInfo(String[] ids, String[] nums, Double price, String username) {		//添加订单
		Order order=new Order();
		order.setTotalPrice(price);
		order.setState(0);
		order.setCreateDate(new Date());
		order.setOrderNum(UUID.randomUUID().toString());	//生成唯一ID作为订单编号
		try {
			User user=userDao.findByName(username);
			order.setBuyer(user);
			orderDao.add(order);
			this.addOrderItems(ids, nums, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return order;
	}

	@Override
	public void addOrderItems(String[] ids, String[] nums, Order order) {	//添加订单明细
		if(ids==null||ids.length==0)
			throw new RuntimeException("图书数据不存在");
		if(nums==null||nums.length==0)
			throw new RuntimeException("购买数量有误");
		if(order==null)
			throw new RuntimeException("订单数据不存在");
		try {
			for(int i=0;i<ids.length;i++){
				OrderItem item=new OrderItem();
				
				int id=Integer.parseInt(ids[i]), num=Integer.parseInt(nums[i]);

				//设置库存
				Book book=new Book();
				book.setId(id);
				try {
					int newStock=bookDao.findStockById(id)-num;
					if(newStock<0)
					    throw new RuntimeException("库存不足");
					book.setStock(newStock);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}

				item.setBook(book);
				item.setOrder(order);
				item.setNum(num);
				itemDao.add(item);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public PageBean<OrderItem> findItemByIndex(String name, int page) {
		// TODO Auto-generated method stub
		if(StringUtil.isNullOrEmpty(name))
			throw new RuntimeException("请登录后查看订单");
		PageBean<OrderItem> pageInfo=new PageBean<>();
		pageInfo.setCurrentPage(page);
		
		try {
			int count=orderDao.countByName(name);
			pageInfo.setCount(count);
			int totalPage=0;
			if(count%pageInfo.getSize()==0){
				totalPage=count/pageInfo.getSize();
			}else{
				totalPage=count/pageInfo.getSize()+1;
			}
			pageInfo.setTotalPage(totalPage);
			
			Map<String, Object> map=new HashMap<>();
			map.put("index", (page-1)*pageInfo.getSize());
			map.put("size", pageInfo.getSize());
			map.put("name", name);
			List<OrderItem> items=itemDao.findByIndex(map);
			
			pageInfo.setPageInfos(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return pageInfo;
	}

	@Override
	public void changeState(Order order) {
	    Integer newState=order.getState()+1;
	    if(newState==5) {
			newState = 0;             //由4状态切换至0状态，遗留用于测试
		}
		order.setState(newState);
        try {
            orderDao.changeState(order);
        } catch (Exception e) {
            throw e;
        }
    }


}
