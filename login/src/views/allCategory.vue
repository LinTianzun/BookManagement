<template>
    <div>
        <div class="searchUser">
            <el-input placeholder="请输入名称" v-model.trim="searInput" class="input-with-select">
                <el-button slot="append" @click="searchUser" icon="el-icon-search"></el-button>
            </el-input>
            <el-button class="resBtn" type="primary" @click="resState">重置</el-button>
        </div>
        <!-- 表格 -->
        <el-table class="userTable" :data="categoryData" style="width: 100%">
            <el-table-column prop="categoryName" label="名称" width="140">
            </el-table-column>
            <el-table-column prop="description" label="描述" width="280">
            </el-table-column>
            <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>

                    <el-button slot="reference" size="mini" type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <div class="block" style="margin-top:15px;">
            <el-pagination align='center' @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="[1,3,5]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <!-- 编辑的表单 -->
        <el-dialog width="30%" title="图书类别详情" :visible.sync="isDialogCateForm" :append-to-body="true">
            <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item label="图书类名">
                    <el-input class="dialogInput" v-model="form.categoryName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="类别描述">
                    <el-input class="dialogInput" type="textarea" v-model="form.description"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelUpdateCate">取 消</el-button>
                <el-button type="primary" @click="checkUpdateCate">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import { loadByPage, fuzzyQueryCategory, deleteCategory, updateCategoryService } from '../api/CategoryApi.js'

    export default {
        data() {
            return {
                isDialogCateForm: false, //  是否弹出表单
                isSearch: false, //  是否执行搜索状态
                form: {},   //  表单的数据
                oldForm: {},    //  验证的数据
                categoryData: [],  //存放图书类别数据
                currentPage: 1, // 当前页码
                total: 0, // 总条数
                pageSize: 3, // 每页的数据条数
                categoryForm: null,
                searInput: '',   //  搜索的内容
            }
        },
        methods: {
            //每页条数改变时触发 选择一页显示多少行
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pageSize = val;
                if (this.isSearch) {    //  如果处于搜索状态时 执行搜索的数据更新
                    this.fuzzyQueryCate()
                    return
                }
                this.fetchCategoryPage()
            },
            //当前页改变时触发 跳转其他页
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                if (this.isSearch) {    //  如果处于搜索状态时 执行搜索的数据更新
                    this.fuzzyQueryCate()
                    return
                }
                this.fetchCategoryPage()
            },
            handleEdit(index, row) {    //  弹出编辑表单
                console.log(index, row);
                this.isDialogCateForm = true
                this.form = row
                this.oldForm = { ...row }
            },
            handleDelete(index, row) {  //  触发删除用户的事件
                console.log(index, row);
                this.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let cateId = row.categoryId
                    this.deleteCateById(cateId)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            searchUser() {   //  搜索
                if (this.searInput) {
                    // 当搜索框有值时，执行搜索逻辑
                    console.log('执行搜索', this.searInput);
                    // 这里可以添加搜索逻辑，例如调用API获取数据
                    this.isSearch = true    //  将其变成搜索状态
                    this.pageSize = 3
                    this.currentPage = 1
                    this.fuzzyQueryCate()
                } else {
                    // 当搜索框没有值时，不执行搜索逻辑
                    console.log('没有检索值');
                    this.resState()
                }
            },
            async deleteCateById(id) {  //  删除图书类别数据
                try {
                    const users = await deleteCategory(id)
                    this.categoryForm = users.data
                    if (this.categoryForm.flag) {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.resState()
                    } else {
                        console.log('删除失败' + this.categoryForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async updateCate() {  //  更新图书分类信息数据
                try {
                    const updateusers = await updateCategoryService(this.form)
                    this.categoryForm = updateusers
                    console.log(this.categoryForm)
                    if (this.categoryForm.flag) {
                        this.$message({
                            type: 'success',
                            message: '更新成功!'
                        });
                        this.resState()
                    } else {
                        console.log('更新失败' + this.categoryForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async fuzzyQueryCate() {   //  对图书类别数据进行模糊查询
                try {
                    const searusers = await fuzzyQueryCategory(this.searInput, this.pageSize, this.currentPage)
                    this.categoryForm = searusers.data
                    console.log(this.categoryForm)
                    if (this.categoryForm.flag) {
                        // 更新用户数据
                        if (this.categoryForm.data.data.length > 0) {
                            this.categoryData = this.categoryForm.data.data
                            this.total = this.categoryForm.data.total
                        } else {
                            // console.log('没有数据')
                            this.$message({
                                type: 'info',
                                message: '未查询到对应的信息'
                            });
                            this.isSearch = false
                            this.searInput = ''
                            // this.resState()
                        }
                    } else {
                        console.log('更新数据失败' + this.categoryForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async fetchCategoryPage() {  //  获取图书类别数据
                try {
                    const categorys = await loadByPage(this.pageSize, this.currentPage)
                    this.categoryForm = categorys.data
                    console.log(this.categoryForm)
                    if (this.categoryForm.flag) {
                        // 更新类别数据
                        this.categoryData = this.categoryForm.data.data
                        this.total = this.categoryForm.data.total
                    } else {
                        console.log('更新数据失败' + this.categoryForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            resState() {    //  重置页面状态
                this.isDialogCateForm = false
                this.searInput = ''
                this.isSearch = false   //  重置搜索状态
                this.pageSize = 3
                this.currentPage = 1
                this.fetchCategoryPage() //  重新查询渲染数据
            },
            checkUpdateCate() {   //  检查更新状态
                if (JSON.stringify(this.form) === JSON.stringify(this.oldForm)) {
                    // 表单未修改
                    console.log('未进行修改');
                    this.resState()
                } else {
                    // 表单已经修改
                    console.log('数据已经更改', this.form);
                    // 执行相应的操作，比如弹出确认对话框或执行保存操作
                    this.updateCate()
                }
            },
            cancelUpdateCate() { //  取消表单更新
                this.resState()
            }
        },
        created() {
            // 组件挂载后调用分页查询
            this.fetchCategoryPage()
        }
    }
</script>
<style scoped>
    .userTable {
        padding: 10px;
        padding-bottom: 0;
        margin: 10px;
        border-radius: 5px;
    }

    .searchUser {
        width: 100%;
        margin: 10px;
        margin-bottom: 30px;
        display: flex;
        justify-content: space-between;
    }

    .searchUser>.input-with-select {
        width: 300px;
    }

    .resBtn {
        margin-right: 10px;
    }

    .userDialog {
        width: 500px;
    }

    .dialogInput {
        width: 300px;
    }
</style>