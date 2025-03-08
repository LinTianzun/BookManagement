-- 创建数据库
CREATE DATABASE IF NOT EXISTS school_library;


-- 使用数据库
USE school_library;

--用户表（users）
CREATE TABLE users (
    -- 用户ID，自增主键
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
    -- 用户名，唯一标识，不允许为空
                       username VARCHAR(50) NOT NULL UNIQUE,
    -- 密码，存储加密后的密码，不允许为空

                       password VARCHAR(255) NOT NULL,
    -- 邮箱，可为空
                       email VARCHAR(100),
    -- 用户角色，枚举类型，包括学生和管理员
                       role ENUM('STUDENT', 'ADMIN') NOT NULL,
    -- 创建时间，自动设置为当前时间
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- 更新时间，每次更新时自动更新为当前时间
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


--图书类别表（categories）
CREATE TABLE categories (
    -- 类别ID，自增主键
                            category_id INT AUTO_INCREMENT PRIMARY KEY,
    -- 类别名称，唯一标识，不允许为空
                            category_name VARCHAR(100) NOT NULL UNIQUE,
    -- 类别描述，可为空
                            description TEXT
);


--图书信息表（books）
CREATE TABLE books (
    -- 图书ID，自增主键
                       book_id INT AUTO_INCREMENT PRIMARY KEY,
    -- 图书标题，不允许为空
                       title VARCHAR(255) NOT NULL,
    -- 作者，可为空
                       author VARCHAR(100),
    -- 国际标准书号，可为空
                       isbn VARCHAR(13),
    -- 出版社，可为空
                       publisher VARCHAR(100),
    -- 出版日期，可为空
                       publish_date DATE,
    -- 类别ID，外键关联categories表
                       category_id INT,
    -- 图书数量 不可小于0
                       sum INT,
    -- 外键约束，确保类别ID在categories表中存在
                       FOREIGN KEY (category_id) REFERENCES categories(category_id)
);


--借阅记录表（borrow_records）
CREATE TABLE borrow_records (
    -- 记录ID，自增主键
                                record_id INT AUTO_INCREMENT PRIMARY KEY,
    -- 图书ID，外键关联books表
                                book_id INT,
    -- 用户ID，外键关联users表
                                user_id INT,
    -- 借阅日期，可为空
                                borrow_date DATE,
    -- 归还日期，可为空
                                return_date DATE,
    -- 外键约束，确保图书ID在books表中存在
                                FOREIGN KEY (book_id) REFERENCES books(book_id),
    -- 外键约束，确保用户ID在users表中存在
                                FOREIGN KEY (user_id) REFERENCES users(user_id)
);

--插入用户数据
INSERT INTO users (username, password, email, role) VALUES
                                                        ('stu1', '123456', 'student1@example.com', 'STUDENT'),
                                                        ('admin', '123456', 'admin1@example.com', 'ADMIN');

--  插入图书类别数据
INSERT INTO categories (category_name, description) VALUES
                                                        ('计算机', '包含计算机科学、编程语言、软件工程等领域的书籍'),
                                                        ('历史', '包含历史事件、历史人物、历史理论等领域的书籍'),
                                                        ('科幻', '包含科幻小说、科幻电影、科幻理论等领域的书籍'),
                                                        ('军事', '包含军事历史、军事理论、军事技术等领域的书籍'),
                                                        ('经济', '包含经济学理论、经济政策、金融管理等领域的书籍'),
                                                        ('文学', '包含诗歌、小说、戏剧、文学理论等领域的书籍');

--  插入图书信息
INSERT INTO books (title, author, isbn, publisher, publish_date, category_id, sum) VALUES
                                                                                       ('Vue.js实战', '张三', '9787111626975', '机械工业出版社', '2023-01-01', 1, 10),
                                                                                       ('深入理解计算机系统', '李四', '9787111626975', '电子工业出版社', '2023-03-01', 1, 15),
                                                                                       ('JavaScript高级程序设计', '王五', '9787111626975', '人民邮电出版社', '2023-02-01', 2, 5),
                                                                                       ('中国近代史', '赵六', '9787111626975', '中华书局', '2023-04-01', 2, 8),
                                                                                       ('三体', '钱七', '9787111626975', '重庆出版社', '2023-05-01', 3, 20),
                                                                                       ('围城', '孙八', '9787111626975', '人民文学出版社', '2023-06-01', 3, 12),
                                                                                       ('经济学原理', '周九', '9787111626975', '北京大学出版社', '2023-07-01', 4, 10),
                                                                                       ('金融学', '吴十', '9787111626975', '中国人民大学出版社', '2023-08-01', 4, 15),
                                                                                       ('莎士比亚全集', '郑十一', '9787111626975', '商务印书馆', '2023-09-01', 5, 5),
                                                                                       ('红楼梦', '赵十二', '9787111626975', '中华书局', '2023-10-01', 5, 8);

--  链表查询所有的图书信息
SELECT b.book_id, b.title, b.author, b.isbn, b.publisher, b.publish_date,  b.category_id,c.category_name, b.sum
FROM books b
         JOIN categories c ON b.category_id = c.category_id

SELECT * FROM books
ORDER BY book_id ASC ;