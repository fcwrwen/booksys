package com.sdust.service;

import com.sdust.entity.Book;
import com.sdust.vo.PageBean;

public interface IBookService {
	//根据页码进行分页查询，判断请求来源前台or后台
	public PageBean<Book> findByPage(Integer page, String judge);
}
