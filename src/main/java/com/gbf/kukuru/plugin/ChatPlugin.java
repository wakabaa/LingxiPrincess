package com.gbf.kukuru.plugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.MessageHandlerFilter;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.action.common.ActionList;
import com.mikuac.shiro.dto.action.response.GroupMemberInfoResp;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;

@Shiro
@Component
public class ChatPlugin extends BotPlugin {

	String avatarPath = "http://q.qlogo.cn/headimg_dl?dst_uin=userId&spec=200&img_type=jpg";
	String comPath = this.getClass().getResource("/").getPath() + "/static/img/todaywaifu/output.png";
	
	@GroupMessageHandler
	@MessageHandlerFilter(cmd = "今日老婆")
	public int onGroupMessage(@NotNull Bot bot, GroupMessageEvent event) {
//		// 获取当前用户QQ号
//		Long userId = event.getUserId();
//		Long groupId = event.getGroupId();
//		// 获取当前群QQ号
//		ActionList<GroupMemberInfoResp> groupMemberList = bot.getGroupMemberList(groupId, true);
//
//		// 随机抽选一名幸运群友
//		Random random = new Random();
//		List<GroupMemberInfoResp> data = groupMemberList.getData();
//		GroupMemberInfoResp groupMemberInfoResp = data.get(random.nextInt(data.size()));
//		Long userId2 = groupMemberInfoResp.getUserId();
//
//		// 获取当前用户QQ头像
//		BufferedImage avatar1 = downloadImage(avatarPath.replace("userId", String.valueOf(userId)));
//
//		// 获取幸运群友QQ头像
//		BufferedImage avatar2 = downloadImage(avatarPath.replace("userId", String.valueOf(userId2)));
//
//		BufferedImage canvas = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2d = canvas.createGraphics();
//
//		// 绘制背景
//		g2d.setColor(Color.PINK);
//		g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//		// 绘制头像
//		g2d.drawImage(avatar1, 0, 0, 120, 120, null);
//		g2d.drawImage(avatar2, 80, 80, 120, 120, null);
//
//		// 保存结果图像
//		g2d.dispose();
//		try {
//			ImageIO.write(canvas, "png", new File(comPath));
//			System.out.println("图像已保存为 output.png");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		// 构建消息
//		String sendMsg = MsgUtils.builder().img(comPath).at(userId).build();
//		// 发送私聊消息
//		bot.sendGroupMsg(groupId, sendMsg, false);
		return MESSAGE_BLOCK;
	}

	private static BufferedImage downloadImage(String url) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(url);
			try (CloseableHttpResponse response = httpClient.execute(request)) {
				org.apache.http.HttpEntity entity = response.getEntity();
				if (entity != null) {
					try (InputStream inputStream = entity.getContent()) {
						return ImageIO.read(inputStream);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
