package com.gbf.kukuru.plugin;

import org.springframework.stereotype.Component;

import com.mikuac.shiro.core.BotPlugin;

/**
 * 指令提示插件
 *
 * @author ginoko
 * @since 2022-08-09
 */
@Component
public class CommandHintPlugin extends BotPlugin {

//    @Override
//    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
//        String msg = event.getRawMessage();
//        long groupId = event.getGroupId();
//        if (CommandUtils.isCommand(msg, CommandConstant.COMMAND_HELP)) {
//            Msg sendMsg = Msg.builder().text(CommandUtils.getCommandMapString());
//            bot.sendGroupMsg(groupId, sendMsg.build(), false);
//            return MESSAGE_BLOCK;
//        } else if (CommandUtils.isSeemsLikeCommand(msg)) {
//            Msg sendMsg = Msg.builder().text("？输入\"" + CommandConstant.COMMAND_PREFIX + "帮助\"显示所有指令");
//            bot.sendGroupMsg(groupId, sendMsg.build(), false);
//            return MESSAGE_BLOCK;
//        }
//        return MESSAGE_IGNORE;
//    }
//
//    @Override
//    public int onPrivateMessage(@NotNull Bot bot, OnebotEvent.PrivateMessageEvent event) {
//        String msg = event.getRawMessage();
//        long userId = event.getUserId();
//        if (CommandUtils.isCommand(msg, CommandConstant.COMMAND_HELP)) {
//            Msg sendMsg = Msg.builder().text(CommandUtils.getCommandMapString());
//            bot.sendPrivateMsg(userId, sendMsg.build(), false);
//            return MESSAGE_BLOCK;
//        } else if (CommandUtils.isSeemsLikeCommand(msg)) {
//            Msg sendMsg = Msg.builder().text("？输入\"" + CommandConstant.COMMAND_PREFIX + "帮助\"显示所有指令");
//            bot.sendPrivateMsg(userId, sendMsg.build(), false);
//            return MESSAGE_BLOCK;
//        }
//        return MESSAGE_IGNORE;
//    }
}
