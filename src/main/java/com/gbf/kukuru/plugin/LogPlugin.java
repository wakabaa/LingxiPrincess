package com.gbf.kukuru.plugin;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.PrivateMessageHandler;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Shiro
@Component
public class LogPlugin{
	
	@PrivateMessageHandler
    public void onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        log.info("收到私聊消息 QQ：{} 内容：{}", event.getUserId(), event.getRawMessage());
    }

	@GroupMessageHandler
    public void onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        log.info("收到群消息 群号：{} QQ：{} 内容：{}", event.getGroupId(), event.getUserId(), event.getRawMessage());
    }
}
