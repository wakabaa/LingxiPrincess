package com.gbf.kukuru.mapper.Spirit;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gbf.kukuru.entity.SpiritEventEntity;

/**
 * 梦幻活动 Mapper接口类
 *
 * @author wakaba
 * @since 2024-05-30
 */
@Mapper
public interface SpiritMapper extends BaseMapper<SpiritEventEntity> {
    /**
     * 查询本周活动
     *
     * @return 本周活动
     */
    List<SpiritEventEntity> selectSpiritEvent();

}
