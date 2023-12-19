import request from '@/utils/request.js'

export const langchaincaht = (inputText)=>{
    console.log('/llm/chat?input="'+inputText+'"')
    return request.get('/llm/chat?input="'+inputText+'"');
}