package com.sdust.dao;

import java.util.List;
import java.util.Map;

import com.sdust.base.IBaseDao;
import com.sdust.entity.Book;

public interface IBookDao extends IBaseDao<Book>{
	
	//进行分页查询，索引、几条记录
	//public List<Book> findByIndex(Integer index, Integer size);
	List<Book> findByIndex(Map<String, Object> pageInfo);           //显示所有图书
	List<Book> findByIndex_front(Map<String, Object> pageInfo);     //只显示未删除的图书
	
	// 根据相关的书籍id进行查询
	List<Book> findByIds(List<String> ids);
	
	//根据id查询库存
	Integer findStockById(Integer id);

	//找一本书
	Book findById(Integer id);

	//根据图书id查找订单数量
	int countOrderByBookId(Integer id);

	//恢复删除
	void restore(Book book);

	//按书名查找
    List<Book> findByName(String bookName);

}
