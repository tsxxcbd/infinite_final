//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';

import { ElMessage } from 'element-plus'
//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8080';
const baseURL = '';
const instance = axios.create({baseURL})


//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        //判断业务状态码
        if(result.status===200){
            return result.data;
        }

        //异步操作的状态转换为失败
        return Promise.reject(result.data);
    },
    err=>{
        // 在这里处理异常
        if (err.response) {
            // 如果是服务器返回的错误
            const status = err.response.status;

            if (status === 603) {
                alert('用户名不存在');
            } else if(status === 600){
                alert('密码错误');
            }else if(status===601){
                alert('重复的用户名')
            } else if(status===602){
                alert('不合法的参数')
            }
        } else {
            // 如果是网络等其他错误
            alert('网络错误或请求被中断');
        }

        return Promise.reject(err); // 异步的状态转化成失败的状态
    }
)

export default instance;