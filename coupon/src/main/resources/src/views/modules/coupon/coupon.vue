<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input clearable placeholder="参数名" v-model="dataForm.key"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button @click="addOrUpdateHandle()" type="primary" v-if="isAuth('coupon:coupon:save')">新增</el-button>
        <el-button :disabled="dataListSelections.length <= 0" @click="deleteHandle()" type="danger" v-if="isAuth('coupon:coupon:delete')">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      @selection-change="selectionChangeHandle"
      border
      style="width: 100%;"
      v-loading="dataListLoading">
      <el-table-column
        align="center"
        header-align="center"
        type="selection"
        width="50">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="id"
        prop="id">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]"
        prop="couponType">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="优惠券图片"
        prop="couponImg">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="优惠卷名字"
        prop="couponName">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="数量"
        prop="num">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="金额"
        prop="amount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="每人限领张数"
        prop="perLimit">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="使用门槛"
        prop="minPoint">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="开始时间"
        prop="startTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="结束时间"
        prop="endTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="使用类型[0->全场通用；1->指定分类；2->指定商品]"
        prop="useType">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="备注"
        prop="note">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="发行数量"
        prop="publishCount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="已使用数量"
        prop="useCount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="领取数量"
        prop="receiveCount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="可以领取的开始日期"
        prop="enableStartTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="可以领取的结束日期"
        prop="enableEndTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="优惠码"
        prop="code">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="可以领取的会员等级[0->不限等级，其他-对应等级]"
        prop="memberLevel">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="发布状态[0-未发布，1-已发布]"
        prop="publish">
      </el-table-column>
      <el-table-column
        align="center"
        fixed="right"
        header-align="center"
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button @click="addOrUpdateHandle(scope.row.id)" size="small" type="text">修改</el-button>
          <el-button @click="deleteHandle(scope.row.id)" size="small" type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="pageIndex"
      :page-size="pageSize"
      :page-sizes="[10, 20, 50, 100]"
      :total="totalPage"
      @current-change="currentChangeHandle"
      @size-change="sizeChangeHandle"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update @refreshDataList="getDataList" ref="addOrUpdate" v-if="addOrUpdateVisible"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './coupon-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/coupon/coupon/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/coupon/coupon/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
