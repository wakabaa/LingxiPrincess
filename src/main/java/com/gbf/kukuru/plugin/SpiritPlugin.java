package com.gbf.kukuru.plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.PrivateMessageHandler;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;

import cn.hutool.json.JSONObject;

@Shiro
@Component
public class SpiritPlugin {

	String spiritApi = "https://xyq.gm.163.com/cgi-bin/csa/csa_sprite.py?act=ask&question={{question}}&product_name=xyq";

	@GroupMessageHandler
	public void onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
		String msg = event.getRawMessage();
		long userId = event.getUserId();
		long groupId = event.getGroupId();

		if (msg.startsWith("梦幻精灵")) {
			String question = msg.replace("梦幻精灵", "").trim();
			try {
				String encodedQuestion = URLEncoder.encode(question, StandardCharsets.UTF_8.toString());
				String apiUrl = spiritApi.replace("{{question}}", encodedQuestion);
				URL url = new URL(apiUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				connection.connect();

				int responseCode = connection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					StringBuilder response = new StringBuilder();
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(connection.getInputStream()))) {
						String line;
						while ((line = reader.readLine()) != null) {
							response.append(line);
						}
					}
					String answer = formatResponse(cleanHtml(response.toString()));
					// 构建消息
					String sendMsg = MsgUtils.builder().text(answer).at(userId).build();
					// 发送私聊消息
					bot.sendGroupMsg(groupId, sendMsg, false);
				} else {
					throw new Exception("HTTP GET request failed with response code: " + responseCode);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@PrivateMessageHandler
	public void onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
		String msg = event.getRawMessage();
		try {
			String encodedQuestion = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
			String apiUrl = spiritApi.replace("{{question}}", encodedQuestion);
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				StringBuilder response = new StringBuilder();
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
				}
				String answer = formatResponse(cleanHtml(response.toString()));
				// 构建消息
		        String sendMsg = MsgUtils.builder().text(answer).build();
		        // 发送私聊消息
		        bot.sendPrivateMsg(event.getUserId(), sendMsg, false);

			} else {
				throw new Exception("HTTP GET request failed with response code: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String cleanHtml(String rawHtml) {
		// 处理HTML实体
		String unescapedHtml = StringEscapeUtils.unescapeHtml4(rawHtml);
		// 移除HTML标签
		String noHtml = unescapedHtml.replaceAll("<[^>]+>", "");
		// 处理Unicode转义字符
		String decodedText = decodeUnicode(noHtml);
		return decodedText;
	}

	private String formatResponse(String jsonResponse) {
		JSONObject jsonObject = new JSONObject(jsonResponse);
		String answer = jsonObject.getStr("answer");
		// 进一步处理answer字符串，使其更易读
		answer = answer.replace("【精灵笔记】", "").replace("少侠可能想要问的是：", "您可能想问的是：").replace("小精灵提醒：", "提示：")
				.replace("1、", "\n1. ").replace("2、", "\n2. ");
		return answer.trim();
	}

	private static String decodeUnicode(String text) {
		StringBuilder result = new StringBuilder();
		int i = 0;
		while (i < text.length()) {
			char ch = text.charAt(i);
			if (ch == '\\' && i + 1 < text.length() && text.charAt(i + 1) == 'u') {
				String unicode = text.substring(i + 2, i + 6);
				result.append((char) Integer.parseInt(unicode, 16));
				i += 6;
			} else {
				result.append(ch);
				i++;
			}
		}
		return result.toString();
	}
}
