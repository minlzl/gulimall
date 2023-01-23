package com.lzl.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.common.utils.PageUtils;
import com.lzl.gulimall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author liuzile
 * @email liuzile@gmail.com
 * @date 2023-01-15 11:03:27
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

