package com.gbf.kukuru.service.gbf;

import com.gbf.kukuru.dto.GbfSoloPointDTO;
import com.gbf.kukuru.entity.UniteAndFightInfo;

import java.util.List;

/**
 * GBF团成员 服务实现类
 *
 * @author ginoko
 * @since 2022-06-24
 */
public interface IGbfGuildMemberService {

    /**
     * 查询骑空团成员列表
     *
     * @param groupId         群ID
     * @param uniteAndFightId 古战场ID
     * @return 骑空团成员列表
     */
    List<StringBuilder> getMemberInfoByQQGroupId(long groupId, long uniteAndFightId);

    /**
     * 将用户加入贡献监听中
     *
     * @param groupId  qq群ID
     * @param userId   账号ID
     * @param userName 昵称
     * @return 加入的结果
     */
    String addToWatchList(long groupId, Long userId, String userName);

    /**
     * 获取所有成员的贡献
     *
     * @param groupIdList  机器人所在群列表
     * @param info         古战场信息实体
     * @param enableUpdate 是否更新贡献数据
     * @return 个排贡献列表
     */
    List<GbfSoloPointDTO> getAllPoint(List<Long> groupIdList, UniteAndFightInfo info, boolean enableUpdate);

    /**
     * 获取个排档位的贡献
     *
     * @param groupIdList 群ID列表
     * @param info        古战场信息实体
     * @return 档位排名贡献
     */
    List<GbfSoloPointDTO> getRankTotalPoint(List<Long> groupIdList, UniteAndFightInfo info);
}
