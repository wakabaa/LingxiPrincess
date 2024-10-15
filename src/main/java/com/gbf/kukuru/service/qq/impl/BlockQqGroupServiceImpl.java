package com.gbf.kukuru.service.qq.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gbf.kukuru.entity.BlockQqGroup;
import com.gbf.kukuru.service.qq.IBlockQqGroupService;

/**
 * QQ群黑名单 服务实现类
 *
 * @author ginoko
 * @since 2022-08-17
 */
@Component
public class BlockQqGroupServiceImpl implements IBlockQqGroupService {

	@Override
	public boolean isBlockedQqGroup(long groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BlockQqGroup> getBlockList() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Autowired
//    private BlockQqGroupMapper mapper;
//
//    @Override
//    public boolean isBlockedQqGroup(long groupId) {
//        return mapper.selectCount(
//                Wrappers.<BlockQqGroup>lambdaQuery()
//                        .eq(BlockQqGroup::getGroupId, groupId)
//        ) > 0;
//    }
//
//    @Override
//    public List<BlockQqGroup> getBlockList() {
//        return mapper.selectList(Wrappers.emptyWrapper());
//    }
}
