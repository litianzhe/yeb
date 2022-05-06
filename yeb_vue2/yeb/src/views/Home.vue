<template>
  <div>
    <el-container>

      <el-header class="homeHeader">
        <div class="title">云E办</div>
        <!-- 1-1 添加在线聊天入口 -->
        <el-button type="text" icon="el-icon-bell" size="normal"
                     style="margin-right: 8px;color: black;" @click="goChar"></el-button>
        <el-dropdown class="userInfo" @command="commandHandler">
          <span class="el-dropdown-link">
            {{user.name}}<i><img :src="user.userFace" ></i>
          </span>
          <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
              <el-dropdown-item command="setting">设置</el-dropdown-item>
              <el-dropdown-item command="logout">注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        
      </el-header>

      <el-container>
        <el-aside width="200px">
          <!-- 1、添加 router  -->
          <el-menu router unique-opened> 
            <!-- 2、循环整个路由组件 1.vue、element-ui 依权限动态导航 v-for、v-if  2也可以：使用计算属性
            在管理后台需要按用户权限展示不同导航菜单时，我们需要从后端请求数据来进行菜单渲染
            v-for和v-if不能在同一标签内使用（存在性能问题：v-for 具有比 v-if 更高的优先级，
            意味着 v-if 将分别重复运行于每个 v-for 循环中），所以我们需要将其分别放在不同标签内，v-for放在父级，v-if放在子级。
            同时，由于使用实体实体标签作为v-for载体，将会影响el-menu的list结构，因此，我们将v-for放在template标签中。  -->
            <template v-for="(item,index) in routes">
              <!-- 2、循环整个路由组件，不展示 hidden: true 的路由组件 -->
              <el-submenu :index="index+''" :key="index" 
              v-if="!item.hidden"  >
                
                  <template slot="title" ><i :class="item.iconCls" style="color: black;margin-right: 5px"></i>
                    <span>{{item.name}}</span>
                  </template>
                    <!-- 3、循环遍历子路由 -->
                    <el-menu-item :index="children.path" :key="index"
                     v-for="(children,index) in item.children">
                      {{children.name}}
                    </el-menu-item>
              </el-submenu>
            </template>
          </el-menu>
        </el-aside>
        <el-main>
          <!-- 面包屑导航区域 -->
          <el-breadcrumb separator-class="el-icon-arrow-right"
           v-if="this.$router.currentRoute.path!=='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页 </el-breadcrumb-item>
            <el-breadcrumb-item >{{this.$router.currentRoute.name }} </el-breadcrumb-item>
          </el-breadcrumb>
          <div class="homeWelcome" v-if="this.$router.currentRoute.path==='/home'">
            欢迎来到云E办系统！
          </div>
          <!-- 路由点位符 -->
          <router-view class="homeRouterView"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
  
</template>

<script>


export default {
  name: 'Home',
  data() {
    return{
      // 获取用户信息，将字符串转对象  
      //user: JSON.parse(window.sessionStorage.getItem('user'))
    }
  },
  computed: {
    // 从 vuex 获取 routes
    routes() {
      return this.$store.state.routes;
    },
    user(){  //   user: JSON.parse(window.sessionStorage.getItem('user'))
      return this.$store.state.currentAdmin;
    }
  },
  methods:{
    // 1-2 进入在线聊天页面
    goChar() {
      this.$router.push('/chat')
    },
    // menuClick(index) {
    //   this.$router.push(index);
    // }
    // 注销登录
    commandHandler(command) {
      if (command === 'logout') {
        // 弹框提示用户是否要删除
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 注销登录
          this.postRequest('/logout')
          // 清空用户信息
          window.sessionStorage.removeItem('tokenStr');
          window.sessionStorage.removeItem('user');
          // 路由替换到登录页面
          this.$router.replace('/')
          // 清空菜单信息；在src/utils/menus.js 中初始化菜单信息
          this.$store.commit('initRoutes', [])
         // this.$router.replace('/')
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消注销登录'
          });
        });
      }
      if (command === 'userinfo') {
        this.$router.push('/userinfo')
      }
    }

  }
}
</script>

<style scoped>
    .homeHeader{
        background: cornflowerblue ;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0px 15px;
        box-sizing: border-box;
    }
    .homeHeader .title{
        font-size: 40px;
        font-family:华文行楷;
        color: white;
    }
    .homeHeader .userInfo{
        cursor: pointer;
    }
    .el-dropdown-link img{
        width: 40px;
        height: 40px;
        border-radius: 24px;
        margin-left: 8px;
    }
    .homeWelcome{
        text-align: center;
        font-size: 40px;
        font-family: 华文行楷;
        color: #409eff;
        padding-top: 200px;
    }
    .homeRouterView{
        margin-top: 20px;
        text-align: left;
    }

</style>
