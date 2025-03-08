package com.linlin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * BorrowRecord类代表数据库中的借阅记录信息。
 * 它包含了借阅记录的基本信息，如记录ID、图书ID、用户ID、借阅日期和归还日期。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecord {
    /**
     * 记录ID，数据库中的主键。
     */
    private Integer recordId;

    /**
     * 图书ID，外键关联图书表，表示借阅的图书。
     */
    private Integer bookId;

    /**
     * 用户ID，外键关联用户表，表示借阅图书的用户。
     */
    private Integer userId;

    /**
     * 借阅日期，用户借阅图书的日期。
     */
    private Date borrowDate;

    /**
     * 归还日期，用户归还图书的日期。
     */
    private Date returnDate;
}