package com.gbf.kukuru.service.gbf.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gbf.kukuru.constant.CommandConstant;
import com.gbf.kukuru.dto.GbfSoloPointDTO;
import com.gbf.kukuru.entity.GbfGuildAndQqGroupBindInfo;
import com.gbf.kukuru.entity.GbfGuildMember;
import com.gbf.kukuru.entity.GbfMemberAndQqGroupBindInfo;
import com.gbf.kukuru.entity.UniteAndFightInfo;
import com.gbf.kukuru.entity.webJson.*;
import com.gbf.kukuru.mapper.gbf.GbfGuildAndQqGroupBindInfoMapper;
import com.gbf.kukuru.mapper.gbf.GbfGuildMemberMapper;
import com.gbf.kukuru.mapper.gbf.GbfMemberAndQqGroupBindInfoMapper;
import com.gbf.kukuru.service.gbf.IGbfGuildMemberService;
import com.gbf.kukuru.util.UniteAndFightUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * GBF团成员 服务实现类
 *
 * @author ginoko
 * @since 2022-06-24
 */
@Slf4j
@Component
@EnableAsync(proxyTargetClass = true)
public class GbfGuildMemberServiceImpl implements IGbfGuildMemberService {

    @Resource
    private GbfGuildMemberMapper guildMemberMapper;
    @Resource
    private GbfGuildAndQqGroupBindInfoMapper guildAndQqGroupBindInfoMapper;
    @Resource
    private GbfMemberAndQqGroupBindInfoMapper memberAndQqGroupBindInfoMapper;

    private Map<String, StringBuilder> getUpdateCaseKeyMap() {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("guild_id", new StringBuilder("guild_id = case id "));
        map.put("nick_name", new StringBuilder("nick_name = case id "));
        map.put("user_rank", new StringBuilder("user_rank = case id "));
        map.put("position", new StringBuilder("position = case id "));
        map.put("last_rank", new StringBuilder("last_rank = case id "));
        map.put("last_point", new StringBuilder("last_point = case id "));
        map.put("update_time", new StringBuilder("update_time = case id "));
        return map;
    }

    private String getValueOfUpdateCase(String column, GbfGuildMember member) {
        String data = "";
        switch (column) {
            case "guild_id":
                data = String.valueOf(member.getGuildId());
                break;
            case "nick_name":
                data = member.getNickName();
                break;
            case "user_rank":
                data = String.valueOf(member.getUserRank());
                break;
            case "position":
                data = member.getPosition();
                break;
            case "last_rank":
                data = String.valueOf(member.getLastRank());
                break;
            case "last_point":
                data = String.valueOf(member.getLastPoint());
                break;
            case "update_time":
                data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getUpdateTime() == null ? 0L : member.getUpdateTime());
                break;
            default:
                /* do nothing */
                break;
        }
        return data;
    }

    /**
     * 批量更新团成员列表
     *
     * @param memberList 团成员列表
     */
    @Async
    public void updateCase(List<GbfGuildMember> memberList) {
        Map<String, StringBuilder> keyMap = getUpdateCaseKeyMap();
        List<String> ids = new ArrayList<>();
        memberList.forEach(member -> {
            for (String column : keyMap.keySet()) {
                keyMap.get(column).append("when '").append(member.getId())
                        .append("' then '").append(getValueOfUpdateCase(column, member))
                        .append("'");
            }
            ids.add("'" + member.getId() + "'");
        });
        StringBuilder builder = new StringBuilder();
        for (String column : keyMap.keySet()) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(keyMap.get(column).toString()).append(" end");
        }
        String idsString = ids.stream().collect(Collectors.joining(",", "(", ")"));
        guildMemberMapper.updateCase(builder.toString(), idsString);
    }

    private void insertBatch(List<GbfGuildMember> memberList) {
        StringBuilder builder = new StringBuilder();

        memberList.forEach(member -> {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append("('")
                    .append(member.getId())
                    .append("','")
                    .append(member.getGuildId())
                    .append("','")
                    .append(member.getNickName())
                    .append("','")
                    .append(member.getUserRank())
                    .append("','")
                    .append(member.getPosition())
                    .append("')");
        });

        if (builder.length() > 0) {
            guildMemberMapper.insertBatch(builder.toString());
        }
    }

    @Override
    @SneakyThrows
    public List<StringBuilder> getMemberInfoByQQGroupId(long groupId, long uniteAndFightId) {
        List<StringBuilder> builderList = new ArrayList<>();
        List<GbfGuildAndQqGroupBindInfo> guildList = guildAndQqGroupBindInfoMapper.selectList(
                Wrappers.<GbfGuildAndQqGroupBindInfo>lambdaQuery()
                        .eq(GbfGuildAndQqGroupBindInfo::getGroupId, groupId)
        );
        if (guildList.size() == 0) {
            builderList.add(new StringBuilder("该群没有登记骑空团！"));
            return builderList;
        }

        List<Long> guildIdList = guildList.stream()
                .map(GbfGuildAndQqGroupBindInfo::getGuildId)
                .distinct()
                .collect(Collectors.toList());
        String cookieStr = UniteAndFightUtils.getCookie();
        List<GbfGuildMember> memberList = guildMemberMapper.selectList(Wrappers.lambdaQuery());
        for (Long guildId : guildIdList) {
            if (StrUtil.isBlank(cookieStr)) {
                /* 尝试恢复cookie */
                log.warn("[查询团成员]: 尝试恢复cookie...");
                cookieStr = UniteAndFightUtils.getCookie();
            }
            GbfTeamRaidWebJson guildWebJson = UniteAndFightUtils.getGuildInfo(
                    cookieStr,
                    guildId,
                    uniteAndFightId
            );
            if (ObjectUtils.isEmpty(guildWebJson)) {
                builderList.add(new StringBuilder("检索骑空团[" + guildId + "], 连接超时！"));
                continue;
            }
            if (guildWebJson.getResult().size() == 0) {
                builderList.add(new StringBuilder("没有检索到该ID骑空团: " + guildId));
                continue;
            }
            GbfTeamRaidWebJson memberWebJson = UniteAndFightUtils.getGuildMemberInfo(
                    cookieStr,
                    guildId,
                    uniteAndFightId
            );
            if (ObjectUtils.isEmpty(memberWebJson)) {
                builderList.add(new StringBuilder("检索骑空团[" + guildId + "]成员列表, 连接超时！"));
                continue;
            }
            if (memberWebJson.getResult().size() == 0) {
                builderList.add(new StringBuilder("没有检索到该ID骑空团成员列表: " + guildId));
                continue;
            }
            GuildJson guildJson = guildWebJson.getResult().toList(GuildJson.class).get(0);
            List<GuildMemberJson> memberJsonList = memberWebJson.getResult().toList(GuildMemberJson.class);

            List<GbfGuildMember> newMemberList = new ArrayList<>();
            int totalRank = 0;
            int count = 0;
            StringBuilder builder = new StringBuilder(guildJson.getName() + " 的成员如下：\n");
            for (GuildMemberJson json : memberJsonList) {
                memberList.stream().filter(m -> Objects.equals(m.getId(), json.getUserid())).findFirst().ifPresentOrElse(member -> {
                    member.setGuildId(guildId);
                    member.setNickName(json.getName());
                    member.setUserRank(json.getLevel());
                    member.setPosition(json.getPosition());
                }, () -> {
                    GbfGuildMember member = new GbfGuildMember();
                    member.setNickName(json.getName());
                    member.setUserRank(json.getLevel());
                    member.setPosition(json.getPosition());
                    member.setId(json.getUserid());
                    member.setGuildId(guildId);
                    newMemberList.add(member);
                });

                builder.append("ID: ").append(json.getUserid())
                        .append(" 昵称: ").append(json.getName())
                        .append(" 等级: ").append(json.getLevel())
                        .append(" 职位: ").append(json.getPosition())
                        .append("\n");
                totalRank += json.getLevel();
                count++;
            }
            builder.append("骑空团平均等级: ").append(totalRank / count);
            builderList.add(builder);

            updateCase(memberList);
            insertBatch(newMemberList);
        }

        return builderList;
    }

    @Override
    @SneakyThrows
    public String addToWatchList(long groupId, Long userId, String userName) {
        GbfTeamRaidWebJson webJson = UniteAndFightUtils.getUserInfo(UniteAndFightUtils.getCookie(), userId);
        if (ObjectUtils.isEmpty(webJson)) {
            return "连接超时，请联系管理员";
        }
        if (webJson.getResult().size() == 0) {
            return "没有检索到该ID用户";
        }

        GbfGuildMember oldMember = guildMemberMapper.selectOne(
                Wrappers.<GbfGuildMember>lambdaQuery()
                        .eq(GbfGuildMember::getId, userId)
                        .last("limit 1")
        );
        if (ObjectUtils.isEmpty(oldMember)) {
            GbfGuildMember member = new GbfGuildMember();
            member.setId(userId);
            member.setNickName(userName);
            member.setUserRank(webJson.getResult().toList(MemberJson.class).get(0).getLevel());
            member.setIsHide("0");
            guildMemberMapper.insert(member);
        }
        else {
            if (Objects.equals(oldMember.getIsHide(), "0")) {
                return "加过啦，下次给你整个高亮行不";
            }
            oldMember.setIsHide("0");
            oldMember.setNickName(userName);
            guildMemberMapper.updateById(oldMember);
        }

        GbfMemberAndQqGroupBindInfo bindInfo = memberAndQqGroupBindInfoMapper.selectOne(
                Wrappers.<GbfMemberAndQqGroupBindInfo>lambdaQuery()
                        .eq(GbfMemberAndQqGroupBindInfo::getGroupId, groupId)
                        .eq(GbfMemberAndQqGroupBindInfo::getGbfId, userId)
        );
        if (ObjectUtils.isEmpty(bindInfo)) {
            GbfMemberAndQqGroupBindInfo bind = new GbfMemberAndQqGroupBindInfo();
            bind.setGbfId(userId);
            bind.setGroupId(groupId);
            memberAndQqGroupBindInfoMapper.insert(bind);
        }

        return "添加成功！正在监视你的贡献...";
    }

    private String buildRankChangingStr(GbfGuildMember member, int nowRank) {
        int differ = nowRank - member.getLastRank();
        if (differ <= 0) {
            differ = Math.abs(differ);
            return differ + " ↑";
        }
        else {
            return differ + " ↓";
        }
    }

    private List<GbfSoloPointDTO> getAllPointInner(UniteAndFightInfo info, boolean enableUpdate) {
        List<GbfSoloPointDTO> soloPointDAOList = new ArrayList<>();
        List<GbfGuildMember> memberList = new ArrayList<>();
        AtomicReference<String> cookieStr = new AtomicReference<>(UniteAndFightUtils.getCookie());
        guildMemberMapper.selectList(Wrappers.lambdaQuery()).forEach(member -> {
            log.info("[贡献监视]: 正在获取团成员[{}, {}]的信息...", member.getId(), member.getNickName());
            if (StrUtil.isBlank(cookieStr.get())) {
                /* 尝试恢复cookie */
                log.warn("[贡献监视]: 尝试恢复cookie...");
                cookieStr.set(UniteAndFightUtils.getCookie());
            }
            GbfTeamRaidWebJson webJson = UniteAndFightUtils.getSoloPointTotal(
                    cookieStr.get(),
                    member.getId(),
                    info.getId()
            );
            if (webJson == null) {
                GbfSoloPointDTO pointDAO = new GbfSoloPointDTO();
                pointDAO.setId(member.getId());
                pointDAO.setPointResult(member.getNickName() + " 的贡献信息获取超时...\n");
                soloPointDAOList.add(pointDAO);
                return;
            }
            if (webJson.getResult().size() == 0) {
                return;
            }
            List<MemberPointJson> pointList = webJson.getResult().get(0, JSONObject.class)
                    .getJSONArray("全日期").toList(MemberPointJson.class);
            if (CollectionUtils.isEmpty(pointList)) {
                return;
            }
            MemberPointJson pointJson = pointList.get(pointList.size() - 1);
            long pointDiff = pointJson.getPoint() - member.getLastPoint();
            pointDiff = pointDiff < 0 ? 0 : pointDiff;

            String resultStr = member.getNickName() +
                    " 时速: " + pointDiff +
                    " 排名: " + pointJson.getRank() +
                    " " + buildRankChangingStr(member, pointJson.getRank()) +
                    "\n";

            if (memberList.stream().noneMatch(m -> Objects.equals(m.getId(), member.getId()))
                    && (!Objects.equals(member.getLastPoint(), pointJson.getPoint())
                    || member.getLastRank() != pointJson.getRank()
                    || member.getUpdateTime().getTime() != pointJson.getUpdatetime())) {
                member.setLastPoint(pointJson.getPoint());
                member.setLastRank(pointJson.getRank());
                member.setUpdateTime(new Date(pointJson.getUpdatetime() * 1000));
                memberList.add(member);
            }

            if (Objects.equals(member.getIsHide(), "0")) {
                GbfSoloPointDTO pointDAO = new GbfSoloPointDTO();
                pointDAO.setId(member.getId());
                pointDAO.setPointResult(resultStr);
                soloPointDAOList.add(pointDAO);
            }
            else {
                log.info("[贡献监视]: **忽略团成员[{}, {}]的贡献文本**", member.getId(), member.getNickName());
            }
        });

        if (enableUpdate) {
            updateCase(memberList);
        }
        return soloPointDAOList;
    }

    @Override
    @SneakyThrows
    public List<GbfSoloPointDTO> getAllPoint(List<Long> groupIdList, UniteAndFightInfo info, boolean enableUpdate) {
        List<GbfMemberAndQqGroupBindInfo> groupList = memberAndQqGroupBindInfoMapper.selectList(
                Wrappers.<GbfMemberAndQqGroupBindInfo>lambdaQuery()
                        .in(GbfMemberAndQqGroupBindInfo::getGroupId, groupIdList)
        );
        List<GbfSoloPointDTO> pointGroupList = new ArrayList<>();
        if (CollectionUtils.isEmpty(groupList)) {
            log.warn("[贡献监视]: 找不到绑定关系! 群ID: " + groupIdList);
            return pointGroupList;
        }
        groupList = groupList.stream().distinct().collect(Collectors.toList());

        List<GbfSoloPointDTO> pointAllList = getAllPointInner(info, enableUpdate);
        if (CollectionUtils.isEmpty(pointAllList)) {
            return pointGroupList;
        }

        Map<Long, List<GbfMemberAndQqGroupBindInfo>> orderedGroupList = groupList.stream()
                .collect(Collectors.groupingBy(GbfMemberAndQqGroupBindInfo::getGroupId));
        for (Long groupId : orderedGroupList.keySet()) {
            StringBuilder builder = new StringBuilder("时速汇报(" + new SimpleDateFormat().format(new Date()) + ")：\n");
            orderedGroupList.get(groupId).forEach(bindInfo -> pointAllList.stream()
                    .filter(p -> Objects.equals(p.getId(), bindInfo.getGbfId()))
                    .findFirst().ifPresent(p -> builder.append(p.getPointResult()))
            );
            builder.append("发送命令【" + CommandConstant.COMMAND_PREFIX + "监视我的贡献】加入监视列表");

            GbfSoloPointDTO pointDAO = new GbfSoloPointDTO();
            pointDAO.setId(groupId);
            pointDAO.setPointResult(builder.toString());
            pointGroupList.add(pointDAO);
        }

        return pointGroupList;
    }

    @Override
    public List<GbfSoloPointDTO> getRankTotalPoint(List<Long> joinedGroupIdList, UniteAndFightInfo info) {
        List<Long> groupIdList = new ArrayList<>();
        List<GbfGuildAndQqGroupBindInfo> guildList = guildAndQqGroupBindInfoMapper.selectList(Wrappers.emptyWrapper());
        if (!CollectionUtils.isEmpty(guildList)) {
            groupIdList = guildList.stream()
                    .map(GbfGuildAndQqGroupBindInfo::getGroupId)
                    .distinct()
                    .collect(Collectors.toList());
        }
        List<GbfSoloPointDTO> pointGroupList = new ArrayList<>();
        String cookie = UniteAndFightUtils.getCookie();
        String[] rankKeyList = {"2000", "80000", "140000", "180000"};
        StringBuilder builder = new StringBuilder();
        for (String key : rankKeyList) {
            if (StrUtil.isBlank(cookie)) {
                /* 尝试恢复cookie */
                log.warn("[查询个排档位]: 尝试恢复cookie...");
                cookie = UniteAndFightUtils.getCookie();
            }
            GbfTeamRaidWebJson webJson = UniteAndFightUtils.getSoloAllRank(cookie, null, info.getId(), key);
            if (ObjectUtils.isEmpty(webJson)) {
                log.warn("检索个排[" + key + "], 连接超时！");
                continue;
            }
            if (webJson.getResult().size() == 0) {
                continue;
            }
            List<MemberPointJson> pointList = webJson.getResult().get(0, JSONObject.class)
                    .getJSONArray("全日期").toList(MemberPointJson.class);
            if (CollectionUtils.isEmpty(pointList)) {
                continue;
            }
            MemberPointJson pointJson = pointList.get(pointList.size() - 1);
            String rankTip;
            switch (key) {
                case "2000":
                    rankTip = "英雄";
                    break;
                case "80000":
                    rankTip = "一档";
                    break;
                case "140000":
                    rankTip = "二档";
                    break;
                case "180000":
                    rankTip = "三档";
                    break;
                default:
                    rankTip = "未知档位";
                    break;
            }
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(rankTip)
                    .append("：")
                    .append(pointJson.getPoint())
                    .append(" 更新时间：")
                    .append(DateUtil.format(new Date(pointJson.getUpdatetime() * 1000), DatePattern.NORM_DATETIME_FORMAT));
        }
        String realMessage = "";
        if (builder.length() > 0) {
            realMessage = "个排档位：\n" + builder;
        }
        if (!CollectionUtils.isEmpty(groupIdList)) {
            for (Long id : groupIdList) {
                if (joinedGroupIdList.stream().anyMatch(temp -> Objects.equals(temp, id))) {
                    GbfSoloPointDTO dto = new GbfSoloPointDTO();
                    dto.setId(id);
                    dto.setPointResult(realMessage);
                    pointGroupList.add(dto);
                }
            }
        }
        if (CollectionUtils.isEmpty(pointGroupList)) {
            /* 说明没有绑定QQ群, 默认传一个ID为0的QQ群 */
            GbfSoloPointDTO dto = new GbfSoloPointDTO();
            dto.setId(0L);
            dto.setPointResult(realMessage);
            pointGroupList.add(dto);
        }
        return pointGroupList;
    }
}
