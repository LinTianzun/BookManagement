import axios from "axios";
import request from '../utils/request'

//  获取所有的用户数据
export function getAllUserService() {
    // return axios.get('http://localhost:8080/ssm/user/getUsers')
    return request.get('/user/getUsers')
        .then(response => {
            return response.data; // 返回响应数据
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
            throw error; // 如果有错误，抛出异常
        });
}


//  登录
export function loginService(user) {
    // return axios.post('http://localhost:8080/ssm/user/login', user)
    return request.post('/user/login', user)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            // console.error('登录失败:', error);
            // throw error; // 可以选择抛出错误，以便在调用处处理
            return Promise.reject(error)
            // return error
        });
}

//  注册
export function registerService(user) {
    // return axios.post('http://localhost:8080/ssm/user/register', user)
    return request.post('/user/register', user)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  分页查询用户信息
export function loadByPage(pageSize, currentPage) {
    return request.get(`/user/${pageSize}/${currentPage}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

// @GetMapping("searchUser/{searTit}/{searInput}/{pageSize}/{currentPage}")
//  分页模糊查询用户信息 fuzzyQueryUser
export function loadFuzzyByPage(searTit, searInput, pageSize, currentPage) {
    return request.get(`/user/searchUser/${searTit}/${searInput}/${pageSize}/${currentPage}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  根据id删除用户信息
export function deleteUser(id) {
    return request.delete(`/user/${id}`)
        .then(response => {
            return response;
        })
        .catch(error => {
            // return error
            return Promise.reject(error)
        });
}

//  更新用户数据
export function updateUserService(user) {
    // return axios.post('http://localhost:8080/ssm/user/register', user)
    return request.put('/user', user)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            return error
            return Promise.reject(error)
        });
}

