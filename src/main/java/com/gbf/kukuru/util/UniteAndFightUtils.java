package com.gbf.kukuru.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.gbf.kukuru.entity.webJson.GbfTeamRaidWebJson;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 古战场工具类
 */
@Component
public class UniteAndFightUtils {
    public static final String COOKIE_REDIS_KEY = "GBF_TEAM_RAID_WEB_COOKIE";
    public static final String URL_LOGIN = "https://info.gbfteamraid.fun/login";
    public static final String URL_USER_RANK = "https://info.gbfteamraid.fun/web/userrank";
    public static final String URL_GUILD_RANK = "https://info.gbfteamraid.fun/web/guildrank";

    public static final String METHOD_SOLO_DAY_POINT = "getUserDayPoint";
    public static final String METHOD_SOLO_TOTAL_POINT = "getUserrankChartById";
    public static final String METHOD_SOLO_ALL_RANK = "getUserrankChartByRank";
    public static final String METHOD_USER_INFO = "getUserrank";
    public static final String METHOD_GUILD_INFO = "getGuildrank";
    public static final String METHOD_GUILD_MEMBER_INFO = "getGuilduser";

    /**
     * 获取咕战场的网站cookie
     *
     * @return cookie字符串
     */
    public static String getCookie() {
        String cookie = RedisUtils.get(COOKIE_REDIS_KEY);
        if (StrUtil.isBlank(cookie)) {
            try (HttpResponse request = HttpRequest.post(URL_LOGIN)
                    .setConnectionTimeout(3000)
                    .setReadTimeout(7000)
                    .execute()) {
                cookie = request.getCookieStr().split(";")[0];
            } catch (Exception e) {
                e.printStackTrace();
                cookie = null;
            }
            if (StrUtil.isNotBlank(cookie)) {
                /* cookie保存4分钟 */
                RedisUtils.setEx(COOKIE_REDIS_KEY, cookie, 240000);
            }
        }
        return cookie;
    }

    /**
     * 构造表单参数
     *
     * @param method          使用的方法
     * @param id              gbf账号ID或团ID
     * @param name            gbf用户昵称或团名称
     * @param uniteAndFightId 古战场ID
     * @return 表单参数
     */
    public static Map<String, Object> getFormParams(String method,
                                                    @Nullable Long id,
                                                    @Nullable String name,
                                                    @Nullable Long uniteAndFightId) {
        if (ObjectUtils.isEmpty(uniteAndFightId)) {
            uniteAndFightId = 0L;
        }
        uniteAndFightId = Math.abs(uniteAndFightId);
        String uniteAndFightIdStr = String.valueOf(uniteAndFightId);
        if (uniteAndFightId < 10) {
            uniteAndFightIdStr = "00" + uniteAndFightId;
        }
        else if (uniteAndFightId < 100) {
            uniteAndFightIdStr = "0" + uniteAndFightId;
        }
        Map<String, Object> formParams = new HashMap<>();
        formParams.put("method", method);
        switch (method) {
            case METHOD_USER_INFO:
                formParams.put("params",
                        "{\"userid\":\"" + id + "\",\"username\":\"\"}"
                );
                break;
            case METHOD_SOLO_ALL_RANK:
                formParams.put("params",
                        "{\"teamraidid\":\"teamraid" + uniteAndFightIdStr + "\",\"rank\":\"" + name + "\"}");
                break;
            case METHOD_GUILD_MEMBER_INFO:
                formParams.put("params",
                        "{\"teamraidid\":\"teamraid" + uniteAndFightIdStr + "\",\"guildid\":\"" + id + "\"}"
                );
                break;
            case METHOD_GUILD_INFO:
                if (name == null) {
                    name = "";
                }
                formParams.put("params",
                        "{\"teamraidid\":\"teamraid" + uniteAndFightIdStr
                                + "\",\"guildid\":\"" + id
                                + "\",\"guildname\":\"" + name + "\"}"
                );
                break;
            default:
                formParams.put("params",
                        "{\"teamraidid\":\"teamraid" + uniteAndFightIdStr + "\",\"userid\":\"" + id + "\"}"
                );
                break;
        }
        return formParams;
    }

    public static GbfTeamRaidWebJson requestRaid(String cookieStr,
                                                 @Nullable Long userId, String url, String method,
                                                 @Nullable String name,
                                                 @Nullable Long uniteAndFightId) {
        try (HttpResponse response = HttpRequest.post(url)
                .setConnectionTimeout(3000)
                .setReadTimeout(7000)
                .form(getFormParams(method, userId, name, uniteAndFightId))
                .header(Header.COOKIE, cookieStr)
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,ja;q=0.8")
                .header(Header.ACCEPT, "*/*")
                .execute()) {
            return JSONUtil.toBean(response.body(), GbfTeamRaidWebJson.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取咕战场的团数据
     *
     * @param cookieStr       cookie字符串
     * @param guildId         团ID
     * @param uniteAndFightId 古战场ID
     * @return 团数据
     */
    public static GbfTeamRaidWebJson getGuildInfo(String cookieStr, Long guildId, long uniteAndFightId) {
        if (StrUtil.isBlank(cookieStr)) {
            return null;
        }
        return requestRaid(cookieStr, guildId, URL_GUILD_RANK, METHOD_GUILD_INFO, null, uniteAndFightId);
    }

    /**
     * 获取咕战场的团成员数据
     *
     * @param cookieStr       cookie字符串
     * @param guildId         团ID
     * @param uniteAndFightId 古战场ID
     * @return 团成员数据
     */
    public static GbfTeamRaidWebJson getGuildMemberInfo(String cookieStr, Long guildId, long uniteAndFightId) {
        if (StrUtil.isBlank(cookieStr)) {
            return null;
        }
        return requestRaid(cookieStr, guildId, URL_GUILD_RANK, METHOD_GUILD_MEMBER_INFO, null, uniteAndFightId);
    }

    /**
     * 获取咕战场的用户数据
     *
     * @param cookieStr cookie字符串
     * @param userId    用户ID
     * @return 用户数据
     */
    public static GbfTeamRaidWebJson getUserInfo(String cookieStr, Long userId) {
        if (StrUtil.isBlank(cookieStr)) {
            return null;
        }
        return requestRaid(cookieStr, userId, URL_USER_RANK, METHOD_USER_INFO, null, null);
    }

    /**
     * 获取咕战场的个排数据
     *
     * @param cookieStr       cookie字符串
     * @param userId          用户ID
     * @param uniteAndFightId 古战场ID
     * @return 个排数据
     */
    public static GbfTeamRaidWebJson getSoloPointTotal(String cookieStr, Long userId, long uniteAndFightId) {
        if (StrUtil.isBlank(cookieStr)) {
            return null;
        }
        return requestRaid(cookieStr, userId, URL_USER_RANK, METHOD_SOLO_TOTAL_POINT, null, uniteAndFightId);
    }

    /**
     * 获取咕战场的个排排名数据
     *
     * @param cookieStr       cookie字符串
     * @param userId          用户ID
     * @param uniteAndFightId 古战场ID
     * @return 个排排名数据
     */
    public static GbfTeamRaidWebJson getSoloAllRank(String cookieStr, Long userId, long uniteAndFightId, String rankName) {
        if (StrUtil.isBlank(cookieStr)) {
            return null;
        }
        return requestRaid(cookieStr, userId, URL_USER_RANK, METHOD_SOLO_ALL_RANK, rankName, uniteAndFightId);
    }
}
