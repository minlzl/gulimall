package com.lzl.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.common.utils.PageUtils;
import com.lzl.gulimall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author liuzile
 * @email liuzile@gmail.com
 * @date 2023-01-15 11:28:24
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

