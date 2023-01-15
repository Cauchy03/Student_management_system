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
                   size="small"
                   @click="search">查询</el-button>
        <el-button type="default"
                   size="small"
                   @click="clearInput">清空</el-button>
      </el-form-item>
    </el-form>

    <div style="margin-bottom: 10px">
      <!-- 添加与批量添加按钮 -->
      <el-button type="primary"
                 icon="el-icon-plus"
                 size="small"
                 @click="addStu">添加学生</el-button>
      <el-button type="danger"
                 size="small"
                 @click="deleteInBatches">批量删除</el-button>
    </div>

    <el-table v-loading="loading"
              :data="stuInfo"
              style="width: 100%"
              border
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55" />
      <el-table-column label="序号"
                       width="80"
                       align="center"
                       prop="sid"
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
      <el-table-column prop="sid"
                       label="操作"
                       width="width">
        <template slot-scope="{row}">
          <el-button type="info"
                     icon="el-icon-info"
                     size="mini"
                     title="成绩信息"
                     @click="scoreInfo(row)"></el-button>
          <el-button type="warning"
                     icon="el-icon-edit"
                     size="mini"
                     title="编辑"
                     @click="updateStu(row)"></el-button>
          <el-popconfirm confirm-button-text='确定'
                         cancel-button-text='取消'
                         icon="el-icon-info"
                         icon-color="red"
                         title="确定删除吗？"
                         @confirm="deleteStu(row)">
            <el-button type="danger"
                       icon="el-icon-delete"
                       size="mini"
                       title="删除"
                       slot="reference"
                       style="margin-left:8px">
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <el-pagination :current-page="curpage"
                   :page-size="limit"
                   :page-sizes="[3,5,7]"
                   layout=" prev, pager, next, jumper,  ->, sizes, total"
                   :total="total"
                   style="margin-top:5px"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange">
    </el-pagination>

    <!-- 会话框 -->
    <el-dialog title="成绩管理"
               :visible.sync="dialogTableVisible">
      <el-table :data="stuScore"
                border>
        <el-table-column property="cno"
                         label="课程编号"
                         width="width"></el-table-column>
        <el-table-column property="cname"
                         label="课程名称"
                         width="180"></el-table-column>
        <el-table-column property="cscore"
                         label="学分"
                         width="width"></el-table-column>
        <el-table-column property="score"
                         label="成绩"
                         width="width">
          <template slot-scope="{row}">
            <div v-if="!row.flag"
                 @click="changeInput(row)"> {{row.score}}</div>
            <el-input v-model="row.score"
                      placeholder=""
                      v-if="row.flag"
                      size="mini"
                      @blur="blurInput(row)"
                      ref="input"></el-input>
          </template>
        </el-table-column>
        <el-table-column property="score"
                         label="操作"
                         width="width">
          <template slot-scope="{row}">
            <el-popconfirm confirm-button-text='确定'
                           cancel-button-text='取消'
                           icon="el-icon-info"
                           icon-color="red"
                           title="确定删除吗？"
                           @confirm="deleteScore(row)">
              <el-button type="danger"
                         size="small"
                         slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 学生信息 编辑 -->
    <el-dialog title="编辑学生基本信息"
               :visible.sync="dialogFormVisible">
      <el-form :model="row"
               inline
               :rules="rules"
               ref="ruleForm">
        <el-form-item label="学号"
                      :label-width="formLabelWidth"
                      prop="stuno">
          <el-input v-model.number="row.stuno"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="姓名"
                      :label-width="formLabelWidth"
                      prop="stuname">
          <el-input v-model="row.stuname"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="性别"
                      :label-width="formLabelWidth"
                      prop="stusex">
          <el-input v-model="row.stusex"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="年龄"
                      :label-width="formLabelWidth"
                      prop="stuage">
          <el-input v-model.number="row.stuage"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="班级"
                      :label-width="formLabelWidth"
                      prop="stuclass">
          <el-input v-model="row.stuclass"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="confirmUpdate('ruleForm',scene)">确 定</el-button>
        <el-button @click="cancleUpdate">取 消</el-button>

      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { reqStuscore, reqSearchStu } from '@/api/stuInfo'
export default {
  name: 'StudentInfo',
  data() {
    return {
      loading: false,//loading效果
      searchStu: '',
      dialogTableVisible: false,
      dialogFormVisible: false,
      stuScore: [],//学生成绩信息
      formLabelWidth: '120px',
      row: {},
      rules: {
        stuno: [
          { required: true, message: '学号不能为空', trigger: 'blur' },
        ],
        stuname: [
          { required: true, message: '姓名不能为空', trigger: 'blur' },
        ],
        stusex: [
          { required: true, message: '性别不能为空', trigger: 'blur' },
        ],
        stuage: [
          { required: true, message: '年龄不能为空', trigger: 'blur' },
        ],
        stuclass: [
          { required: true, message: '班级不能为空', trigger: 'blur' },
        ]
      },
      scene: 0,//0代表添加，1代表编辑
      selectedIds: [], // 所有选择的user的id的数组
      curpage: 1,//分页器当前页数
      limit: 3//每页大小
    }
  },
  mounted() {
    this.getStuInfo()
  },
  computed: {
    ...mapState('stuInfo', ['stuInfo', 'total'])
  },
  methods: {
    // 获取学生数据
    getStuInfo() {
      this.loading = true
      this.$store.dispatch('stuInfo/getStuInfo', { pagenum: this.curpage, pagesize: this.limit })
      setTimeout(() => {
        this.loading = false
      }, 500);
    },
    async scoreInfo(row) {
      this.dialogTableVisible = true
      try {
        let result = await reqStuscore(row.sid)
        this.stuScore = [...result.stuScore]
        this.stuScore.forEach(item => {
          this.$set(item, 'flag', false);
        })
      } catch (error) {
        this.$message.err('查询失败')
      }
    },
    // 切换输入框
    changeInput(row) {
      row.flag = !row.flag
      this.$nextTick(() => {
        this.$refs.input.focus()
      })
    },
    // 失去焦点的回调
    blurInput(row) {
      row.flag = !row.flag
      this.$message({ type: 'success', message: '修改成功，已保存' })
    },
    // 删除成绩
    deleteScore(row) {
      this.stuScore = this.stuScore.filter(item => {
        return item.id !== row.id
      })
    },
    // 更新学生信息
    updateStu(row) {
      this.dialogFormVisible = true
      this.row = row
      this.scene = 1
    },
    // 确定更新学生信息
    confirmUpdate(formName, scene) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (scene === 0) {
            this.stuInfo.unshift(this.row)
          }
          this.dialogFormVisible = false
          this.$message({ type: 'success', message: scene === 0 ? '添加成功' : '编辑成功' })
          this.row = {}
        } else {
          this.$message.err('不能为空')
          return false;
        }
      });
    },
    // 取消更新学生信息
    cancleUpdate() {
      this.dialogFormVisible = false
      this.row = {}
    },
    // 删除学生信息
    deleteStu(row) {
      let deleteOne = this.stuInfo.filter(item => {
        return item.sid !== row.sid
      })
      this.$store.commit('stuInfo/GETSTUINFO', deleteOne)
      this.$message({ type: 'success', message: '删除成功' })
    },
    // 添加学生
    addStu() {
      this.scene = 0
      this.dialogFormVisible = true
    },
    // 查询学生
    async search() {
      if (this.searchStu === '') {
        this.$message('请输入查询的学生名字')
      } else {
        let result = await reqSearchStu(this.searchStu)
        if (result.code === 200) {
          this.$store.commit('stuInfo/GETSTUINFO', result.searchResults)
        }
      }
    },
    // 清空查询框
    clearInput() {
      this.searchStu = ''
      this.getStuInfo()
    },
    // 列表选中状态发生改变的监听回调
    handleSelectionChange(selection) {
      this.selectedIds = selection.map(item => item.sid)
    },
    // 批量删除
    deleteInBatches() {
      this.$confirm('此操作将永久删除学生信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let selected = this.stuInfo.filter(item => {
          return this.selectedIds.indexOf(item.sid) === -1
        })
        this.$store.commit('stuInfo/GETSTUINFO', selected)
        this.$message({
          type: 'success',
          message: '删除成功'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 分页大小切换
    handleSizeChange(val) {
      this.limit = val
      this.getStuInfo()
    },
    // 当前页
    handleCurrentChange(val) {
      this.curpage = val
      this.getStuInfo()
    }
  }
}
</script>
