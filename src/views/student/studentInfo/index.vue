<template>
  <div class="app-container">
    <!-- 表单 -->
    <el-form inline>
      <!-- 表单元素 -->
      <el-form-item>
        <el-input v-model="searchStu"
                  placeholder="学生名"
                  size="small" />
      </el-form-item>
      <!-- 查询与清空的按钮 -->
      <el-form-item>
        <el-button type="primary"
                   icon="el-icon-search"
                   size="small">查询</el-button>
        <el-button type="default"
                   size="small">清空</el-button>
      </el-form-item>
    </el-form>

    <div style="margin-bottom: 10px">
      <!-- 添加与批量添加按钮 -->
      <el-button type="primary"
                 icon="el-icon-plus"
                 size="small">添加学生</el-button>
      <el-button type="danger"
                 size="small">批量删除</el-button>
    </div>

    <el-table v-loading="loading"
              :data="stuInfo"
              style="width: 100%"
              border>
      <el-table-column label="序号"
                       width="80"
                       align="center"
                       type="index">
      </el-table-column>
      <el-table-column prop="stuno"
                       label="学号"
                       width="180">
      </el-table-column>
      <el-table-column prop="stuname"
                       label="姓名"
                       width="160">
      </el-table-column>
      <el-table-column prop="stusex"
                       label="性别"
                       width="130">
      </el-table-column>
      <el-table-column prop="stuage"
                       label="年龄"
                       width="130">
      </el-table-column>
      <el-table-column prop="stuclass"
                       label="班级"
                       width="160">
      </el-table-column>
      <el-table-column label="编辑"
                       width="width"
                       prop="sid">
        <template slot-scope="{row}">
          {{row}}
          <el-button type="info"
                     icon="el-icon-info"
                     size="mini"></el-button>
          <el-button type="warning"
                     icon="el-icon-edit"
                     size="mini"></el-button>
          <el-button type="danger"
                     icon="el-icon-delete"
                     size="mini"></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'StudentInfo',
  data() {
    return {
      loading: false,//loading效果
      searchStu: ''
    }
  },
  mounted() {
    this.loading = true
    this.$store.dispatch('stuInfo/getStuInfo')
    setTimeout(() => {
      this.loading = false
    }, 500);
  },
  computed: {
    ...mapState('stuInfo', ['stuInfo'])
  }
}
</script>
