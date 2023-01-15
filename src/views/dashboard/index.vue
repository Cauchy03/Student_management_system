<template>
  <div class="app-container">
    <el-divider content-position="left">欢迎登录！</el-divider>
    <!-- <div class="dashboard-text">登录用户: {{ name }}</div> -->
    <el-row :gutter="10">
      <el-col :span="6">
        <div class="block">
          <el-avatar :size="200"
                     :src="avatar"></el-avatar>
        </div>
      </el-col>

    </el-row>
    <el-row :gutter="10"
            class="layout">
      <el-col :span="18">
        <el-descriptions title="登录用户信息"
                         direction="vertical"
                         :column="4"
                         border>
          <el-descriptions-item label="用户名">{{name}}</el-descriptions-item>
          <el-descriptions-item label="手机号">17600000000</el-descriptions-item>
          <el-descriptions-item label="居住地"
                                :span="2">重庆市</el-descriptions-item>
          <el-descriptions-item label="备注">
            <el-tag size="small">重庆工商大学</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="联系地址">重庆市南岸区学府大道 19 号</el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Teacher',
  data() {
    return {
    }
  },
  methods: {

  },
  computed: {
    ...mapGetters(['name', 'avatar'])
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
.layout {
  margin-top: 65px;
}
</style>
<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>

