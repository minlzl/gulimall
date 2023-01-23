package com.lzl.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.common.utils.PageUtils;
import com.lzl.gulimall.coupon.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author liuzile
 * @email liuzile@gmail.com
 * @date 2023-01-15 10:54:41
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

