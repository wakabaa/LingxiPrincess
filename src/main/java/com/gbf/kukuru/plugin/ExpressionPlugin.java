package com.gbf.kukuru.plugin;

import java.lang.management.ManagementFactory;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import com.gbf.kukuru.util.PathUtils;

import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;

@Component
public class ExpressionPlugin extends BotPlugin {

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        long upTime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        String msg = event.getRawMessage();
        long userId = event.getUserId();
        long groupId = event.getGroupId();
        if (msg.equals("延迟") || msg.equals("掉线")) {
            bot.sendGroupMsg(
                    groupId,
                    Msg.builder()
                            .at(userId)
                            .text("UpTime: " + upTime + "s\n")
                            .image(PathUtils.getRealPathFromResource("/static/img/ping.jpg"))
                            .build(),
                    false
            );
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }
}
