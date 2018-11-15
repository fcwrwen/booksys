package com.sdust.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sdust.entity.Book;
import com.sdust.service.IBookService;
import com.sdust.vo.JsonBean;
import com.sdust.vo.PageBean;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BookController {
	@Autowired
	private IBookService bookService;
	
	@RequestMapping(value="/books/page/{page}", method=RequestMethod.GET)
	//@PathVariable 表示从路径中取对应变量的值
	public @ResponseBody JsonBean findByPage(@PathVariable("page")Integer page, String judge){
		JsonBean bean=new JsonBean();

		try {
			PageBean<Book> infos=bookService.findByPage(page,judge);
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

}
