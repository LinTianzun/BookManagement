import axios from "axios";
import request from '../utils/request'

//  分页查询书籍信息
export function loadByPage(pageSize, currentPage) {
    return request.get(`/book/${pageSize}/${currentPage}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  获取所有的图书类别数据
export function getAllCategoryService() {
    return request.get('/category/allCategory')
        .then(response => {
            return response.data; // 返回响应数据
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            throw error; // 如果有错误，抛出异常
        });
}
//  分页模糊查询图书信息 
// http://localhost:8080/ssm/book/searchBook/title/J/3/1
export function fuzzyQueryBook(searTit, searInput, pageSize, currentPage) {
    return request.get(`/book/searchBook/${searTit}/${searInput}/${pageSize}/${currentPage}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  更新图书数据
export function updateBookService(book) {
    return request.put('/book', book)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            return error
            return Promise.reject(error)
        });
}

//  添加图书
export function addBookService(book) {
    return request.post('/book/addBook', book)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  根据id删除图书类别信息
export function deleteBook(id) {
    return request.delete(`/book/${id}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}
