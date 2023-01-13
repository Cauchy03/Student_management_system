import requestx from '@/utils/requestx'

// 查询课程信息
export function reqCourseInfo(termId) {
  return requestx({ url: '/course', method: 'post', data: { termId } })
}

// 查询该课程学生成绩
export function reqScoreInfo() {
  return requestx({ url: '/course/score', method: 'post' })
}

// 删除课程
export function reqDeleteCourse(id) {
  return requestx({ url: `/course/${id}`, method: 'delete' })
}
