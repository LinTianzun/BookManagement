<template>
    <div class="addUserBody">
        <el-form :model="ruleForm" status-icon ref="ruleForm" label-width="300px" class="demo-ruleForm">
            <el-form-item for="categoryName" label="图书类名" prop="name" :rules="[ { required: true, message: '类名不能为空'}]">
                <el-input id="categoryName" v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item for="description" prop="description" label="分类描述">
                <el-input type="textarea" placeholder="请输入内容" id="description" v-model="ruleForm.description"
                    maxlength="30" show-word-limit>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import { addCategoryService } from '../api/CategoryApi.js'

    export default {
        data() {
            return {
                ruleForm: { //获取的表单信息
                    name: '',
                    description: ''
                },
                category: { //  用于传递的图书类别信息
                    categoryName: '',
                    description: ''
                },
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //  将表单数据 填充到user里
                        this.category.categoryName = this.ruleForm.name
                        this.category.description = this.ruleForm.description

                        this.addCategory()

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
            async addCategory() {
                try {

                    const response = await addCategoryService(this.category);
                    console.log(response);

                    if (response.flag) {
                        console.log("添加图书类别成功")
                        this.$message({
                            message: '添加图书类别成功',
                            type: 'success',
                            duration: 2000,
                            showClose: true
                        });
                        // 等待2秒后自动跳转到用户列表
                        setTimeout(() => {
                            if (this.$route.name !== 'allCategory') {
                                this.$router.push({
                                    name: 'allCategory'
                                });
                            }
                        }, 2000);
                    } else {
                        console.log("添加用失败", response.data)
                        this.resetForm('ruleForm')
                    }

                } catch (error) {
                    const addUserError = error.response.data;
                    console.log('添加失败', addUserError.data)
                    this.resetForm('ruleForm')
                }
            }
        }
    }
</script>
<style scoped>
    .addUserBody {
        position: relative;
        width: 600px;
        left: -10%;
        /* background-color: aliceblue; */
        align-items: center;
        justify-content: center;
        padding: 10px;
    }
</style>