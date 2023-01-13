<template>
  <div class="app-container">
    <!-- :model="formInline" -->
    <el-form :inline="true"
             class="demo-form-inline">
      <el-form-item label="开设学期">
        <el-select v-model="termId"
                   placeholder="学期选择"
                   size="small">
          <el-option label="2022-2023第一学期"
                     value="1"></el-option>
          <el-option label="2022-2023第二学期"
                     value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   size="small"
                   @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="courseInfo"
              v-loading="loading"
              style="width: 100%"
              border>
      <el-table-column label="序号"
                       width="80"
                       align="center"
                       type="index">
      </el-table-column>
      <el-table-column prop="cno"
                       label="课程编号"
                       width="180">
      </el-table-column>
      <el-table-column prop="cname"
                       label="课程名称"
                       width="160">
      </el-table-column>
      <el-table-column prop="tname"
                       label="任课教师"
                       width="130">
      </el-table-column>
      <el-table-column prop="cdep"
                       label="开设院系"
                       width="160">
      </el-table-column>
      <el-table-column prop="ctime"
                       label="总学时"
                       width="100">
      </el-table-column>
      <el-table-column prop="cscore"
                       label="学分"
                       width="100">
      </el-table-column>
      <el-table-column label="操作"
                       width="width"
                       prop="sid">
        <template slot-scope="{row}">
          <!-- {{row}} -->
          <el-button type="primary"
                     icon="el-icon-user-solid"
                     size="mini"
                     title="查询学生该课程成绩总览"
                     @click="getScoreInfo"></el-button>
          <el-button type="warning"
                     icon="el-icon-edit"
                     size="mini"
                     title="编辑"
                     @click="updateCourse(row)"></el-button>
          <el-popconfirm confirm-button-text='确定'
                         cancel-button-text='取消'
                         icon="el-icon-info"
                         icon-color="red"
                         title="确定删除吗？"
                         @onConfirm="deleteCourse(row.cid)">
            <el-button type="danger"
                       icon="el-icon-delete"
                       size="mini"
                       title="删除"
                       slot="reference"
                       style="margin-left:7px"></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 对话框 成绩查询 -->
    <el-dialog title="学生成绩信息"
               :visible.sync="dialogTableVisible">
      <!-- :data="gridData" -->
      <el-table :data="scoreInfo"
                v-loading="loading">

        <el-table-column property="cname"
                         label="课程名称"
                         width="200"></el-table-column>
        <el-table-column property="stuno"
                         label="学号"></el-table-column>
        <el-table-column property="stuname"
                         label="学生姓名"></el-table-column>
        <el-table-column property="score"
                         label="成绩">
          <template slot-scope="{row}">
            <el-tag type="success">{{row.score}}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- 课程 编辑 -->
    <el-dialog title="编辑课程信息"
               :visible.sync="dialogFormVisible">
      <el-form :model="row"
               inline
               :rules="rules">
        <el-form-item label="课程编号"
                      :label-width="formLabelWidth"
                      prop="cno">
          <el-input v-model.number="row.cno"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="课程名称"
                      :label-width="formLabelWidth"
                      prop="cname">
          <el-input v-model="row.cname"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="任课教师"
                      :label-width="formLabelWidth"
                      prop="tname">
          <el-input v-model="row.tname"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="开设院系"
                      :label-width="formLabelWidth"
                      prop="cdep">
          <el-input v-model="row.cdep"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="总学时"
                      :label-width="formLabelWidth"
                      prop="ctime">
          <el-input v-model.number="row.ctime"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="学分"
                      :label-width="formLabelWidth"
                      prop="cscore">
          <el-input v-model.number="row.cscore"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="confirmUpdate(row)">确 定</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>

      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { reqScoreInfo, reqDeleteCourse } from '@/api/course';
export default {
  name: 'Course',
  data() {
    return {
      loading: false,//loading效果
      termId: undefined,//选择学期
      dialogTableVisible: false,//对话框显示与隐藏
      dialogFormVisible: false,//对话框显示与隐藏
      scoreInfo: [],//成绩信息
      row: {},
      formLabelWidth: '120px',
      rules: {
        cno: [
          { required: true, message: '课程编号不能为空', trigger: 'blur' },
        ],
        cname: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' },
        ],
        tname: [
          { required: true, message: '任课教师不能为空', trigger: 'blur' },
        ],
        cdep: [
          { required: true, message: '开课院系不能为空', trigger: 'blur' },
        ],
        ctime: [
          { required: true, message: '总学时不能为空', trigger: 'blur' },
        ],
        cscore: [
          { required: true, message: '学分不能为空', trigger: 'blur' },
        ],
      }//表单验证规则
    }
  },
  computed: {
    ...mapState('course', ['courseInfo'])
  },
  methods: {
    getDada() {
      this.$store.dispatch('course/getCourseInfo', this.termId)
    },
    // 查询学期课程信息
    onSubmit() {
      if (this.termId === undefined) {
        this.$message({ type: 'info', message: '请选择学期' })
      } else {
        this.loading = true
        this.getDada()
        setTimeout(() => {
          this.loading = false
        }, 500);
      }

    },
    // 查询学生信息
    async getScoreInfo() {
      this.dialogTableVisible = true
      this.loading = true
      try {
        let result = await reqScoreInfo()
        console.log(result);
        this.scoreInfo = result.scoreInfo
        this.loading = false
      } catch (error) {
        this.$message.error(error || '查询成绩信息失败')
      }
    },
    // 编辑课程信息
    updateCourse(row) {
      this.row = { ...row }// 浅拷贝一份数据
      this.dialogFormVisible = true
    },
    // 确定编辑
    confirmUpdate(row) {
      console.log(row);
      this.dialogFormVisible = false
    },
    // 删除课程
    async deleteCourse(id) {
      console.log(id);
      try {
        let result = await reqDeleteCourse(id)
        this.getDada()
      } catch (error) {
        this.$message.error(error || '删除课程信息失败！')
      }
    }
  }
}
</script>

