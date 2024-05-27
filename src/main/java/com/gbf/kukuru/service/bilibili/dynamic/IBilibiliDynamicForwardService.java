package com.gbf.kukuru.service.bilibili.dynamic;

import com.gbf.kukuru.entity.BilibiliDynamic;

/**
 * B站动态转发功能 服务接口类
 *
 * @author ginoko
 * @since 2022-05-11
 */
public interface IBilibiliDynamicForwardService {

    /**
     * 根据B站用户ID获取最新的动态
     *
     * @param uid B站用户ID
     * @return B站动态
     */
    BilibiliDynamic getNewestDynamic(Long uid);

    /**
     * 根据B站用户ID和偏移值获取下一条动态
     *
     * @param uid    B站用户ID
     * @param offset 偏移值
     * @return B站动态
     */
    BilibiliDynamic getNextDynamic(Long uid, Long offset);
}
