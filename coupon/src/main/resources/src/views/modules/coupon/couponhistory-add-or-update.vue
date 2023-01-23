<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" @keyup.enter.native="dataFormSubmit()" label-width="80px" ref="dataForm">
    <el-form-item label="优惠券id" prop="couponId">
      <el-input placeholder="优惠券id" v-model="dataForm.couponId"></el-input>
    </el-form-item>
    <el-form-item label="会员id" prop="memberId">
      <el-input placeholder="会员id" v-model="dataForm.memberId"></el-input>
    </el-form-item>
    <el-form-item label="会员名字" prop="memberNickName">
      <el-input placeholder="会员名字" v-model="dataForm.memberNickName"></el-input>
    </el-form-item>
    <el-form-item label="获取方式[0->后台赠送；1->主动领取]" prop="getType">
      <el-input placeholder="获取方式[0->后台赠送；1->主动领取]" v-model="dataForm.getType"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input placeholder="创建时间" v-model="dataForm.createTime"></el-input>
    </el-form-item>
    <el-form-item label="使用状态[0->未使用；1->已使用；2->已过期]" prop="useType">
      <el-input placeholder="使用状态[0->未使用；1->已使用；2->已过期]" v-model="dataForm.useType"></el-input>
    </el-form-item>
    <el-form-item label="使用时间" prop="useTime">
      <el-input placeholder="使用时间" v-model="dataForm.useTime"></el-input>
    </el-form-item>
    <el-form-item label="订单id" prop="orderId">
      <el-input placeholder="订单id" v-model="dataForm.orderId"></el-input>
    </el-form-item>
    <el-form-item label="订单号" prop="orderSn">
      <el-input placeholder="订单号" v-model="dataForm.orderSn"></el-input>
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
          couponId: '',
          memberId: '',
          memberNickName: '',
          getType: '',
          createTime: '',
          useType: '',
          useTime: '',
          orderId: '',
          orderSn: ''
        },
        dataRule: {
          couponId: [
            { required: true, message: '优惠券id不能为空', trigger: 'blur' }
          ],
          memberId: [
            { required: true, message: '会员id不能为空', trigger: 'blur' }
          ],
          memberNickName: [
            { required: true, message: '会员名字不能为空', trigger: 'blur' }
          ],
          getType: [
            { required: true, message: '获取方式[0->后台赠送；1->主动领取]不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          useType: [
            { required: true, message: '使用状态[0->未使用；1->已使用；2->已过期]不能为空', trigger: 'blur' }
          ],
          useTime: [
            { required: true, message: '使用时间不能为空', trigger: 'blur' }
          ],
          orderId: [
            { required: true, message: '订单id不能为空', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: '订单号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/couponhistory/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.couponId = data.couponHistory.couponId
                this.dataForm.memberId = data.couponHistory.memberId
                this.dataForm.memberNickName = data.couponHistory.memberNickName
                this.dataForm.getType = data.couponHistory.getType
                this.dataForm.createTime = data.couponHistory.createTime
                this.dataForm.useType = data.couponHistory.useType
                this.dataForm.useTime = data.couponHistory.useTime
                this.dataForm.orderId = data.couponHistory.orderId
                this.dataForm.orderSn = data.couponHistory.orderSn
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
              url: this.$http.adornUrl(`/coupon/couponhistory/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'couponId': this.dataForm.couponId,
                'memberId': this.dataForm.memberId,
                'memberNickName': this.dataForm.memberNickName,
                'getType': this.dataForm.getType,
                'createTime': this.dataForm.createTime,
                'useType': this.dataForm.useType,
                'useTime': this.dataForm.useTime,
                'orderId': this.dataForm.orderId,
                'orderSn': this.dataForm.orderSn
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
