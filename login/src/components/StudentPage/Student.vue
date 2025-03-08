<template lang="">
    <div id="stuBody">
        <header>
            <div class="navbar">
                <div class="logo"><a>Logo</a></div>
                <ul class="links">
                    <li><a>Home</a></li>
                    <li><a>About</a></li>
                    <li><a>Services</a></li>
                    <li><a>Contact</a></li>
                </ul>
                <a @click="stuout" class="action_btn">Get Started</a>
                <div class="toggle_btn">
                    <i class="iconfont icon-menu"></i>
                </div>
            </div>

            <div class="dropdown_menu">
                <li><a>Home</a></li>
                <li><a>About</a></li>
                <li><a>Services</a></li>
                <li><a>Contact</a></li>
                <li><a class="action_btn">Get Started</a></li>
            </div>
        </header>

        <main>
            <section id="hero">
                <h1>Welcomne</h1>
                <p style="margin-bottom: 10px;">
                    {{routeName}}
                </p>
                <p>
                    迎着盛大的夕阳去逃亡吧！
                </p>
            </section>
        </main>
    </div>
</template>
<script>
    export default {
        name: 'Studnet',
        data() {
            return {
                // 用于存储路由参数
                routeName: JSON.parse(localStorage.getItem('user')).name,
                routeRole: JSON.parse(localStorage.getItem('user')).role
            }
        },
        methods: {
            stuout() {// 登出逻辑
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
    }
</script>
<style scoped>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: 'Open sans', sans-serif;
    }

    #stuBody {
        height: 100vh;
        background-color: #000;
        background: url(../../assets/11.jpg);
        background-size: cover;
        background-position: center;
    }

    li {
        list-style: none;
    }

    a {
        text-decoration: none;
        color: #fff;
        font-size: 1rem;
    }

    a:hover {
        color: #c46769;
    }

    header {
        position: relative;
        padding: 0 2rem;
    }

    .navbar {
        width: 100%;
        height: 60px;
        max-width: 1200px;
        margin: 0 auto;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .navbar .logo a {
        font-size: 2rem;
        font-weight: bold;
    }

    .navbar .links {
        display: flex;
        gap: 2rem;
    }

    .navbar .toggle_btn {
        color: #fff;
        font-size: 1.5rem;
        cursor: pointer;
        display: none;
    }

    .action_btn {
        background-color: #ca7678;
        color: #fff;
        padding: 0.5rem 1rem;
        border: none;
        outline: none;
        border-radius: 20px;
        font-size: 0.8rem;
        font-weight: bold;
        cursor: pointer;
        transition: scale 0.2 ease;
    }

    .action_btn:hover {
        scale: 1.05;
        color: #fff;
    }

    .action_btn:active {
        scale: 0.95;
    }

    .dropdown_menu {
        display: none;
        position: absolute;
        right: 2rem;
        top: 60px;
        width: 300px;
        height: 0px;
        background: rgba(255, 255, 255, 0.1);
        backdrop-filter: blur(15px);
        border-radius: 10px;
        overflow: hidden;
        transition: height 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
    }

    .dropdown_menu.open {
        height: 240px;
    }

    .dropdown_menu li {
        padding: 0.7rem;
        display: flex;
        align-content: center;
        justify-content: center;
    }

    .dropdown_menu .action_btn {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    section#hero {
        height: calc(100vh - 60px);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        text-align: center;
        color: #5a728f;
    }

    #hero h1 {
        font-size: 3rem;
        margin-bottom: 1rem;
    }

    @media(max-width:992px) {

        .navbar .links,
        .navbar .action_btn {
            display: none;
        }

        .navbar .toggle_btn {
            display: block;
        }

        .dropdown_menu {
            display: block;
        }
    }

    @media(max-width:576px) {
        .dropdown_menu {
            left: 2rem;
            width: unset;
        }
    }
</style>