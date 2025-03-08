package com.linlin.service;

import com.linlin.pojo.Book;
import com.linlin.utils.R;

import java.util.List;

public interface BookSrevice {
    List<Book> queryAllBooks();

    R page(int pageSize, int currentPage);

    R findBookBySearch(String searTit, String searInput, int pageSize, int currentPage);

    R update(Book book);

    R addBook(Book book);

    R remove(Integer id);
}
