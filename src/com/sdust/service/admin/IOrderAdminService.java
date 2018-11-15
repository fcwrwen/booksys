package com.sdust.service.admin;

import com.sdust.entity.OrderItem;
import com.sdust.vo.PageBean;

import java.util.List;

public interface IOrderAdminService {
    //查询订单明细
    PageBean<OrderItem> findItemByIndex(String name, int page, int state);

    //按订单号查询订单明细（单个）
    List<OrderItem> findByNum(String orderNum);

    //按订单号删除订单【涉及外键关联，慎用】
    void deleteByNum(String orderNum);
}
