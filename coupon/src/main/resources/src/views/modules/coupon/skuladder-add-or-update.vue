<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" @keyup.enter.native="dataFormSubmit()" label-width="80px" ref="dataForm">
    <el-form-item label="spu_id" prop="skuId">
      <el-input placeholder="spu_id" v-model="dataForm.skuId"></el-input>
    </el-form-item>
    <el-form-item label="满几件" prop="fullCount">
      <el-input placeholder="满几件" v-model="dataForm.fullCount"></el-input>
    </el-form-item>
    <el-form-item label="打几折" prop="discount">
      <el-input placeholder="打几折" v-model="dataForm.discount"></el-input>
    </el-form-item>
    <el-form-item label="折后价" prop="price">
      <el-input placeholder="折后价" v-model="dataForm.price"></el-input>
    </el-form-item>
    <el-form-item label="是否叠加其他优惠[0-不可叠加，1-可叠加]" prop="addOther">
      <el-input placeholder="是否叠加其他优惠[0-不可叠加，1-可叠加]" v-model="dataForm.addOther"></el-input>
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
          skuId: '',
          fullCount: '',
          discount: '',
          price: '',
          addOther: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: 'spu_id不能为空', trigger: 'blur' }
          ],
          fullCount: [
            { required: true, message: '满几件不能为空', trigger: 'blur' }
          ],
          discount: [
            { required: true, message: '打几折不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '折后价不能为空', trigger: 'blur' }
          ],
          addOther: [
            { required: true, message: '是否叠加其他优惠[0-不可叠加，1-可叠加]不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/skuladder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.skuLadder.skuId
                this.dataForm.fullCount = data.skuLadder.fullCount
                this.dataForm.discount = data.skuLadder.discount
                this.dataForm.price = data.skuLadder.price
                this.dataForm.addOther = data.skuLadder.addOther
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
              url: this.$http.adornUrl(`/coupon/skuladder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'fullCount': this.dataForm.fullCount,
                'discount': this.dataForm.discount,
                'price': this.dataForm.price,
                'addOther': this.dataForm.addOther
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
