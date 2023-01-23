package com.lzl.gulimall.coupon.dao;

import com.lzl.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author liuzile
 * @email liuzile@gmail.com
 * @date 2023-01-15 10:54:41
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
