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

import com.gbf.kukuru.constant.CommandConstant;

import cn.hutool.json.JSONObject;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;

@Component
public class SpiritPlugin extends BotPlugin {

	String spiritApi = "https://xyq.gm.163.com/cgi-bin/csa/csa_sprite.py?act=ask&question={{question}}&product_name=xyq";
	
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        String msg = event.getRawMessage();
        long userId = event.getUserId();
        long groupId = event.getGroupId();
        

        if (msg.startsWith(CommandConstant.COMMAND_DREAM_JOURNEY_TO_THE_WEST)) {
            String question = msg.replace(CommandConstant.COMMAND_DREAM_JOURNEY_TO_THE_WEST, "").trim();
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
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    String answer = formatResponse(cleanHtml(response.toString()));
                    bot.sendGroupMsg(groupId, Msg.builder().at(userId).text(answer), false);
                } else {
                    throw new Exception("HTTP GET request failed with response code: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }
    
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        String msg = event.getRawMessage();
        long userId = event.getUserId();
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
                bot.sendPrivateMsg(userId, Msg.builder().text(answer), false);
            } else {
                throw new Exception("HTTP GET request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MESSAGE_IGNORE;
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
        answer = answer.replace("【精灵笔记】", "")
                       .replace("少侠可能想要问的是：", "您可能想问的是：")
                       .replace("小精灵提醒：", "提示：")
                       .replace("1、", "\n1. ")
                       .replace("2、", "\n2. ");
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
