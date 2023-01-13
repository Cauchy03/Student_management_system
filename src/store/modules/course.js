import { reqCourseInfo } from '@/api/course';
import { Message } from 'element-ui';


const actions = {
  async getCourseInfo({ commit }, value) {
    try {
      let result = await reqCourseInfo(value)
      console.log(result);
      commit('GETCOURSEINFO', result.courseInfo)
    } catch (error) {
      Message.error(error || '获取课程信息失败')
    }

  }
}
const mutations = {
  GETCOURSEINFO(state, value) {
    state.courseInfo = value
  }
}
const state = {
  courseInfo: []
}

export default {
  namespaced: true,
  actions,
  mutations,
  state
}

