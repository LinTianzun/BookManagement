-- �������ݿ�
CREATE DATABASE IF NOT EXISTS school_library;


-- ʹ�����ݿ�
USE school_library;

--�û���users��
CREATE TABLE users (
    -- �û�ID����������
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
    -- �û�����Ψһ��ʶ��������Ϊ��
                       username VARCHAR(50) NOT NULL UNIQUE,
    -- ���룬�洢���ܺ�����룬������Ϊ��

                       password VARCHAR(255) NOT NULL,
    -- ���䣬��Ϊ��
                       email VARCHAR(100),
    -- �û���ɫ��ö�����ͣ�����ѧ���͹���Ա
                       role ENUM('STUDENT', 'ADMIN') NOT NULL,
    -- ����ʱ�䣬�Զ�����Ϊ��ǰʱ��
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- ����ʱ�䣬ÿ�θ���ʱ�Զ�����Ϊ��ǰʱ��
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


--ͼ������categories��
CREATE TABLE categories (
    -- ���ID����������
                            category_id INT AUTO_INCREMENT PRIMARY KEY,
    -- ������ƣ�Ψһ��ʶ��������Ϊ��
                            category_name VARCHAR(100) NOT NULL UNIQUE,
    -- �����������Ϊ��
                            description TEXT
);


--ͼ����Ϣ��books��
CREATE TABLE books (
    -- ͼ��ID����������
                       book_id INT AUTO_INCREMENT PRIMARY KEY,
    -- ͼ����⣬������Ϊ��
                       title VARCHAR(255) NOT NULL,
    -- ���ߣ���Ϊ��
                       author VARCHAR(100),
    -- ���ʱ�׼��ţ���Ϊ��
                       isbn VARCHAR(13),
    -- �����磬��Ϊ��
                       publisher VARCHAR(100),
    -- �������ڣ���Ϊ��
                       publish_date DATE,
    -- ���ID���������categories��
                       category_id INT,
    -- ͼ������ ����С��0
                       sum INT,
    -- ���Լ����ȷ�����ID��categories���д���
                       FOREIGN KEY (category_id) REFERENCES categories(category_id)
);


--���ļ�¼��borrow_records��
CREATE TABLE borrow_records (
    -- ��¼ID����������
                                record_id INT AUTO_INCREMENT PRIMARY KEY,
    -- ͼ��ID���������books��
                                book_id INT,
    -- �û�ID���������users��
                                user_id INT,
    -- �������ڣ���Ϊ��
                                borrow_date DATE,
    -- �黹���ڣ���Ϊ��
                                return_date DATE,
    -- ���Լ����ȷ��ͼ��ID��books���д���
                                FOREIGN KEY (book_id) REFERENCES books(book_id),
    -- ���Լ����ȷ���û�ID��users���д���
                                FOREIGN KEY (user_id) REFERENCES users(user_id)
);

--�����û�����
INSERT INTO users (username, password, email, role) VALUES
                                                        ('stu1', '123456', 'student1@example.com', 'STUDENT'),
                                                        ('admin', '123456', 'admin1@example.com', 'ADMIN');

--  ����ͼ���������
INSERT INTO categories (category_name, description) VALUES
                                                        ('�����', '�����������ѧ��������ԡ�������̵�������鼮'),
                                                        ('��ʷ', '������ʷ�¼�����ʷ�����ʷ���۵�������鼮'),
                                                        ('�ƻ�', '�����ƻ�С˵���ƻõ�Ӱ���ƻ����۵�������鼮'),
                                                        ('����', '����������ʷ���������ۡ����¼�����������鼮'),
                                                        ('����', '��������ѧ���ۡ��������ߡ����ڹ����������鼮'),
                                                        ('��ѧ', '����ʫ�衢С˵��Ϸ�硢��ѧ���۵�������鼮');

--  ����ͼ����Ϣ
INSERT INTO books (title, author, isbn, publisher, publish_date, category_id, sum) VALUES
                                                                                       ('Vue.jsʵս', '����', '9787111626975', '��е��ҵ������', '2023-01-01', 1, 10),
                                                                                       ('�����������ϵͳ', '����', '9787111626975', '���ӹ�ҵ������', '2023-03-01', 1, 15),
                                                                                       ('JavaScript�߼��������', '����', '9787111626975', '�����ʵ������', '2023-02-01', 2, 5),
                                                                                       ('�й�����ʷ', '����', '9787111626975', '�л����', '2023-04-01', 2, 8),
                                                                                       ('����', 'Ǯ��', '9787111626975', '���������', '2023-05-01', 3, 20),
                                                                                       ('Χ��', '���', '9787111626975', '������ѧ������', '2023-06-01', 3, 12),
                                                                                       ('����ѧԭ��', '�ܾ�', '9787111626975', '������ѧ������', '2023-07-01', 4, 10),
                                                                                       ('����ѧ', '��ʮ', '9787111626975', '�й������ѧ������', '2023-08-01', 4, 15),
                                                                                       ('ɯʿ����ȫ��', '֣ʮһ', '9787111626975', '����ӡ���', '2023-09-01', 5, 5),
                                                                                       ('��¥��', '��ʮ��', '9787111626975', '�л����', '2023-10-01', 5, 8);

--  �����ѯ���е�ͼ����Ϣ
SELECT b.book_id, b.title, b.author, b.isbn, b.publisher, b.publish_date,  b.category_id,c.category_name, b.sum
FROM books b
         JOIN categories c ON b.category_id = c.category_id

SELECT * FROM books
ORDER BY book_id ASC ;