package com.gbf.kukuru.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.gbf.kukuru.entity.BotResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 机器人AI 服务类
 */
@Component
public class RobotService {
    /**
     * 青云客平台的API地址
     */
    private final String uri = "https://api.qingyunke.com/api.php?key=free&appid=0&msg=%s";

    /**
     * 获取对话结果
     *
     * @param question：使用者输入的对话
     * @return Response对象，保存来自机器人的对话
     */
    @SneakyThrows
    public BotResponse answer(String question) {
        String ans = HttpUtil.get(String.format(uri, URLEncoder.encode(question, StandardCharsets.UTF_8)));
        if (!ans.contains("##出现错误，")) {
            return JSONUtil.toBean(ans, BotResponse.class);
        } else {
            BotResponse response = new BotResponse();
            response.setContent(ans);
            return response;
        }
    }
}
