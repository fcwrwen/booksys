package com.sdust.service;

import java.util.List;

import com.sdust.entity.Book;

public interface ICartService {
	public String addCart(String[] bookIds,  String cartId);
	public List<Book> findCartInfo(String ids);	//查询购物车中显示的数据
	public String updateCart(String[] ids); //更新购物车
}
