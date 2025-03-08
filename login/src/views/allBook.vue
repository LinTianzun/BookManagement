<template>
    <div>
        <div class="searchBook">
            <el-input placeholder="请输入内容" v-model.trim="searInput" class="input-with-select">
                <el-select v-model.trim="searSelect" slot="prepend" placeholder="请选择">
                    <el-option label="书名" value="title"></el-option>
                    <el-option label="作者" value="author"></el-option>
                </el-select>
                <el-button slot="append" @click="searchBook" icon="el-icon-search"></el-button>
            </el-input>
            <el-button class="resBtn" type="primary" @click="resState">重置</el-button>
        </div>
        <div>
            <!-- 表格 -->
            <el-table ref="tableRef" class="userTable" :data="bookData" style="width: 100%">
                <el-table-column prop="title" label="书名" width="220">
                </el-table-column>
                <el-table-column prop="author" label="作者" width="120">
                </el-table-column>
                <el-table-column prop="publisher" label="出版社" width="200">
                </el-table-column>
                <el-table-column prop="categoryName" label="分类" width="110" :filters="filters"
                    :filter-method="filterTag" filter-placement="bottom-end">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.categoryId %2  == 0 ? 'primary' : 'success'" :style="{ width: '100%' }"
                            disable-transitions>{{scope.row.categoryName }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="数量" width="160">
                    <template slot-scope="scope">
                        <el-input-number size="mini" :value="scope.row.sum" :disabled="true"></el-input-number>
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

        <el-dialog width="30%" title="图书详情" :visible.sync="isDialogUserForm" :append-to-body="true">
            <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item label="书名">
                    <el-input class="dialogInput" v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="作者">
                    <el-input class="dialogInput" v-model="form.author"></el-input>
                </el-form-item>
                <el-form-item label="出版社">
                    <el-input class="dialogInput" v-model="form.publisher"></el-input>
                </el-form-item>
                <el-form-item label="分类">
                    <el-select class="dialogInput" v-model="form.categoryName" placeholder="请选择类型"
                        @change="updateCategoryId">
                        <el-option v-for="(item, index) in filters" :key="index" :label="item.text"
                            :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数量">
                    <el-input-number size="mini" :min="parseInt('-1')" class="dialogInput"
                        v-model="form.sum"></el-input-number>
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
    import { loadByPage, getAllCategoryService, fuzzyQueryBook, updateBookService, deleteBook } from '../api/BookApi.js'

    export default {
        data() {
            return {
                isDialogUserForm: false, //  是否弹出表单
                isSearch: false, //  是否执行搜索状态
                searInput: '',   //  绑定到搜索输入框的值   
                searSelect: '',  //  绑定到下拉选择框的值    列名
                bookForm: null,  // 定义接收数据的对象
                multipleSelection: [],
                bookData: [],  //存放图书数据
                categoryData: [],  //存放图书分类数据
                currentPage: 1, // 当前页码
                total: 0, // 总条数
                pageSize: 5, // 每页的数据条数
                form: {},   //  表单的数据
                oldForm: {},
                filters: []
            };
        },
        methods: {
            //每页条数改变时触发 选择一页显示多少行
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                // this.currentPage = 1;
                this.pageSize = val;
                if (this.isSearch) {    //  如果处于搜索状态时 执行搜索的数据更新
                    this.fuzzyQueryBook()
                    return
                }
                this.fetchBooksByPage()
            },
            //当前页改变时触发 跳转其他页
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
                if (this.isSearch) {    //  如果处于搜索状态时 执行搜索的数据更新
                    this.fuzzyQueryBook()
                    return
                }
                this.fetchBooksByPage()
            },
            handleEdit(index, row) {    //  弹出编辑表单
                console.log(index, row);
                this.isDialogUserForm = true
                this.form = row
                this.oldForm = { ...row }
            },
            handleDelete(index, row) {  //  触发删除图书的事件
                console.log(index, row);
                this.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let bookId = row.categoryId
                    this.deleteBookById(bookId)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            filterTag(value, row) {
                return row.categoryName === value;
            },
            async updateBook() {  //  更新图书数据
                try {
                    const updateusers = await updateBookService(this.form)
                    this.bookForm = updateusers
                    console.log(this.bookForm)
                    if (this.bookForm.flag) {
                        this.$message({
                            type: 'success',
                            message: '更新成功!'
                        });
                        this.resState()
                    } else {
                        console.log('更新失败' + this.bookForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async deleteBookById(id) {  //  删除图书数据
                try {
                    const users = await deleteBook(id)
                    this.bookForm = users.data
                    if (this.bookForm.flag) {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.fetchBooksByPage()
                    } else {
                        console.log('删除失败' + this.bookForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async fetchBooksByPage() {  //  获取图书数据
                try {
                    const users = await loadByPage(this.pageSize, this.currentPage)
                    this.bookForm = users.data
                    console.log(this.bookForm)
                    if (this.bookForm.flag) {
                        // 更新图书数据
                        this.bookData = this.bookForm.data.data
                        this.total = this.bookForm.data.total
                    } else {
                        console.log('更新数据失败' + this.bookForm.data.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            async getCategoryData() {  //  获取用图书类别信息
                try {
                    const category = await getAllCategoryService()
                    console.log(category)
                    if (category.flag) {
                        // 更新图书数据
                        this.categoryData = category.data
                        // 转换数据格式
                        this.filters = this.categoryData.map(category => ({
                            text: category.categoryName,
                            value: category.categoryName,
                            id: category.categoryId
                        }));
                    } else {
                        console.log('获取类别数据失败' + category.data)
                    }
                } catch (error) {
                    // 处理错误
                    console.error('Error fetching users by page:', error)
                }
            },
            searchBook() {   //  搜索
                if (this.searInput && this.searSelect) {
                    // 当搜索框有值时，执行搜索逻辑
                    console.log('执行搜索', this.searInput);
                    // 这里可以添加搜索逻辑，例如调用API获取数据
                    this.isSearch = true    //  将其变成搜索状态
                    this.pageSize = 5
                    this.currentPage = 1
                    this.fuzzyQueryBook()
                } else {
                    // 当搜索框没有值时，不执行搜索逻辑
                    console.log('没有检索值');
                    this.resState()
                }
            },
            async fuzzyQueryBook() {   //  对图书图书进行模糊查询
                try {
                    const searusers = await fuzzyQueryBook(this.searSelect, this.searInput, this.pageSize, this.currentPage)
                    this.bookForm = searusers.data
                    console.log(this.bookForm)
                    if (this.bookForm.flag) {
                        // 更新图书数据
                        if (this.bookForm.data.data.length > 0) {
                            this.bookData = this.bookForm.data.data
                            this.total = this.bookForm.data.total
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
                        console.log('更新数据失败' + this.bookForm.data.data)
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
                this.fetchBooksByPage() //  重新查询渲染数据
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
                    this.updateBook()
                }
            },
            cancelUpdateUser() { //  取消表单更新
                this.resState()
            },
            updateCategoryId(value) {
                // 根据选择的分类名称找到对应的分类ID
                const category = this.filters.find(item => item.text === value);
                if (category) {
                    // 更新categoryId
                    this.form.categoryId = category.id;
                }
            },
            // tableRowClassName({ row, rowIndex }) {
            //     // 根据sum的值返回不同的类名
            //     if (row.sum < 20) {
            //         return 'danger';
            //     } else if (row.sum >= 20 && row.sum < 50) {
            //         return 'warning';
            //     } else if (row.sum >= 50) {
            //         return 'success';
            //     }
            //     return ''
            // },
        },
        created() {
            // 组件挂载后调用分页查询
            this.fetchBooksByPage()
            this.getCategoryData()

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

    .searchBook {
        width: 100%;
        margin: 10px;
        margin-bottom: 30px;
        display: flex;
        justify-content: space-between;
    }

    .searchBook>.input-with-select {
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

    .userTable.danger {
        background: #f56c6c;
        /* color: white; */
    }

    .userTable .warning {
        background: #e6a23c;
        /* color: white; */
    }

    .userTable.success {
        background: #67c23a;
        /* color: white; */
    }
</style>