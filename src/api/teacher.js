import requestx from '@/utils/requestx'

export function reqTeaInfo() {
  return requestx({ url: '/teaInfo', method: 'get' })
}

export function reqSelectTea(searchTea) {
  return requestx({ url: `/teaInfo/${searchTea}`, method: 'get' })
}