// 导入 axios 实例
import request from '@/utils/request.js'


// 提供调用搜索接口的函数
export const searchService = (keyword) => {
    const params = new URLSearchParams();
    params.append('keyword', keyword);

    // 假设搜索接口为 /search
    return request.post('/api/search', params);
};