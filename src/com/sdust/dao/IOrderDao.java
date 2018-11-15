package com.sdust.dao;

import com.sdust.base.IBaseDao;
import com.sdust.entity.Order;

public interface IOrderDao extends IBaseDao<Order>{
    void changeState(Order order);

    int countByName(String userName);
    int countByName0(String userName);
    int countByName1(String userName);
    int countByName2(String userName);
    int countByName3(String userName);
    int countByName4(String userName);
}
