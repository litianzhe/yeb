import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login'
import Home from '../views/Home'
import FriendChat from "@/views/chat/FriendChat";
import AdminInfo from "@/views/AdminInfo";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden: true // 不会被循环遍历出来
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children:[
      {
        path:'/chat',
        name: '在线聊天',
        //component: Home,
        component: FriendChat
      },
      {
        path:'/userinfo',
        name: '个人中心',
        component: AdminInfo
      },
    ],

  }
]

const router = new VueRouter({
  //mode: 'history', // localhost:8080/#/  默认会有#，实际上是路由的哈希 ,有两种模式，一种是history，直接用url地址，第二种就是hash，默认用的是hash
  base: process.env.BASE_URL,  //
  routes
})

export default router
