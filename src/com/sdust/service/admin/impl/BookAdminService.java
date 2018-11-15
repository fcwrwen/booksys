package com.sdust.service.admin.impl;

import com.sdust.dao.IBookDao;
import com.sdust.entity.Book;
import com.sdust.service.admin.IBookAdminService;
import com.sdust.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAdminService implements IBookAdminService{
    @Autowired
    private IBookDao bookDao;

    @Override
    public void add(Book book) {
        // TODO Auto-generated method stub
        if(book==null)
            throw new RuntimeException("图书信息不能为空");
        if(book.getStock()<1)
            throw new RuntimeException("请输入正确的库存");
        if(book.getPrice()<0)
            throw new RuntimeException("请输入正确的价格");
        try {
            bookDao.add(book);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void update(Book book){
        if(book==null)
            throw new RuntimeException("图书信息不能为空");
        if(StringUtil.isNullOrEmpty(book.getBookName()))
            throw new RuntimeException("书名不能为空");
        if(book.getPrice()<0)
            throw new RuntimeException("请输入正确的价格");
        if(book.getStock()<1)
            throw new RuntimeException("请输入正确的库存");
        if(StringUtil.isNullOrEmpty(book.getImg())||book.getImg()=="image/")
            throw new RuntimeException("文件上传错误");
        try {
            bookDao.update(book);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Book findById(Integer id) {
        if(id<1)
            throw new RuntimeException("图书id有误");
        try {
            return bookDao.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void delete(Integer id, boolean flag) {
        //判断图书是否存在
        Book book;
        try {
            book=this.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        if(book==null)
            throw new RuntimeException("不存在id为"+id+"的图书");

        if(flag){   //删除图书

            //判断是否已删除
            if(book.getIsDeleted()!=0)
                throw new RuntimeException("图书已删除");


            //查询购买信息
            int count=0;
            try {
                count=bookDao.countOrderByBookId(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

            //判断是否有购买信息，没有则删除
            if(count==0){
                try {
                    bookDao.delete(book);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }else{
                throw new RuntimeException("此书已有购买信息，请先删除相应订单后再进行操作");
            }

        }else{      //恢复图书
            //判断是否未删除
            if(book.getIsDeleted()==0)
                throw new RuntimeException("图书未删除");

            try {
                bookDao.restore(book);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

    }

    @Override
    public List<Book> findByName(String name) {
        if(StringUtil.isNullOrEmpty(name))
            throw new RuntimeException("请输入书名");
        List<Book> books;
        try {
            books=bookDao.findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return books;
    }
}
