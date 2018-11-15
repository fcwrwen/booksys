package com.sdust.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdust.dao.IBookDao;
import com.sdust.entity.Book;
import com.sdust.service.ICartService;
import com.sdust.util.StringUtil;

@Service
public class CartService implements ICartService{
	@Autowired
	private IBookDao bookDao;
	
	@Override
	public String addCart(String[] bookIds,  String cartId){

	List<String> list = new ArrayList<>();
	if(!StringUtil.isNullOrEmpty(cartId)){
//		String[] split = cartId.split(",");
        String[] split=cartId.split("#");
		for(String v : split){
			list.add(v);
		}
	}
	if(bookIds == null || bookIds.length == 0) {
		throw new RuntimeException("没有选择相关的输出");
	}
	// 1,2,3
	String info = "";
	for(int i = 0; i < bookIds.length; i++){
		if(!list.contains(bookIds[i])){
			list.add(bookIds[i]);
		}
	}

	for(String v : list){
//        info += v + ",";
        info += v + "#";
	}
	info = info.substring(0, info.length() - 1); //去掉最后的","

	return info;

	}

	@Override
	public List<Book> findCartInfo(String ids) {
		// TODO Auto-generated method stub
		if(StringUtil.isNullOrEmpty(ids))
			throw new RuntimeException("购物车为空");
		try {
			List<String> list=new ArrayList<>();
//			String[] split=ids.split(",");
			String[] split=ids.split("#");
			for(String info : split){
				list.add(info);
			}
			List<Book> books=bookDao.findByIds(list);
			return books;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public String updateCart(String[] ids){

        String info = "";
        for(String v : ids)
            //info+=v+",";
            info += v+"#";
        if(!info.isEmpty())
            info = info.substring(0, info.length() - 1); //去掉最后的","

        return info;
    }
}
