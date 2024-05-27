package com.gbf.kukuru.mapper.gbf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gbf.kukuru.entity.GbfGuildMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * GBF团成员 Mapper接口类
 *
 * @author ginoko
 * @since 2022-06-24
 */
@Mapper
public interface GbfGuildMemberMapper extends BaseMapper<GbfGuildMember> {
    /**
     * 根据QQ群ID获取对应gbf团员列表
     *
     * @param groupId QQ群ID
     * @return gbf团员列表
     */
    List<GbfGuildMember> selectListByQqGroupId(Long groupId);

    /**
     * 批量更新gbf团成员列表
     *
     * @param updateSql 更新的SQL语句
     * @param ids       团成员的游戏ID字符串
     */
    void updateCase(String updateSql, String ids);

    /**
     * 批量新增gbf团成员列表
     *
     * @param insertSql 新增的SQL语句
     */
    void insertBatch(String insertSql);
}
