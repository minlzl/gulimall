package com.lzl.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.common.utils.PageUtils;
import com.lzl.gulimall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author liuzile
 * @email liuzile@gmail.com
 * @date 2023-01-15 11:40:33
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

