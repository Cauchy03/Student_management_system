import { reqStuInfo } from '@/api/stuInfo';
import { Message } from 'element-ui';


const actions = {
  async getStuInfo({ commit }, { pagenum, pagesize }) {
    try {
      let result = await reqStuInfo(pagenum, pagesize)
      commit('GETSTUINFO', result.stuInfo)
      commit('GETTOTAL', result.total)
    } catch (error) {
      Message.error(error || '获取学生信息失败')
    }

  }
}
const mutations = {
  GETSTUINFO(state, value) {
    state.stuInfo = value
  },
  GETTOTAL(state, value) {
    state.total = value
  }
}
const state = {
  stuInfo: [],
  total: undefined
}

export default {
  namespaced: true,
  actions,
  mutations,
  state
}

