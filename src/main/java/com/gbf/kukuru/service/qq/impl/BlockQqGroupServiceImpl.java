package com.gbf.kukuru.service.qq.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gbf.kukuru.entity.BlockQqGroup;
import com.gbf.kukuru.mapper.BlockQqGroupMapper;
import com.gbf.kukuru.service.qq.IBlockQqGroupService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * QQ群黑名单 服务实现类
 *
 * @author ginoko
 * @since 2022-08-17
 */
@Component
public class BlockQqGroupServiceImpl implements IBlockQqGroupService {

    @Resource
    private BlockQqGroupMapper mapper;

    @Override
    public boolean isBlockedQqGroup(long groupId) {
        return mapper.selectCount(
                Wrappers.<BlockQqGroup>lambdaQuery()
                        .eq(BlockQqGroup::getGroupId, groupId)
        ) > 0;
    }

    @Override
    public List<BlockQqGroup> getBlockList() {
        return mapper.selectList(Wrappers.emptyWrapper());
    }
}
