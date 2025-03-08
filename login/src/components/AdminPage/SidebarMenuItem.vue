<template>
    <div id="adBody" :class="{'isSwitch':isSwitch}">
        <div class="container">
            <div class="sidebar" :class="{'active':isSidActive}">
                <!-- 侧边栏头部 -->
                <div class="head">
                    <div class="user-img">
                        <img :src="userImg" alt="">
                    </div>
                    <div class="user-details">
                        <p class="title">{{routeRole}}</p>
                        <p class="name">{{routeName}}</p>
                    </div>
                    <div class="user-logo">
                        <i class="iconfont icon-logo-apple-ar"></i>
                    </div>
                </div>

                <!-- 尝试 -->
                <span class="ssp"></span>
                <span class="ssp"></span>
                <span class="ssp"></span>

                <!-- 侧边栏内容 -->
                <div class="nav">
                    <div class="menu">
                        <p class="title">Main</p>
                        <ul>
                            <li :class="{ active: activeMenu === 'home' }" @click="toggleMenu('home')">
                                <a @click="gotoHome">
                                    <i class="iconfont icon-home"></i>
                                    <span class="text">主页</span>
                                </a>
                            </li>
                            <!-- 其他菜单项 -->

                            <!-- 用户管理菜单项 -->
                            <li :class="{ active: activeMenu === 'userManagement' }">
                                <a @click="toggleMenu('userManagement')">
                                    <i class="iconfont icon-team"></i>
                                    <span class="text">用户管理</span>
                                    <i class="iconfont icon-down arrow"></i>
                                </a>
                                <!-- 子菜单 -->
                                <el-collapse-transition>
                                    <ul v-show="activeMenu === 'userManagement'" class="sub-menu">
                                        <li>
                                            <span class="text sub-menu-title">用户管理</span>
                                        </li>
                                        <li @click="allUsers">
                                            <a>
                                                <span class="text">用户列表</span>
                                            </a>
                                        </li>
                                        <li @click="addUser">
                                            <a>
                                                <span class="text">用户添加</span>
                                            </a>
                                        </li>
                                    </ul>
                                </el-collapse-transition>
                            </li>

                            <!-- 图书分类管理菜单项 -->
                            <li :class="{ active: activeMenu === 'bookCategoryManagement' }">
                                <a @click="toggleMenu('bookCategoryManagement')">
                                    <i class="iconfont icon-library1"></i>
                                    <span class="text">图书分类管理</span>
                                    <i class="iconfont icon-down arrow"></i>
                                </a>
                                <!-- 子菜单 -->
                                <el-collapse-transition>
                                    <ul v-show="activeMenu === 'bookCategoryManagement'" class="sub-menu">
                                        <li>
                                            <span class="text sub-menu-title">图书分类管理</span>
                                        </li>
                                        <li @click="allCategory">
                                            <a>
                                                <span class="text">分类总览</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a @click="addCategory">
                                                <span class="text">新增分类</span>
                                            </a>
                                        </li>
                                    </ul>
                                </el-collapse-transition>
                            </li>

                            <!-- 图书管理菜单项 -->
                            <li :class="{ active: activeMenu === 'bookManagement' }">
                                <a @click="toggleMenu('bookManagement')">
                                    <i class="iconfont icon-book-open"></i>
                                    <span class="text">图书管理</span>
                                    <i class="iconfont icon-down arrow"></i>
                                </a>
                                <!-- 子菜单 -->
                                <el-collapse-transition>
                                    <ul v-show="activeMenu === 'bookManagement'" class="sub-menu">
                                        <li>
                                            <span class="text sub-menu-title">图书管理</span>
                                        </li>
                                        <li @click="allBook">
                                            <a>
                                                <span class="text">图书列表</span>
                                            </a>
                                        </li>
                                        <li @click="addBook">
                                            <a>
                                                <span class="text">图书上架</span>
                                            </a>
                                        </li>
                                    </ul>
                                </el-collapse-transition>
                            </li>

                            <!-- 借阅管理菜单项 -->
                            <li :class="{ active: activeMenu === 'borrowManagement' }">
                                <a @click="toggleMenu('borrowManagement')">
                                    <i class="iconfont icon-library"></i>
                                    <span class="text">借阅管理</span>
                                    <i class="iconfont icon-down arrow"></i>
                                </a>
                                <!-- 子菜单 -->
                                <el-collapse-transition>
                                    <ul v-show="activeMenu === 'borrowManagement' " class="sub-menu">
                                        <li>
                                            <span class="text sub-menu-title">借阅管理</span>
                                        </li>
                                        <li>
                                            <a>
                                                <span class="text">借阅总览</span>
                                            </a>
                                        </li>
                                    </ul>
                                </el-collapse-transition>
                            </li>

                        </ul>
                    </div>

                    <!-- 更多菜单项 -->
                    <div class="menu">
                        <p class="title">Settings</p>
                        <ul>
                            <li :class="{ active: activeMenu === 'settings' }">
                                <a @click="toggleMenu('settings')">
                                    <i class="iconfont icon-setting"></i>
                                    <span class="text">设置</span>
                                </a>
                            </li>
                            <!-- 其他菜单项 -->
                        </ul>
                    </div>
                </div>

                <!-- 侧边栏底部 -->
                <div class="menu">
                    <p class="title">Account</p>
                    <ul>
                        <li>
                            <a>
                                <i class="iconfont icon-info"></i>
                                <span class="text">帮助</span>
                            </a>
                        </li>
                        <li class="switchLi">
                            <a>
                                <i class="iconfont icon-outline-moon iconSwitch"></i>
                                <i class="iconfont icon-outline-sun iconSwitch"></i>
                                <span class="text">切换</span>
                                <div class="toggle-switch">
                                    <span class="switch" @click="toggleTheme()"></span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a @click="logout">
                                <i class="iconfont icon-logout"></i>
                                <span class="text">退出</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <section class="home-section">
                <div class="home-section-title">
                    <div class="menu-btn" @click="toggleSidActive">
                        <i class="iconfont icon-unorderedlist"></i>
                    </div>
                    <h1>图书馆管理系统</h1>
                </div>

                <div class="home-section-body">
                    <div class="home-section-body-view">
                        <!-- <keep-alive>
                        
                        </keep-alive> -->
                        <router-view />
                    </div>
                </div>
            </section>
        </div>
    </div>
</template>

<script>


    export default {
        name: 'SidebarMenuItem',
        data() {
            return {
                isUlactive: false,
                activeMenu: 'home', // 当前激活的菜单项 默认值为首页
                isSwitch: false, // 主题切换状态
                isSidActive: false, // 侧边栏切换状态
                userImg: require('../../assets/10.png'), // 用户头像路径
                // 用于存储路由参数
                routeName: JSON.parse(localStorage.getItem('user')).name,
                routeRole: JSON.parse(localStorage.getItem('user')).role
            };
        },
        methods: {
            toggleMenu(menu) {  //  激活菜单栏
                this.activeMenu = this.activeMenu === menu ? null : menu;
            },
            toggleTheme() { //  切换主题
                this.isSwitch = !this.isSwitch
            },
            toggleSidActive() { //  切换侧边栏状态
                this.isSidActive = !this.isSidActive
            },
            gotoHome() {    //  返回主页 
                if (this.$route.name !== 'home') {
                    this.$router.push({
                        name: 'home'
                    });
                }
            },
            allUsers() { //  用户列表
                if (this.$route.name !== 'getAllUser') {
                    this.$router.push({
                        name: 'getAllUser'
                    });
                }
            },
            addUser() { //  添加用户
                if (this.$route.name !== 'addUser') {
                    this.$router.push({
                        name: 'addUser'
                    });
                }
            },
            allCategory() {    //  图书分类总览 
                if (this.$route.name !== 'allCategory') {
                    this.$router.push({
                        name: 'allCategory'
                    });
                }
            },
            addCategory() {    //  新增图书分类 
                if (this.$route.name !== 'addCategory') {
                    this.$router.push({
                        name: 'addCategory'
                    });
                }
            },
            allBook() {    //  图书列表
                if (this.$route.name !== 'allBook') {
                    this.$router.push({
                        name: 'allBook'
                    });
                }
            },
            addBook() {    //  新增图书
                if (this.$route.name !== 'addBook') {
                    this.$router.push({
                        name: 'addBook'
                    });
                }
            },
            logout() {// 登出逻辑
                this.$confirm('确认退出?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    localStorage.removeItem('user')

                    this.$router.replace({  //  跳转会登录界面
                        name: 'LoginRegisterForm'
                    })
                    // this.$message({
                    //     type: 'success',
                    //     message: '删除成功!'
                    // });
                }).catch(() => {
                    // this.$message({
                    //     type: 'info',
                    //     message: '已取消删除'
                    // });
                });
            },
        },
        created() {
            // this.gotoHome()
        }
    };
</script>

<style scoped>
    @import url("../../assets/css/SidebarMenu.css");
</style>