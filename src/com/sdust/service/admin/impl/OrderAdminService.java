package com.sdust.service.admin.impl;

import com.sdust.dao.IOrderDao;
import com.sdust.dao.IOrderItemDao;
import com.sdust.dao.IUserDao;
import com.sdust.entity.OrderItem;
import com.sdust.entity.User;
import com.sdust.service.admin.IOrderAdminService;
import com.sdust.util.StringUtil;
import com.sdust.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderAdminService implements IOrderAdminService{
    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IOrderItemDao itemDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public PageBean<OrderItem> findItemByIndex(String name, int page, int state) {   //state表示订单状态，-1表示全部，0/1/2/3/4表示四种状态
        PageBean<OrderItem> pageInfo=new PageBean<>();
        pageInfo.setCurrentPage(page);
        if(state<-1||state>4)
            throw new RuntimeException("订单状态有误");

        try {
            int count;
            switch (state){
                case -1:
                    count=orderDao.countByName(name);
                    break;
                case 0:
                    count=orderDao.countByName0(name);
                    break;
                case 1:
                    count=orderDao.countByName1(name);
                    break;
                case 2:
                    count=orderDao.countByName2(name);
                    break;
                case 3:
                    count=orderDao.countByName3(name);
                    break;
                case 4:
                    count=orderDao.countByName4(name);
                    break;
                default:throw new RuntimeException("订单状态有误");
            }
            pageInfo.setCount(count);
            int totalPage;
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
            List<OrderItem> items;
            switch (state){
                case -1:
                    items=itemDao.findByIndex(map);
                    break;
                case 0:
                    items=itemDao.findByIndex0(map);
                    break;
                case 1:
                    items=itemDao.findByIndex1(map);
                    break;
                case 2:
                    items=itemDao.findByIndex2(map);
                    break;
                case 3:
                    items=itemDao.findByIndex3(map);
                    break;
                case 4:
                    items=itemDao.findByIndex4(map);
                    break;
                default:throw new RuntimeException("订单状态有误");
            }

            pageInfo.setPageInfos(items);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return pageInfo;

    }

    @Override
    public List<OrderItem> findByNum(String orderNum) {
        List<OrderItem> item;
        try {
            item=itemDao.findByName(orderNum);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        if(item==null)
            throw new RuntimeException("订单号有误");
        return item;
    }

    @Override
    public void deleteByNum(String orderNum){
        try {
            List<OrderItem> items=this.findByNum(orderNum);
            itemDao.delete(items.get(0));
            orderDao.delete(items.get(0).getOrder());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
