<template>
    <form id="signinForm" class="form" @submit.prevent="login">
        <h2>Login</h2>
        <div class="inputBox">
            <input type="text" v-model.trim="user.username" name="username" placeholder="用户名" />
        </div>
        <div class="inputBox">
            <input type="password" v-model.trim="user.password" name="password" placeholder="密码" />
        </div>
        <div class="inputBox group">
            <!-- <a href="#">Forgot Password</a> -->
            <el-checkbox class="rePwd" v-model="checkedPwd">Remember</el-checkbox>
            <a @click="toggleForm">Signup</a>
        </div>
        <div class="inputBox">
            <input type="submit" value="Sign in" />
        </div>
    </form>
</template>

<script>
    import { loginService, getAllUserService } from '../../api/UserApi.js'
    export default {
        name: 'LoginForm',
        data() {
            return {
                user: { //  用户信息
                    username: '',
                    password: '',
                },
                loginForm: '',
                data: [],
                loginError: null, //存储登录错误的信息
                checkedPwd: false,    //  是否记住密码
                userRole: null  //  用户权限
            }
        },
        mounted() {

        },
        methods: {
            toggleForm() {      //  切换登录注册页面
                this.usercClear()
                this.$bus.$emit('toggleForm')
            },
            async login() {
                if (this.user.username == "" || this.user.password == "") {
                    // alert("用户名和密码不能为空")
                    this.loginError = '用户名和密码不能为空'
                    this.alertError()
                    this.usercClear()
                } else {
                    try {
                        const response = await loginService(this.user);
                        console.log(response);
                        // 登录成功后的逻辑，例如跳转到另一个页面或显示成功消息
                        // 例如：this.$router.push('/dashboard');
                        if (response.flag) {
                            console.log("登录成功")
                            this.success()
                            this.userRole = response.data.role
                            if (this.checkedPwd) {
                                // 如果用户选择了记住密码，则保存用户信息
                                localStorage.setItem('savedUser', JSON.stringify(this.user));
                            } else {
                                // 如果用户取消了记住密码，则清除本地存储中的用户信息
                                localStorage.removeItem('savedUser');
                            }
                            if (this.userRole === 'ADMIN') {
                                console.log('跳转到管理员页面')
                                const adminUser = {
                                    name: this.user.username,
                                    role: '管理员'
                                }
                                //  将一些用户信息存储到浏览器的localStorage中
                                localStorage.setItem('user', JSON.stringify(adminUser))
                                this.$router.replace({
                                    name: 'SidebarMenuItem'
                                })
                            }
                            if (this.userRole === 'STUDENT') {
                                console.log('跳转到学生页面')
                                const adminUser = {
                                    name: this.user.username,
                                    role: '学生'
                                }
                                //  将一些用户信息存储到浏览器的localStorage中
                                localStorage.setItem('user', JSON.stringify(adminUser))
                                this.$router.replace({
                                    name: 'Student'
                                })
                            }
                        } else {
                            console.log("登录失败", response.data)
                            this.loginError = response.data
                            this.alertError()
                            this.usercClear()
                        }

                    } catch (error) {
                        // console.error('登录失败', error);
                        this.loginError = error.response.data;
                        console.log('登录失败', this.loginError.data)
                        this.loginError = this.loginError.data
                        this.alertError()
                        this.usercClear()
                    }
                }
            },
            usercClear() {   //  清空登录信息
                this.user.username = ""
                this.user.password = ""
                this.checkedPwd = false
            },
            alertError() {   //  错误的消息提示
                this.$message({
                    message: '登录失败 原因: ' + this.loginError,
                    type: 'error',
                    duration: 3000,
                    showClose: true
                });
            },
            success() {  //  成功的消息提示
                this.$message({
                    message: '登录成功',
                    type: 'success',
                    duration: 2200,
                    showClose: true
                });
            },
            popUser(pUser) {    //  填充用户名和密码
                this.user.username = pUser.username
                this.user.password = pUser.password
            }
        },
        mounted() { //  当注册成功的时候 接受传递过来的user 调用填充方法
            this.$bus.$on('popUser', this.popUser)
            // 检查本地存储中是否存在之前保存的用户名和密码
            const savedUser = localStorage.getItem('savedUser');
            if (savedUser) {
                this.user = JSON.parse(savedUser);
                this.rememberMe = true; // 设置为记住状态
            }
        },
        beforeDestroy() {
            this.$bus.$off('popUser')
        },
    }
</script>

<style scoped>
    .rePwd {
        color: white;
    }
</style>