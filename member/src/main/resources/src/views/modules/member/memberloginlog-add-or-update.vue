<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" @keyup.enter.native="dataFormSubmit()" label-width="80px" ref="dataForm">
    <el-form-item label="member_id" prop="memberId">
      <el-input placeholder="member_id" v-model="dataForm.memberId"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input placeholder="创建时间" v-model="dataForm.createTime"></el-input>
    </el-form-item>
    <el-form-item label="ip" prop="ip">
      <el-input placeholder="ip" v-model="dataForm.ip"></el-input>
    </el-form-item>
    <el-form-item label="city" prop="city">
      <el-input placeholder="city" v-model="dataForm.city"></el-input>
    </el-form-item>
    <el-form-item label="登录类型[1-web，2-app]" prop="loginType">
      <el-input placeholder="登录类型[1-web，2-app]" v-model="dataForm.loginType"></el-input>
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
          ip: '',
          city: '',
          loginType: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member_id不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          ip: [
            { required: true, message: 'ip不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: 'city不能为空', trigger: 'blur' }
          ],
          loginType: [
            { required: true, message: '登录类型[1-web，2-app]不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberloginlog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.memberLoginLog.memberId
                this.dataForm.createTime = data.memberLoginLog.createTime
                this.dataForm.ip = data.memberLoginLog.ip
                this.dataForm.city = data.memberLoginLog.city
                this.dataForm.loginType = data.memberLoginLog.loginType
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
              url: this.$http.adornUrl(`/member/memberloginlog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'createTime': this.dataForm.createTime,
                'ip': this.dataForm.ip,
                'city': this.dataForm.city,
                'loginType': this.dataForm.loginType
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
