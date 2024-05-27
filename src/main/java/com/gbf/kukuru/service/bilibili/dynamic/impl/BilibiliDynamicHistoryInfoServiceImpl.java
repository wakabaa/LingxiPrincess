package com.gbf.kukuru.service.bilibili.dynamic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gbf.kukuru.entity.BilibiliDynamicHistoryInfo;
import com.gbf.kukuru.mapper.bilibili.dynamic.BiliBiliDynamicHistoryInfoMapper;
import com.gbf.kukuru.service.bilibili.dynamic.IBilibiliDynamicHistoryInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * B站动态历史信息 服务实现类
 *
 * @author ginoko
 * @since 2022-05-16
 */
@Component
public class BilibiliDynamicHistoryInfoServiceImpl implements IBilibiliDynamicHistoryInfoService {
    @Resource
    private BiliBiliDynamicHistoryInfoMapper mapper;

    @Override
    public List<BilibiliDynamicHistoryInfo> getAll() {
        return mapper.selectList(new QueryWrapper<>());
    }
}
