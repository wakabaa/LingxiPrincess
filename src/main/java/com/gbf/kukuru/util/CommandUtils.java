package com.gbf.kukuru.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.gbf.kukuru.constant.CommandConstant;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 指令工具类
 *
 * @author ginoko
 * @since 2022-08-06
 */
public class CommandUtils {

    /**
     * 指令表
     * <pre>
     * key: 指令要匹配的字符串
     * value: 指令注释
     */
    public static Map<String, String> commandMap = new LinkedHashMap<>() {{
        put(CommandConstant.COMMAND_PCR_DIVINATION, "梦幻精灵");
//        put(CommandConstant.COMMAND_ARCANA_DIVINATION, "塔罗牌占卜");
//        put(CommandConstant.COMMAND_PHOENIX_WRIGHT, "逆转裁判图片生成(参数1 上半句; 参数2 下半句)");
//        put(CommandConstant.COMMAND_GBF_COUNT_DOWN_UNITE_AND_FIGHT, "古战场倒计时");
//        put(CommandConstant.COMMAND_GBF_SEARCH_MEMBER_LIST, "查询成员列表");
//        put(CommandConstant.COMMAND_GBF_SEARCH_MEMBER_CONTRIBUTION, "查询成员贡献");
//        put(CommandConstant.COMMAND_GBF_SEARCH_RANK_CONTRIBUTION, "查询个排档位");
//        put(CommandConstant.COMMAND_GBF_WATCH_CONTRIBUTION, "监视我的贡献");
//        put(CommandConstant.COMMAND_BILIBILI_ADD_LIVE_LISTENING, "监听直播");
//        put(CommandConstant.COMMAND_BILIBILI_CANCEL_LIVE_LISTENING, "取消监听直播");
//        put(CommandConstant.COMMAND_KAMIYU_IMAGE, "随机发送kamiyu语录");
//        put(CommandConstant.COMMAND_WAKABA_IMAGE, "随机发送若叶语录");
//        put(CommandConstant.COMMAND_CAT_IMAGE, "随机发送猫猫表情包");
//        put(CommandConstant.COMMAND_YANGKUKU_IMAGE, "随机发送羊库库语录");
//        put(CommandConstant.COMMAND_HELP, "显示当前指令列表");
    }};

    /**
     * 获取无前缀的指令
     *
     * @param command 指令字符串
     * @return 无前缀的指令
     */
    public static String getNoPrefixCommand(@NonNull String command) {
        if (StrUtil.isNotBlank(command) && command.charAt(0) == CommandConstant.COMMAND_PREFIX.charAt(0)) {
            return command.substring(1).trim();
        }
        return command;
    }

    /**
     * 判断指令是否匹配
     *
     * @param command         指令字符串
     * @param commandConstant 指令常量
     * @return true 匹配; false 不匹配
     */
    public static boolean isCommand(@NonNull String command, @Nullable String commandConstant) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (StrUtil.isNotBlank(command)
                && command.charAt(0) == CommandConstant.COMMAND_PREFIX.charAt(0)
                && !command.trim().equals(CommandConstant.COMMAND_PREFIX)) {
            Arrays.stream(
                            command.substring(CommandConstant.COMMAND_PREFIX.length())
                                    .trim()
                                    .split(" ")
                    ).filter(str -> !Objects.equals(str, ""))
                    .findFirst()
                    .ifPresent(str -> {
                        String realCommand = str.toLowerCase();
                        result.set(
                                commandMap.containsKey(realCommand)
                                        && StrUtil.isNotBlank(commandConstant)
                                        && realCommand.equals(commandConstant)
                        );
                    });
        }
        return result.get();
    }

    /**
     * 判断字符串是否看起来像指令
     *
     * @param command 指令字符串
     * @return true 看起来像; false 看起来不像
     */
    public static boolean isSeemsLikeCommand(String command) {
        return !isCommand(command, null)
                && StrUtil.isNotBlank(command)
                && command.charAt(0) == CommandConstant.COMMAND_PREFIX.charAt(0)
                && !command.trim().equals(CommandConstant.COMMAND_PREFIX)
                && !NumberUtil.isNumber(
                String.valueOf(command.substring(CommandConstant.COMMAND_PREFIX.length()).trim().charAt(0)));
    }

    /**
     * 获取指令表的字符串形式
     *
     * @return 字符串
     */
    public static String getCommandMapString() {
        StringBuilder builder = new StringBuilder();
        commandMap.forEach((key, value) -> {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(CommandConstant.COMMAND_PREFIX).append(key)
                    .append(" [").append(value).append("]");
        });
        return builder.toString();
    }
}
