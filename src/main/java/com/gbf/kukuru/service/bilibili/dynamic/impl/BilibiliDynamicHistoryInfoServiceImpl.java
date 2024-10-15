package com.gbf.kukuru.service.bilibili.dynamic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gbf.kukuru.entity.BilibiliDynamicHistoryInfo;
import com.gbf.kukuru.mapper.bilibili.dynamic.BiliBiliDynamicHistoryInfoMapper;
import com.gbf.kukuru.service.bilibili.dynamic.IBilibiliDynamicHistoryInfoService;

/**
 * B站动态历史信息 服务实现类
 *
 * @author ginoko
 * @since 2022-05-16
 */
@Component
public class BilibiliDynamicHistoryInfoServiceImpl implements IBilibiliDynamicHistoryInfoService {
    @Autowired
    private BiliBiliDynamicHistoryInfoMapper mapper;

    @Override
    public List<BilibiliDynamicHistoryInfo> getAll() {
        return mapper.selectList(new QueryWrapper<>());
    }
}
