package com.sdust.service.admin;

import com.sdust.entity.Book;

import java.util.List;

public interface IBookAdminService {
    void add(Book book);
    void update(Book book);
    Book findById(Integer id);
    void delete(Integer id, boolean flag);
    List<Book> findByName(String name);
}
