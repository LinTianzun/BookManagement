import axios from "axios";
import request from '../utils/request'

//  分页查询书籍类别信息
export function loadByPage(pageSize, currentPage) {
    return request.get(`/category/${pageSize}/${currentPage}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

// http://localhost:8080/ssm/category/searchCate/文/3/1
//  分页模糊查询图书类别信息 fuzzyQueryUser
export function fuzzyQueryCategory(searInput, pageSize, currentPage) {
    return request.get(`/category/searchCate/${searInput}/${pageSize}/${currentPage}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  更新图书类别数据
export function updateCategoryService(category) {
    return request.put('/category', category)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            return error
            return Promise.reject(error)
        });
}

//  添加图书类别
export function addCategoryService(category) {
    return request.post('/category/add', category)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  根据id删除图书类别信息
export function deleteCategory(id) {
    return request.delete(`/category/${id}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}
