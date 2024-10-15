package com.gbf.kukuru.plugin;

import org.springframework.stereotype.Component;

import com.mikuac.shiro.core.BotPlugin;

@Component
public class ChatPlugin extends BotPlugin {

//    @Resource
//    private RobotService robotService;
//    @Resource
//    private UserService userService;
//
//    @Override
//    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
//        // 这里展示了event消息链的用法. List里面可能是 at -> text -> image -> face -> text 形式, 根据元素类型组成 List。
//        // List 每一个元素 有type(String)和data(Map<String, String>)，type 表示元素是什么, data 表示元素的具体数据，如at qq，image url，face id
//        List<OnebotBase.Message> messageChain = event.getMessageList();
//        if (messageChain.size() > 0) {
//            OnebotBase.Message message = messageChain.get(0);
//            if (message.getType().equals("text")) {
//                String text = message.getDataMap().get("text");
//                if ("hello".equals(text)) {
//                    bot.sendPrivateMsg(event.getUserId(), "hi", false);
//                }
//            }
//        }
//        return MESSAGE_IGNORE;
//    }
//
//    @Override
//    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
//        long groupId = event.getGroupId();
//        String text = event.getRawMessage();
//        if (text.contains(String.valueOf(bot.getSelfId()))) {
//            text = text.replace("<at qq=\"" + bot.getSelfId() + "\"/>", "");
//            String finalResult = robotService.answer(text).getContent()
//                    .replace("菲菲", "ククル")
//                    .replace("{br}", "\n");
//            bot.sendGroupMsg(groupId, finalResult, false);
//            return MESSAGE_BLOCK;
//        } else if (text.equals("?")) {
//            Random random = new Random();
//            if (random.nextInt(3) == 0) {
//                bot.sendGroupMsg(groupId, "¿", false);
//            }
//            return MESSAGE_BLOCK;
//        }
//
//        return MESSAGE_IGNORE;
//    }
//
//    /**
//     * 群成员减少时调用此方法(有人退群/被踢，事件已发生)
//     *
//     * @param bot   bot对象
//     * @param event 时间内容
//     * @return 结束
//     */
//    @Override
//    public int onGroupDecreaseNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupDecreaseNoticeEvent event) {
//        long groupId = event.getGroupId();
//        long userId = event.getUserId();
//        if (userId == bot.getSelfId()) {
//            /* 不处理机器人自身的退群消息 */
//            return MESSAGE_BLOCK;
//        }
//        long time = event.getTime();
//        String nickName = userService.checkhasNickName(userId);
//        if (!StringUtils.isEmpty(nickName)) {
//            String msg = "握草 " + nickName + "退群了：" + groupId;
//            bot.sendGroupMsg(groupId, msg, false);
//            return MESSAGE_BLOCK;
//        }
//        String msg = "时间：" + time + "QQ：" + userId + "退出了群组：" + groupId;
//        bot.sendGroupMsg(groupId, msg, false);
//        return MESSAGE_BLOCK;
//    }
//
//    /**
//     * 群成员增加时调用此方法(有人进群，事件已发生)
//     *
//     * @param bot   bot对象
//     * @param event 时间内容
//     * @return 结束
//     */
//    @Override
//    public int onGroupIncreaseNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupIncreaseNoticeEvent event) {
//        long groupId = event.getGroupId();
//        long userId = event.getUserId();
//        if (userId == bot.getSelfId()) {
//            /* 不处理机器人自身的入群消息 */
//            return MESSAGE_BLOCK;
//        }
//        String msg = "QQ：" + userId + "加入了群组：" + groupId;
//        bot.sendGroupMsg(groupId, msg, false);
//        return MESSAGE_BLOCK;
//    }
//
//    /**
//     * 成员被禁言事件
//     *
//     * @param bot   bot对象
//     * @param event 时间内容
//     * @return 结束
//     */
//    @Override
//    public int onGroupBanNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupBanNoticeEvent event) {
//        long groupId = event.getGroupId();
//        long userId = event.getUserId();
//        if (userId == bot.getSelfId()) {
//            /* 不处理机器人自身的禁言消息 */
//            return MESSAGE_BLOCK;
//        }
//        String nickName = userService.checkhasNickName(userId);
//        if (StrUtil.isNotBlank(nickName)) {
//            String msg = nickName + "被禁言了    笑死";
//            bot.sendGroupMsg(groupId, msg, false);
//            return MESSAGE_BLOCK;
//        }
//        String msg = "用户" + userId + "被禁言";
//        bot.sendGroupMsg(groupId, msg, false);
//        return MESSAGE_BLOCK;
//    }
}
