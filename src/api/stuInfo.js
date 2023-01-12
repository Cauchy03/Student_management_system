import requestx from '@/utils/requestx'

export function reqStuInfo() {
  return requestx({ url: '/stuInfo', method: 'get', })
}
