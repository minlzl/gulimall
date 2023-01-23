package com.lzl.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.common.utils.PageUtils;
import com.lzl.gulimall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author liuzile
 * @email liuzile@gmail.com
 * @date 2023-01-15 11:03:26
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

