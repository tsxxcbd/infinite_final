import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'
import player from '@/views/player.vue'

import UserLikeList from '@/views/user/likeList.vue'



import searchVue from '@/views/Search.vue'
import commentVue from '@/views/Comment.vue'




//定义路由关系
const routes = [
    { path: '/login', component: LoginVue ,props: (route) => ({ currentSong: route.params.currentSong })},
    {
        path: '/', component: Layout, children: [
            { path: '/player', 
            component: player,
            name:'player',
            },
            { path: '/user/likeList', component: UserLikeList },
            { path: '/search', component: searchVue },
            { path: '/comment', component: commentVue }


        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router
