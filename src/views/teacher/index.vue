<template>
  <div class="app-container">
    <!-- 表单 -->
    <el-form inline>
      <!-- 表单元素 -->

      <!-- 查询与清空的按钮 -->
      <el-form-item class="fr">
        <el-button type="primary"
                   icon="el-icon-search"
                   size="small"
                   @click="selectTea">查询</el-button>
      </el-form-item>
      <el-form-item class="fr">
        <el-input v-model="searchTea"
                  placeholder="教师名"
                  size="small" />
      </el-form-item>
      <el-form-item>
        <div style="margin-bottom: 10px">
          <!-- 添加与批量添加按钮 -->
          <el-button type="primary"
                     icon="el-icon-plus"
                     size="small"
                     @click="addTea">添加教师</el-button>
          <el-button type="danger"
                     size="small"
                     @click="deleteInBatches">批量删除</el-button>
        </div>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading"
              :data="teaInfo"
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
      <el-table-column prop="tno"
                       label="教师编号"
                       width="180">
      </el-table-column>
      <el-table-column prop="tname"
                       label="教师姓名"
                       width="160">
      </el-table-column>
      <el-table-column prop="tsex"
                       label="教师性别"
                       width="130">
      </el-table-column>
      <el-table-column prop="tdep"
                       label="所属院系"
                       width="130">
      </el-table-column>
      <el-table-column prop="ttel"
                       label="联系方式"
                       width="160">
      </el-table-column>
      <el-table-column prop="sid"
                       label="操作"
                       width="width">
        <template slot-scope="{row}">
          <el-button type="warning"
                     icon="el-icon-edit"
                     size="mini"
                     title="编辑"
                     @click="updateTea(row)"></el-button>
          <el-popconfirm confirm-button-text='确定'
                         cancel-button-text='取消'
                         icon="el-icon-info"
                         icon-color="red"
                         title="确定删除吗？"
                         @confirm="deleteOne(row)">
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

    <!-- 学生信息 编辑 -->
    <el-dialog title="编辑学生基本信息"
               :visible.sync="dialogFormVisible">
      <el-form :model="row"
               inline
               ref="ruleForm">
        <el-form-item label="教师编号"
                      :label-width="formLabelWidth"
                      prop="tno">
          <el-input v-model.number="row.tno"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="教师姓名"
                      :label-width="formLabelWidth"
                      prop="tname">
          <el-input v-model="row.tname"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="教师性别"
                      :label-width="formLabelWidth"
                      prop="tsex">
          <el-input v-model="row.tsex"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="所属院系"
                      :label-width="formLabelWidth"
                      prop="tdep">
          <el-input v-model.number="row.tdep"
                    autocomplete="off"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="联系方式"
                      :label-width="formLabelWidth"
                      prop="ttel">
          <el-input v-model="row.ttel"
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
import { reqTeaInfo, reqSelectTea } from '@/api/teacher'
export default {
  name: 'Teacher',
  data() {
    return {
      loading: false,
      teaInfo: [],
      searchTea: '',
      dialogFormVisible: false,
      formLabelWidth: '120px',
      row: {},
      scene: 0,
      selectedIds: []
    }
  },
  methods: {
    async getData() {
      this.loading = true
      let result = await reqTeaInfo()
      this.teaInfo = result.teaInfo
      setTimeout(() => {
        this.loading = false
      }, 500);
    },
    updateTea(row) {
      this.dialogFormVisible = true
      this.row = row
      this.scene = 1
    },
    confirmUpdate(formName, scene) {
      if (scene === 0) {
        this.teaInfo.unshift(this.row)
      }
      this.dialogFormVisible = false
      this.$message({ type: 'success', message: scene === 0 ? '添加成功' : '编辑成功' })
      this.row = {}
    },
    cancleUpdate() {
      this.dialogFormVisible = false
      this.row = {}
    },
    deleteOne(row) {
      this.teaInfo = this.teaInfo.filter(item => {
        return item.tid !== row.tid
      })
      this.$message({ type: 'success', message: '删除成功' })
    },
    handleSelectionChange(selection) {
      this.selectedIds = selection.map(item => item.tid)
    },
    deleteInBatches() {
      this.$confirm('此操作将永久删除教师信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.teaInfo = this.teaInfo.filter(item => {
          return this.selectedIds.indexOf(item.tid) === -1
        })
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
    addTea() {
      this.scene = 0
      this.dialogFormVisible = true
    },
    async selectTea() {
      let result = await reqSelectTea(this.searchTea)
      this.teaInfo = result.searchResults
    }
  },
  mounted() {
    this.getData()
  }
}
</script>

<style  scoped>
.fr {
  float: right;
}
</style>

