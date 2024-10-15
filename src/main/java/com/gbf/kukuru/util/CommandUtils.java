package com.gbf.kukuru.util;

/**
 * 指令工具类
 *
 * @author ginoko
 * @since 2022-08-06
 */
public class CommandUtils {

//    /**
//     * 指令表
//     * <pre>
//     * key: 指令要匹配的字符串
//     * value: 指令注释
//     */
////    public static Map<String, String> commandMap = new LinkedHashMap<>() {{
////        put(CommandConstant.COMMAND_DREAM_JOURNEY_TO_THE_WEST, "梦幻精灵");
////        put(CommandConstant.COMMAND_GET_JEWELRY_PROCESSING, "宝石合成");
////        put(CommandConstant.COMMAND_GET_STAR_JEWELRY_PROCESSING, "星辉石合成");
////    }};
//
//    /**
//     * 获取无前缀的指令
//     *
//     * @param command 指令字符串
//     * @return 无前缀的指令
//     */
//    public static String getNoPrefixCommand(@NonNull String command) {
////        if (StrUtil.isNotBlank(command) && command.charAt(0) == CommandConstant.COMMAND_PREFIX.charAt(0)) {
////            return command.substring(1).trim();
////        }
//        return command;
//    }
//
//    /**
//     * 判断指令是否匹配
//     *
//     * @param command         指令字符串
//     * @param commandConstant 指令常量
//     * @return true 匹配; false 不匹配
//     */
//    public static boolean isCommand(@NonNull String command, @Nullable String commandConstant) {
//        AtomicBoolean result = new AtomicBoolean(false);
//        if (StrUtil.isNotBlank(command)
//                && command.charAt(0) == CommandConstant.COMMAND_PREFIX.charAt(0)
//                && !command.trim().equals(CommandConstant.COMMAND_PREFIX)) {
//            Arrays.stream(
//                            command.substring(CommandConstant.COMMAND_PREFIX.length())
//                                    .trim()
//                                    .split(" ")
//                    ).filter(str -> !Objects.equals(str, ""))
//                    .findFirst()
//                    .ifPresent(str -> {
//                        String realCommand = str.toLowerCase();
//                        result.set(
//                                commandMap.containsKey(realCommand)
//                                        && StrUtil.isNotBlank(commandConstant)
//                                        && realCommand.equals(commandConstant)
//                        );
//                    });
//        }
//        return result.get();
//    }
//
//    /**
//     * 判断字符串是否看起来像指令
//     *
//     * @param command 指令字符串
//     * @return true 看起来像; false 看起来不像
//     */
//    public static boolean isSeemsLikeCommand(String command) {
//        return !isCommand(command, null)
//                && StrUtil.isNotBlank(command)
//                && command.charAt(0) == CommandConstant.COMMAND_PREFIX.charAt(0)
//                && !command.trim().equals(CommandConstant.COMMAND_PREFIX)
//                && !NumberUtil.isNumber(
//                String.valueOf(command.substring(CommandConstant.COMMAND_PREFIX.length()).trim().charAt(0)));
//    }
//
//    /**
//     * 获取指令表的字符串形式
//     *
//     * @return 字符串
//     */
//    public static String getCommandMapString() {
//        StringBuilder builder = new StringBuilder();
//        commandMap.forEach((key, value) -> {
//            if (builder.length() > 0) {
//                builder.append("\n");
//            }
//            builder.append(CommandConstant.COMMAND_PREFIX).append(key)
//                    .append(" [").append(value).append("]");
//        });
//        return builder.toString();
//    }
}
