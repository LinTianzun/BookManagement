<template>
    <div>
        <div class="searchUser">
            <el-input placeholder="请输入内容" v-model.trim="searInput" class="input-with-select">
                <el-select v-model.trim="searSelect" slot="prepend" placeholder="请选择">
                    <el-option label="姓名" value="username"></el-option>
                    <el-option label="邮箱" value="email"></el-option>
                </el-select>
                <el-button slot="append" @click="searchUser" icon="el-icon-search"></el-button>
            </el-input>
            <el-button class="resBtn" type="primary" @click="resState">重置</el-button>
        </div>
        <div>
            <!-- 表格 -->
            <el-table ref="tableRef" class="userTable" :data="usersData" style="width: 100%">
                <el-table-column prop="username" label="姓名" width="140">
                </el-table-column>
                <el-table-column prop="email" label="邮箱" width="220">
                </el-table-column>
                <el-table-column prop="role" label="角色" width="110"
                    :filters="[{ text: '学生', value: 'STUDENT' }, { text: '管理员', value: 'ADMIN' }]"
                    :filter-method="filterTag" filter-placement="bottom-end">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.role === 'STUDENT' ? 'primary' : 'success'" :style="{ width: '100%' }"
                            disable-transitions>{{scope.row.role === 'STUDENT' ? '学生' : '管理员' }}</el-tag>
                    </template>
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
        </div>

        <el-dialog width="30%" title="用户详情" :visible.sync="isDialogUserForm" :append-to-body="true">
            <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item label="用户名">
                    <el-input class="dialogInput" v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input class="dialogInput" v-model="form.password" show-password></el-input>
                </el-form-item>
                <el-form-item label="用户类型">
                    <el-select class="dialogInput" v-model="form.role" placeholder="请选择类型">
                        <el-option label="管理员" value="ADMIN"></el-option>
                        <el-option label="学生" value="STUDENT"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input class="dialogInput" v-model="form.email"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelUpdateUser">取 消</el-button>
                <el-button type="primary" @click="checkUpdateUser">确 定</el-button>
            </div>
        </el-dialog>

    </div>

</template>

<script>
    import { loadByPage, loadFuzzyByPage, deleteUser, updateUserService } from '../api/UserApi.js'

    export default {
        data() {
            return {
                isDialogUserForm: false, //  是否弹出表单
                isSearch: false, //  是否执行搜索状态
                searInput: '',   //  绑定到搜索输入框的值   
                searSelect: '',  //  绑定到下拉选择框的值    列名
                usersForm: null,  // 定义接收数据的对象
                multipleSelection: [],
                usersData: [],  //存放用户数据
                currentPage: 1, // 当前页码
                total: 0, // 总条数
                pageSize: 5, // 每页的数据条数
                form: {},   //  表单的数据
                oldForm: {}
            };
        },
        methods: {
            //每页条数改变时触发 选择一页显示多少行
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                // this.currentPage = 1;
                this.pageSize = val;
                if (this.isSearch) {    //  如果处于搜索状态时 执行搜索的数据更新
                    this.fuzzyQueryUser()
                    return
                }
                this.fetchUsersByPage()
            },
            //当前页改变时触发 跳转其他页
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                if (this.isSearch) {    //  如果处于搜索状态时 执行搜索的数据更新
                    this.fuzzyQueryUser()
                    return
                }
                this.fetchUsersByPage()
            },
            handleEdit(index, row) {    //  弹出编辑表单
                console.log(index, row);
                this.isDialogUserForm = true
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
                    let userId = row.userId
                    this.deleteUserById(userId)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            filterTag(value, row) {
                return row.role === value;
            },
            async updateUser() {  //  跟新用户数据
                try {
                    const updateusers = await updateUserService(this.form)
                    this.usersForm = updateusers
                    console.log(this.usersForm)
                    if (this.usersForm.flag) {
                        this.$message({
                            type: 'success',
                            message: '更新成功!'
                        });
                        this.resState()
                    } else {
                        console.log('更新失败' + this.usersForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async deleteUserById(id) {  //  删除用户数据
                try {
                    const users = await deleteUser(id)
                    this.usersForm = users.data
                    if (this.usersForm.flag) {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.fetchUsersByPage()
                    } else {
                        console.log('删除失败' + this.usersForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async fetchUsersByPage() {  //  获取用户数据
                try {
                    const users = await loadByPage(this.pageSize, this.currentPage)
                    this.usersForm = users.data
                    console.log(this.usersForm)
                    if (this.usersForm.flag) {
                        // 更新用户数据
                        this.usersData = this.usersForm.data.data
                        this.total = this.usersForm.data.total
                    } else {
                        console.log('更新数据失败' + this.usersForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            searchUser() {   //  搜索
                if (this.searInput && this.searSelect) {
                    // 当搜索框有值时，执行搜索逻辑
                    console.log('执行搜索', this.searInput);
                    // 这里可以添加搜索逻辑，例如调用API获取数据
                    this.isSearch = true    //  将其变成搜索状态
                    this.pageSize = 5
                    this.currentPage = 1
                    this.fuzzyQueryUser()
                } else {
                    // 当搜索框没有值时，不执行搜索逻辑
                    console.log('没有检索值');
                    this.resState()
                }
            },
            async fuzzyQueryUser() {   //  对用户数据进行模糊查询
                try {
                    const searusers = await loadFuzzyByPage(this.searSelect, this.searInput, this.pageSize, this.currentPage)
                    this.usersForm = searusers.data
                    console.log(this.usersForm)
                    if (this.usersForm.flag) {
                        // 更新用户数据
                        if (this.usersForm.data.data.length > 0) {
                            this.usersData = this.usersForm.data.data
                            this.total = this.usersForm.data.total
                        } else {
                            // console.log('没有数据')
                            this.$message({
                                type: 'info',
                                message: '为查询到对应的信息'
                            });
                            this.isSearch = false
                            this.searInput = ''
                            // this.resState()
                        }
                    } else {
                        console.log('更新数据失败' + this.usersForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            resState() {    //  重置页面状态
                this.isSearch = false   //  重置搜索状态
                this.isDialogUserForm = false;
                this.searSelect = ''
                this.searInput = ''
                this.pageSize = 5
                this.currentPage = 1
                this.form = {}
                this.oldForm = {}
                // 清除表格过滤
                this.$refs.tableRef.clearFilter()
                this.fetchUsersByPage() //  重新查询渲染数据
            },
            checkUpdateUser() {   //  检查更新状态
                if (JSON.stringify(this.form) === JSON.stringify(this.oldForm)) {
                    // 表单未修改
                    console.log('未进行修改');
                    this.resState()
                } else {
                    // 表单已经修改
                    console.log('数据已经更改', this.form);
                    // 执行相应的操作，比如弹出确认对话框或执行保存操作
                    this.updateUser()
                }
            },
            cancelUpdateUser() { //  取消表单更新
                this.resState()
            }
        },
        created() {
            // 组件挂载后调用分页查询
            this.fetchUsersByPage()
        }
    };
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