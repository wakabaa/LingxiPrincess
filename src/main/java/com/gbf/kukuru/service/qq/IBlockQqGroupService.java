package com.gbf.kukuru.service.qq;

import com.gbf.kukuru.entity.BlockQqGroup;

import java.util.List;

/**
 * QQ群黑名单 服务接口
 *
 * @author ginoko
 * @since 2022-08-17
 */
public interface IBlockQqGroupService {

    /**
     * 该QQ群是否被封禁
     *
     * @param groupId 群ID
     * @return true 被封禁; false 未被封禁
     */
    boolean isBlockedQqGroup(long groupId);

    /**
     * 获取被封禁的QQ群列表
     *
     * @return QQ群列表
     */
    List<BlockQqGroup> getBlockList();
}
