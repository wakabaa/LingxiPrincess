package com.gbf.kukuru.util;

import cn.hutool.core.util.StrUtil;
import com.gbf.kukuru.entity.UniteAndFightInfo;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * GBF 工具类
 *
 * @author ginoko
 * @since 2022-08-06
 */
public class GbfUtils {

    /**
     * 检查该古战场是否正在进行
     *
     * @param info 古战场实体
     * @return true 正在进行; false 没在进行
     */
    public static boolean checkTodayIsUniteAndFight(UniteAndFightInfo info) {
        if (ObjectUtils.isEmpty(info)) {
            return false;
        }
        long currentTimestamp = new Date().getTime();
        return info.getBeginTime().getTime() <= currentTimestamp
                && currentTimestamp <= info.getEndTime().getTime();
    }

    /**
     * 检查该古战场特殊战是否正在进行
     *
     * @param info 古战场实体
     * @return true 正在进行; false 没在进行
     */
    public static boolean checkTodayIsSpecialFight(UniteAndFightInfo info) {
        if (ObjectUtils.isEmpty(info)) {
            return false;
        }
        long currentTimestamp = new Date().getTime();
        return info.getSpecialFightBeginTime().getTime() <= currentTimestamp
                && currentTimestamp <= info.getSpecialFightEndTime().getTime();
    }

    /**
     * 获取属性类型的文字描述
     *
     * @param type 属性类型
     * @return 属性类型的文字描述
     */
    public static String transferElementType(String type) {
        if (StrUtil.isBlank(type)) {
            return "未知属性";
        }
        switch (type) {
            case "0":
                return "风";
            case "1":
                return "土";
            case "2":
                return "水";
            case "3":
                return "火";
            case "4":
                return "暗";
            case "5":
                return "光";
            default:
                return "未知属性";
        }
    }
}
