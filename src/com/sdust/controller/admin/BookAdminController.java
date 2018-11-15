package com.sdust.controller.admin;

import com.sdust.entity.Book;
import com.sdust.service.admin.IBookAdminService;
import com.sdust.vo.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class BookAdminController {
    @Autowired
    private IBookAdminService bookAdminService;

    @RequestMapping(value="/upload.do", method= RequestMethod.POST)
    public @ResponseBody JsonBean add(@RequestParam MultipartFile imgfile, Book book, HttpServletRequest request){
        JsonBean bean=new JsonBean();

        String fileName=imgfile.getOriginalFilename();		//获取文件名

        //通过相对路径对绝对路径的映射获取本机存储上传文件的文件夹的绝对路径
        String tRealPath=request.getSession().getServletContext().getRealPath("/");
        String[] paths=tRealPath.split("booksys");
        String path=paths[0]+"booksys/upload";


        File d=new File(path);
        if(!d.exists()){
            d.mkdir();
        }
        File file=new File(path,fileName);
        try {
            imgfile.transferTo(file);
        } catch (IllegalStateException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        book.setImg("image/"+fileName);					//设置文件路径

        try {
            bookAdminService.add(book);
            bean.setCode(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }
        return bean;
    }

    @RequestMapping(value="/updatebook", method = RequestMethod.POST)
    public @ResponseBody JsonBean update(@RequestParam(name="imgfile", required=false) MultipartFile imgfile, Book book, HttpServletRequest request){
        JsonBean bean=new JsonBean();

        if(imgfile==null||imgfile.isEmpty()){
            Book t=bookAdminService.findById(book.getId());
            String img=t.getImg();
            book.setImg(img);
        }else{
            String fileName=imgfile.getOriginalFilename();		//获取文件名
            //通过相对路径对绝对路径的映射获取本机存储上传文件的文件夹的绝对路径
            String tRealPath=request.getSession().getServletContext().getRealPath("/");
            String[] paths=tRealPath.split("booksys");
            String path=paths[0]+"booksys/upload";
            File d=new File(path);
            if(!d.exists()){
                d.mkdir();
            }
            File file=new File(path,fileName);
            try {
                imgfile.transferTo(file);
            } catch (IllegalStateException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            book.setImg("image/"+fileName);
        }


        try {
            bookAdminService.update(book);
            bean.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

    @RequestMapping(value="/books",method = RequestMethod.GET)
    public @ResponseBody JsonBean findBook(Integer id){
        JsonBean bean=new JsonBean();

        try {
            Book book=bookAdminService.findById(id);
            bean.setCode(1);
            bean.setMsg(book);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

    @RequestMapping(value="/books", method = RequestMethod.DELETE)
    public @ResponseBody JsonBean deleteBook(Integer id, boolean flag){
        JsonBean bean=new JsonBean();

        try {
            bookAdminService.delete(id,flag);
            bean.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }

    @RequestMapping(value="/findBooks",method = RequestMethod.GET)
    public @ResponseBody JsonBean findBooksByName(String name){
        JsonBean bean=new JsonBean();

        List<Book> books;
        try {
            books=bookAdminService.findByName(name);
            bean.setCode(1);
            bean.setMsg(books);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }

        return bean;
    }
}
