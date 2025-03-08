package com.linlin.mapper;

import com.linlin.pojo.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    //  链表查询所有的图书信息
    List<Book> queryAllBooks();

    List<Book> findBookBySearch(@Param("searTit") String searTit,@Param("searInput") String searInput);

    int update(Book book);

    int addBook(Book book);

    @Delete("DELETE FROM books where book_id =#{id}")
    int deleteById(Integer id);
}
