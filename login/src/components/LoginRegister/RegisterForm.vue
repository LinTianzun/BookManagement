<template lang="">
    <form id="signupForm" class="form" @submit.prevent="register">
        <h2>Registration</h2>
        <div class="inputBox">
            <input type="text" v-model.trim="user.username" name="username" placeholder="请输入用户名" />
        </div>
        <div class="inputBox">
            <input type="email" v-model.trim="user.email" name="email" placeholder="邮箱" />
        </div>
        <el-select class="inputBox" v-model="rvalue" placeholder="请选择用户类型">
            <el-option v-for="item in options" :key="item.rvalue" :label="item.label" :value="item.rvalue">
            </el-option>
        </el-select>
        <div class="inputBox">
            <el-input placeholder="请输入密码" minlength="5" maxlength="16" v-model.trim="user.password"
                show-password></el-input>
        </div>
        <div class="inputBox">
            <el-input placeholder="请确认密码" minlength="5" maxlength="16" v-model.trim="confirmPwd"
                show-password></el-input>
        </div>
        <div class="inputBox">
            <input type="submit" value="Register Account" />
        </div>
        <div class="inputBox group">
            <a href="#">Already Have an Account?
                <b id="signin" @click="toggleForm">Login</b>
            </a>
        </div>
    </form>
</template>
<script>
    import { registerService } from '../../api/UserApi.js'

    export default {
        name: 'RegisterForm',
        data() {
            return {
                options: [{
                    rvalue: 'ADMIN',
                    label: '管理员'
                }, {
                    rvalue: 'STUDENT',
                    label: '学生'
                }],
                rvalue: '',     //  用户类型
                confirmPwd: '',  //  二次确认的密码
                user: { //  用户信息
                    username: '',
                    password: '',
                    email: '',
                    role: ''
                },
                registerError: null,  //  存储注册错误的信息
            }
        },
        methods: {
            toggleForm() {   //  切换登录注册页面
                this.clearAll()
                this.$bus.$emit('toggleForm')
            },
            clearAll() { //  清除所有的数据
                this.rvalue = ''
                this.user = {
                    username: '',
                    password: '',
                    email: '',
                    role: ''
                };
                this.confirmPwd = ''
            },
            async register() {    //  注册用户
                // console.log('注册')

                // 验证输入的数据
                if (!this.user.username || !this.user.password || !this.user.role) {
                    this.registerError = '所有字段都是必填的'
                    this.alertError()
                    return
                }

                //  判断两次输入的密码是否一致
                if (this.user.password != this.confirmPwd) {
                    this.registerError = '两次输入的密码不一致'
                    this.alertError()
                    this.confirmPwd = ''
                    return
                }

                try {

                    const response = await registerService(this.user);
                    console.log(response);

                    if (response.flag) {
                        console.log("注册成功")
                        this.success()
                        // 等待2秒后自动跳转到登录面板
                        setTimeout(() => {
                            this.$bus.$emit('popUser', this.user)
                            this.toggleForm()
                        }, 1500);
                    } else {
                        console.log("注册失败", response.data)
                        this.registerError = response.data
                        this.alertError()
                        this.clearAll()
                    }

                } catch (error) {
                    this.registerError = error.response.data;
                    console.log('注册失败', this.registerError.data)
                    this.registerError = this.registerError.data
                    this.alertError()
                    this.clearAll()
                }

            },
            alertError() {   //  错误的消息提示
                this.$message({
                    message: '注册失败 原因: ' + this.registerError,
                    type: 'error',
                    duration: 3000,
                    showClose: true
                });
            },
            success() {  //  成功的消息提示
                this.$message({
                    message: '注册成功',
                    type: 'success',
                    duration: 2200,
                    showClose: true
                });
            }
        },
        watch: {
            rvalue: {   //  当选择了用户类型时 将其绑定到user的role上
                deep: true,
                handler(newValue, oldValue) {
                    this.user.role = newValue
                }
            }
        }
    }
</script>
<style>
    .inputBox>.el-input--suffix>.el-input__inner {
        height: 30px;
    }

    .inputBox .el-input__suffix .el-input__icon {
        line-height: 30px;
    }
</style>