package com.gbf.kukuru.interceptor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class MyWsInterceptor {
//    @SneakyThrows
//    @Override
//    public boolean checkSession(@NotNull WebSocketSession session) {
//        HttpHeaders headers = session.getHandshakeHeaders();
//        String botId = headers.getFirst("x-self-id");
//        System.out.println(headers);
//        System.out.println("新的连接" + botId);
//        if ("123".equals(botId)) {
//            System.out.println("机器人账号是123，关闭连接");
//            session.close();
//            return false; // 禁止连接
//        }
//        return true; // 正常连接
//    }
}
