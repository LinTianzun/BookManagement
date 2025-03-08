package com.linlin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Book类代表数据库中的图书信息。
 * 它包含了图书的基本信息，如图书ID、标题、作者、ISBN、出版社、出版日期、所属类别ID以及图书的借阅状态。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    /**
     * 图书ID，数据库中的主键。
     */
    private Integer bookId;

    /**
     * 图书标题，唯一标识图书。
     */
    private String title;

    /**
     * 作者，图书的作者信息。
     */
    private String author;

    /**
     * ISBN，国际标准书号，用于唯一标识图书。
     */
    private String isbn;

    /**
     * 出版社，图书的出版机构。
     */
    private String publisher;

    /**
     * 出版日期，图书的出版时间。
     */
    private Date publishDate;

    /**
     * 类别ID，外键关联图书类别表，表示图书所属的类别。
     */
    private Integer categoryId;

    /**
     * 类别名称，表示图书所属的类别。
     */
    private String categoryName;

    /**
     * 可用状态，表示图书是否可借阅。
     */
    private Integer sum;
}