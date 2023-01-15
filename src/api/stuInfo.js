import requestx from '@/utils/requestx'

export function reqStuInfo(pagenum, pagesize) {
  return requestx({ url: '/stuInfo', method: 'post', data: { pagenum, pagesize } })
}

// 查询学生成绩信息
export function reqStuscore(sid) {
  return requestx({ url: `/stuInfo/score/${sid}`, method: 'post' })
}

// 查询学生
export function reqSearchStu(stuname) {
  return requestx({ url: `/stuInfo/${stuname}`, method: 'get' })
}
