package com.gbf.kukuru.mapper.gbf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gbf.kukuru.entity.UniteAndFightInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 古战场信息 Mapper接口
 *
 * @author ginoko
 * @since 2022-08-06
 */
@Mapper
public interface UniteAndFightInfoMapper extends BaseMapper<UniteAndFightInfo> {

    /**
     * 找到当前正在进行的古战场信息, 若没有, 则获取下一次古战场的信息
     *
     * @return 古战场信息实体
     */
    UniteAndFightInfo selectCurrentOrNext();
}
