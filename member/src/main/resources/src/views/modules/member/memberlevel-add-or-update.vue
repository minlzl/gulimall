<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" @keyup.enter.native="dataFormSubmit()" label-width="80px" ref="dataForm">
    <el-form-item label="等级名称" prop="name">
      <el-input placeholder="等级名称" v-model="dataForm.name"></el-input>
    </el-form-item>
    <el-form-item label="等级需要的成长值" prop="growthPoint">
      <el-input placeholder="等级需要的成长值" v-model="dataForm.growthPoint"></el-input>
    </el-form-item>
    <el-form-item label="是否为默认等级[0->不是；1->是]" prop="defaultStatus">
      <el-input placeholder="是否为默认等级[0->不是；1->是]" v-model="dataForm.defaultStatus"></el-input>
    </el-form-item>
    <el-form-item label="免运费标准" prop="freeFreightPoint">
      <el-input placeholder="免运费标准" v-model="dataForm.freeFreightPoint"></el-input>
    </el-form-item>
    <el-form-item label="每次评价获取的成长值" prop="commentGrowthPoint">
      <el-input placeholder="每次评价获取的成长值" v-model="dataForm.commentGrowthPoint"></el-input>
    </el-form-item>
    <el-form-item label="是否有免邮特权" prop="priviledgeFreeFreight">
      <el-input placeholder="是否有免邮特权" v-model="dataForm.priviledgeFreeFreight"></el-input>
    </el-form-item>
    <el-form-item label="是否有会员价格特权" prop="priviledgeMemberPrice">
      <el-input placeholder="是否有会员价格特权" v-model="dataForm.priviledgeMemberPrice"></el-input>
    </el-form-item>
    <el-form-item label="是否有生日特权" prop="priviledgeBirthday">
      <el-input placeholder="是否有生日特权" v-model="dataForm.priviledgeBirthday"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="note">
      <el-input placeholder="备注" v-model="dataForm.note"></el-input>
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
          name: '',
          growthPoint: '',
          defaultStatus: '',
          freeFreightPoint: '',
          commentGrowthPoint: '',
          priviledgeFreeFreight: '',
          priviledgeMemberPrice: '',
          priviledgeBirthday: '',
          note: ''
        },
        dataRule: {
          name: [
            { required: true, message: '等级名称不能为空', trigger: 'blur' }
          ],
          growthPoint: [
            { required: true, message: '等级需要的成长值不能为空', trigger: 'blur' }
          ],
          defaultStatus: [
            { required: true, message: '是否为默认等级[0->不是；1->是]不能为空', trigger: 'blur' }
          ],
          freeFreightPoint: [
            { required: true, message: '免运费标准不能为空', trigger: 'blur' }
          ],
          commentGrowthPoint: [
            { required: true, message: '每次评价获取的成长值不能为空', trigger: 'blur' }
          ],
          priviledgeFreeFreight: [
            { required: true, message: '是否有免邮特权不能为空', trigger: 'blur' }
          ],
          priviledgeMemberPrice: [
            { required: true, message: '是否有会员价格特权不能为空', trigger: 'blur' }
          ],
          priviledgeBirthday: [
            { required: true, message: '是否有生日特权不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberlevel/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.memberLevel.name
                this.dataForm.growthPoint = data.memberLevel.growthPoint
                this.dataForm.defaultStatus = data.memberLevel.defaultStatus
                this.dataForm.freeFreightPoint = data.memberLevel.freeFreightPoint
                this.dataForm.commentGrowthPoint = data.memberLevel.commentGrowthPoint
                this.dataForm.priviledgeFreeFreight = data.memberLevel.priviledgeFreeFreight
                this.dataForm.priviledgeMemberPrice = data.memberLevel.priviledgeMemberPrice
                this.dataForm.priviledgeBirthday = data.memberLevel.priviledgeBirthday
                this.dataForm.note = data.memberLevel.note
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
              url: this.$http.adornUrl(`/member/memberlevel/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'growthPoint': this.dataForm.growthPoint,
                'defaultStatus': this.dataForm.defaultStatus,
                'freeFreightPoint': this.dataForm.freeFreightPoint,
                'commentGrowthPoint': this.dataForm.commentGrowthPoint,
                'priviledgeFreeFreight': this.dataForm.priviledgeFreeFreight,
                'priviledgeMemberPrice': this.dataForm.priviledgeMemberPrice,
                'priviledgeBirthday': this.dataForm.priviledgeBirthday,
                'note': this.dataForm.note
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
