import Router from 'vue-router'
import Vue from 'vue'
import LoginRegisterForm from '@/components/LoginRegister/LoginRegisterForm.vue'

import SidebarMenuItem from '@/components/AdminPage/SidebarMenuItem.vue'
import getAllUser from '@/views/getAllUser.vue'
import addUser from '@/views/addUser.vue'
import home from '@/views/home.vue'
import allCategory from '@/views/allCategory.vue'
import addCategory from '@/views/addCategory.vue'
import allBook from '@/views/allBook.vue'
import addBook from '@/views/addBook.vue'


import Student from '@/components/StudentPage/Student.vue'

import NotFound from '@/views/NotFound.vue'

// 创建 Vue Router 实例
const router = new Router({
    routes: [
        {   //  路由重定向
            path: '/',
            redirect: '/LoginRegisterForm'
        },
        {   //  登录和注册界面的路由
            path: '/LoginRegisterForm',
            name: 'LoginRegisterForm',
            component: LoginRegisterForm
        },
        {   //  管理员页面的路由
            path: '/Admin',
            name: 'SidebarMenuItem',
            meta: { requiresAuth: true }, // 添加这个属性来表示这个路由需要认证
            component: SidebarMenuItem,
            redirect: '/Admin/home',
            // props($route) {
            //     return { name: $route.query.name, role: $route.query.role }
            // },
            children: [
                {
                    name: 'getAllUser',
                    path: 'allUser',
                    component: getAllUser
                },
                {
                    name: 'home',
                    path: 'home',
                    component: home
                },
                {
                    name: 'addUser',
                    path: 'addUser',
                    component: addUser
                },
                {
                    name: 'allCategory',
                    path: 'allCategory',
                    component: allCategory
                },
                {
                    name: 'addCategory',
                    path: 'addCategory',
                    component: addCategory
                },
                {
                    name: 'allBook',
                    path: 'allBook',
                    component: allBook
                },
                {
                    name: 'addBook',
                    path: 'addBook',
                    component: addBook
                },
            ]
        },
        {   //  学生用户页面的路由
            path: '/Student',
            name: 'Student',
            meta: { requiresAuth: true }, // 添加这个属性来表示这个路由需要认证
            component: Student
        },
        {   //  跳到未知的页面的处理
            path: '*',
            component: NotFound
        }
    ]
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
    // 检查目标路由是否需要认证
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // 检查是否已登录
        if (!localStorage.getItem('user')) {
            // 如果未登录，重定向到登录页面
            next({
                path: '/LoginRegisterForm',
                // query: { redirect: to.fullPath } // 保存当前路由路径，以便登录后返回
            });
        } else {
            // 如果已登录，继续导航
            next();
        }
    } else {
        // 如果目标路由不需要认证，直接继续导航
        next();
    }
});

export default router