package com.gbf.kukuru.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.gbf.kukuru.service.ClientDaemonService;

import cn.hutool.core.thread.ThreadUtil;

/**
 * Kukuru机器人启动成功的回调接口
 *
 * @author ginoko
 * @since 2022-06-02
 */
@Component
public class KukuruRunner implements ApplicationRunner {

    @Autowired
    private ClientDaemonService daemonService;

    @Override
    public void run(ApplicationArguments args) {
        /* Go-Mirai-Client守护进程 */
        ThreadUtil.execute(() -> daemonService.startClientDaemon());
    }
}
