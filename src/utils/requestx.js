import axios from 'axios'
import { Message } from 'element-ui'

// 创建一个实例
const service = axios.create({
  // 基础路径, 发送请求时 路径出现/api
  baseURL: '/api',
  // 请求超时的时间 5s
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    return config
  },
  error => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
