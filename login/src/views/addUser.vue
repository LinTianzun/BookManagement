<template>
    <div class="addUserBody">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
            <el-form-item for="username" label="用户名" prop="name" :rules="[ { required: true, message: '用户名不能为空'}]">
                <el-input id="username" v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item for="email" prop="email" label="邮箱">
                <el-input id="email" v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="角色">
                <el-select id="role" v-model="ruleForm.role" placeholder="请选角色">
                    <el-option label="管理员" value="ADMIN"></el-option>
                    <el-option label="学生" value="STUDENT"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item for="pass" label="密码" prop="pass">
                <el-input id="pass" type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item for="checkpass" label="确认密码" prop="checkPass">
                <el-input id="checkpass" type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import { registerService } from '../api/UserApi.js'

    export default {
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm: { //获取的表单信息
                    name: '',
                    role: '',
                    pass: '',
                    checkPass: '',
                    email: ''
                },
                user: { //  用于传递的用户信息
                    username: '',
                    password: '',
                    email: '',
                    role: ''
                },
                rules: {
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //  将表单数据 填充到user里
                        this.user.username = this.ruleForm.name
                        this.user.password = this.ruleForm.pass
                        this.user.email = this.ruleForm.email
                        this.user.role = this.ruleForm.role

                        this.addUser()

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.ruleForm.role = ''
                this.$refs[formName].resetFields();
            },
            async addUser() {
                try {

                    const response = await registerService(this.user);
                    console.log(response);

                    if (response.flag) {
                        console.log("添加用户成功")
                        this.$message({
                            message: '添加用户成功',
                            type: 'success',
                            duration: 2000,
                            showClose: true
                        });
                        // 等待2秒后自动跳转到用户列表
                        setTimeout(() => {
                            if (this.$route.name !== 'getAllUser') {
                                this.$router.push({
                                    name: 'getAllUser'
                                });
                            }
                        }, 2000);
                    } else {
                        console.log("添加用失败", response.data)
                        this.resetForm('ruleForm')
                    }

                } catch (error) {
                    const addUserError = error.response.data;
                    console.log('注册失败', addUserError.data)
                    this.resetForm('ruleForm')
                }
            }
        }
    }
</script>
<style scoped>
    .addUserBody {
        position: relative;
        left: -5%;
        /* background-color: aliceblue; */
        align-items: center;
        justify-content: center;
        padding: 10px;
    }
</style>