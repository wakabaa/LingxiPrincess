package com.gbf.kukuru.service.bilibili.dynamic;

import com.gbf.kukuru.entity.BilibiliDynamicHistoryInfo;

import java.util.List;

/**
 * B站动态历史信息 服务接口类
 *
 * @author ginoko
 * @since 2022-05-16
 */
public interface IBilibiliDynamicHistoryInfoService {

    /**
     * 获取全部关注的B站用户的动态信息
     *
     * @return 动态信息列表
     */
    List<BilibiliDynamicHistoryInfo> getAll();
}
