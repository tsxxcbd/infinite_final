// main.ts
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import './assets/fonts/iconfont.css'
import router from '@/router'
import {createPinia} from 'pinia'
import axios from "axios"
axios.defaults.withCredentials=true
//axios.defaults.baseURL="/api"

const app = createApp(App)
const pinia = createPinia();
app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.config.globalProperties.$axios=axios
app.mount('#app')

