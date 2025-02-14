package com.gbf.kukuru.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotContainer;
import com.mikuac.shiro.dto.action.response.GroupInfoResp;

import lombok.extern.slf4j.Slf4j;

/**
 * QQ主动消息推送工具类
 *
 * @author Zero
 * @date 2020/11/2 19:42
 */
@Slf4j
@Component
public class SendMsgUtils {

	// todo: 多账户的时候这里要修改
	long bot_selfId = 2670857929l;

	@Autowired
	private BotContainer botContainer;

	public void sendPrivateMsgForMsg(long userId, String msg) throws InterruptedException {
		Bot bot = botContainer.robots.get(bot_selfId);
		Thread.sleep(1000);
		bot.sendPrivateMsg(userId, msg, false);
	}

	public void sendGroupMsgForMsg(long groupId, String msg) throws InterruptedException {
		Bot bot = botContainer.robots.get(bot_selfId);
		Thread.sleep(1000);
		bot.sendGroupMsg(groupId, msg, false);
	}

	public void sendPrivateMsgForText(long userId, String msg) throws InterruptedException {
		Bot bot = botContainer.robots.get(bot_selfId);
		Thread.sleep(1000);
		bot.sendPrivateMsg(userId, msg, false);
	}

	public void sendGroupMsgForText(long groupId, String msg) throws InterruptedException {
		Bot bot = botContainer.robots.get(bot_selfId);
		// 限制发送速度
		Thread.sleep(1000);
		bot.sendGroupMsg(groupId, msg, false);
	}

	public List<Long> getGroupList() throws InterruptedException {
		int retryCount = 6;
		int retryDelay = 10000;

		List<Long> groupIdList = new ArrayList<>();

		// 获取Bot对象
		Bot bot = botContainer.robots.get(bot_selfId);
		if (bot == null) {
			for (int i = 1; i < retryCount; i++) {
				log.info("Bot对象获取失败，当前失败[{}]次，剩余重试次数[{}]，将在" + (retryDelay / 1000) + "秒后重试~", i, retryCount - i - 1);
				Thread.sleep(retryDelay);
				bot = botContainer.robots.get(bot_selfId);
				if (bot != null) {
					log.info("Bot对象获取成功[{}]", bot);
					break;
				}
				if (i == 5) {
					log.error("Bot对象获取失败5次，将中止此函数");
					return groupIdList;
				}
			}
		} else {
			log.info("Bot对象获取成功[{}]", bot);
		}

		// 获取群号列表
		for (int i = 1; i < retryCount; i++) {
			try {
				int groupCount = 0;
				if (bot != null) {
					groupCount = Objects.requireNonNull(bot.getGroupList().getData().size());
				}
				if (groupCount > 0) {
					log.info("群组计数获取成功，当前群组数量[{}]", groupCount);
					// 遍历群号
					List<GroupInfoResp> data = bot.getGroupList().getData();
					for (int j = 0; j < data.size(); j++) {
						groupIdList.add(data.get(j).getGroupId());
					}
					break;
				} else {
					log.error("群组计数获取失败，且未发生异常，将中止此函数，当前群组计数[{}]", groupCount);
					return groupIdList;
				}
			} catch (Exception e) {
				log.error("群组计数获取失败，当前失败[{}]次，剩余重试次数[{}]，将在" + (retryDelay / 1000) + "秒后重试~", i, retryCount - i - 1);
				if (i == 5) {
					log.error("群组计数获取失败5次，将中止此函数");
					return groupIdList;
				}
				Thread.sleep(retryDelay);
			}
		}

		return groupIdList;

	}

}