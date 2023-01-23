<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" @keyup.enter.native="dataFormSubmit()" label-width="80px" ref="dataForm">
    <el-form-item label="member_id" prop="memberId">
      <el-input placeholder="member_id" v-model="dataForm.memberId"></el-input>
    </el-form-item>
    <el-form-item label="create_time" prop="createTime">
      <el-input placeholder="create_time" v-model="dataForm.createTime"></el-input>
    </el-form-item>
    <el-form-item label="改变的值（正负计数）" prop="changeCount">
      <el-input placeholder="改变的值（正负计数）" v-model="dataForm.changeCount"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="note">
      <el-input placeholder="备注" v-model="dataForm.note"></el-input>
    </el-form-item>
    <el-form-item label="积分来源[0-购物，1-管理员修改]" prop="sourceType">
      <el-input placeholder="积分来源[0-购物，1-管理员修改]" v-model="dataForm.sourceType"></el-input>
    </el-form-item>
    </el-form>
    <span class="dialog-footer" slot="footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button @click="dataFormSubmit()" type="primary">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          memberId: '',
          createTime: '',
          changeCount: '',
          note: '',
          sourceType: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member_id不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'create_time不能为空', trigger: 'blur' }
          ],
          changeCount: [
            { required: true, message: '改变的值（正负计数）不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          sourceType: [
            { required: true, message: '积分来源[0-购物，1-管理员修改]不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/member/growthchangehistory/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.growthChangeHistory.memberId
                this.dataForm.createTime = data.growthChangeHistory.createTime
                this.dataForm.changeCount = data.growthChangeHistory.changeCount
                this.dataForm.note = data.growthChangeHistory.note
                this.dataForm.sourceType = data.growthChangeHistory.sourceType
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/member/growthchangehistory/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'createTime': this.dataForm.createTime,
                'changeCount': this.dataForm.changeCount,
                'note': this.dataForm.note,
                'sourceType': this.dataForm.sourceType
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
