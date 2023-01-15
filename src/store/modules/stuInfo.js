import { reqStuInfo } from '@/api/stuInfo';
import { Message } from 'element-ui';


const actions = {
  async getStuInfo({ commit }) {
    try {
      let result = await reqStuInfo()
      commit('GETSTUINFO', result.stuInfo)
    } catch (error) {
      Message.error(error || '获取学生信息失败')
    }

  }
}
const mutations = {
  GETSTUINFO(state, value) {
    state.stuInfo = value
  }
}
const state = {
  stuInfo: []
}

export default {
  namespaced: true,
  actions,
  mutations,
  state
}

