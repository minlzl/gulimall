package com.lzl.gulimall.order.dao;

import com.lzl.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author liuzile
 * @email liuzile@gmail.com
 * @date 2023-01-15 11:28:24
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
