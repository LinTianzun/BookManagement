<template>
    <div class="addUserBody">
        <el-form :model="ruleForm" status-icon ref="ruleForm" label-width="200px" class="demo-ruleForm">
            <el-form-item for="title" label="书籍名称" prop="title" :rules="[ { required: true, message: '书籍名称不能为空'}]">
                <el-input id="title" v-model="ruleForm.title"></el-input>
            </el-form-item>
            <el-form-item for="author" prop="author" label="作者">
                <el-input id="author" v-model="ruleForm.author" clearable></el-input>
            </el-form-item>
            <el-form-item label="书籍类别">
                <el-select id="categoryId" v-model="ruleForm.categoryId" placeholder="请选类别">
                    <el-option v-for="(item, index) in category" :key="index" :label="item.text"
                        :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="数目" prop="sum" :rules="[
            { required: true, message: '数目不能为空'}]">
                <el-input-number :min="parseInt('-1')" :step="10" v-model="ruleForm.sum"></el-input-number>
            </el-form-item>
            <el-form-item for="publisher" prop="publisher" label="出版社">
                <el-input id="publisher" v-model="ruleForm.publisher" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import { getAllCategoryService, addBookService } from '../api/BookApi.js'

    export default {
        data() {
            return {
                ruleForm: { //获取的表单信息
                    title: '',
                    categoryId: '',
                    sum: '',
                    publisher: '',
                    author: ''
                },
                book: null, //用于传递的图书信息
                category: null,  //  用于存放图书类别
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //  将表单数据 填充到user里
                        this.book = this.ruleForm

                        this.addBook()

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.ruleForm.categoryId = ''
                this.$refs[formName].resetFields();
            },
            async addBook() {
                try {

                    const response = await addBookService(this.book);
                    console.log(response);

                    if (response.flag) {
                        // console.log("添加书籍成功")
                        this.$message({
                            message: '添加书籍成功',
                            type: 'success',
                            duration: 2000,
                            showClose: true
                        });
                        // 等待2秒后自动跳转到用户列表
                        setTimeout(() => {
                            if (this.$route.name !== 'allBook') {
                                this.$router.push({
                                    name: 'allBook'
                                });
                            }
                        }, 2000);
                    } else {
                        console.log("添加书籍失败", response.data)
                        this.resetForm('ruleForm')
                    }

                } catch (error) {
                    const addUserError = error.response.data;
                    console.log('添加书籍失败', addUserError.data)
                    this.resetForm('ruleForm')
                }
            },
            async getCategoryData() {  //  获取用图书类别信息
                try {
                    const category = await getAllCategoryService()
                    console.log(category)
                    if (category.flag) {
                        // 更新用户数据
                        this.categoryData = category.data
                        // 转换数据格式
                        this.category = this.categoryData.map(category => ({
                            text: category.categoryName,
                            value: category.categoryId
                        }));
                    } else {
                        console.log('获取类别数据失败' + category.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
        }
        ,
        created() {
            this.getCategoryData()
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

    .el-input-number {
        width: 217px;
    }
</style>