package com.gbf.kukuru.service;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gbf.kukuru.dto.BotAccountDTO;
import com.gbf.kukuru.entity.BotAccount;
import com.gbf.kukuru.mapper.BotAccountMapper;
import com.gbf.kukuru.util.PathUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客户端守护进程 服务类
 *
 * @author ginoko
 * @since 2022-07-01
 */
@Slf4j
@Component
public class ClientDaemonService {

    @Value("${client.port-start}")
    private Integer clientPortStart;
    @Value("${client.max-fail-count}")
    private Integer maxFailCount;
    @Value("${client.enable-daemon}")
    private boolean enableDaemon;
    @Value("${client.login-name}")
    private String loginName;
    @Value("${client.login-password}")
    private String loginPassword;

    @Resource
    private BotAccountMapper botAccountMapper;
    private final List<BotAccountDTO> botList = new ArrayList<>();
    private final boolean isWindows = PathUtils.isWindows();

    /**
     * 更新账号列表
     */
    private void updateBotList() {
        int prevPort = this.clientPortStart + botList.size();
        int i = 0;
        List<BotAccount> newBotList = botAccountMapper.selectList(Wrappers.emptyWrapper());
        for (BotAccount newB : newBotList.stream()
                .filter(b -> botList.stream().noneMatch(oldB -> Objects.equals(oldB.getQqAccount(), b.getQqAccount())))
                .collect(Collectors.toList())) {
            BotAccountDTO accountDAO = new BotAccountDTO();
            accountDAO.setQqAccount(newB.getQqAccount());
            accountDAO.setQqPassword(newB.getQqPassword());
            accountDAO.setIsDisabled(newB.getIsDisabled());
            accountDAO.setPort(prevPort + i);
            accountDAO.setFailCount(0);
            botList.add(accountDAO);
            log.info("[客户端守护]: 发现新账号 QQ:" + accountDAO.getQqAccount() + " !");
            i++;
        }
    }

    @SneakyThrows
    private boolean isClientDeadLinux() {
        Process p = Runtime.getRuntime().exec(new String[]{
                "/bin/sh", "-c",
                "ps -ef | grep -v grep | grep Go-Mirai-Client"
        });
        InputStream stream = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        boolean isDead = true;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Go-Mirai-Client")) {
                isDead = false;
                break;
            }
        }
        return isDead;
    }

    @SneakyThrows
    private void startClientLinux(int port) {
        if (isClientDeadLinux()) {
            log.warn("[客户端守护]: 发现客户端已死, 准备重启...");
            Runtime.getRuntime().exec(new String[]{
                    "/" + PathUtils.getProjectJarPath() + "/../client/Go-Mirai-Client",
                    "-auth", loginName + "," + loginPassword,
                    "-port", String.valueOf(port),
                    ">/dev/null", "2>&1", "&"
            });
            ThreadUtil.sleep(1000);
            if (isClientDeadLinux()) {
                log.error("[客户端守护]: !! 客户端重启失败 !!");
            } else {
                log.info("[客户端守护]: 客户端重启成功!");
            }
        }
    }

    private void startClientWindows(int port) {
        // todo: 这里写windows下的客户端守护流程
    }

    @SneakyThrows
    private void startClient(int port) {
        if (isWindows) {
            startClientWindows(port);
        } else {
            startClientLinux(port);
        }
    }

    /**
     * 守护主流程
     * <p>
     * todo: 后续需要支持多账号登录
     */
    private void daemon() {
        for (BotAccountDTO account : botList) {
            if (Objects.equals(account.getIsDisabled(), "0")) {
                startClient(account.getPort());
                break;
            }
        }
    }

    /**
     * Go-Mirai-Client客户端守护进程
     */
    public void startClientDaemon() {
        if (enableDaemon) {
            log.info("[客户端守护]: 守护进程开始");
            for (int count = 12; true; count--) {
                try {
                    /* 每隔1分钟刷新机器人账号列表 */
                    if (count == 12) {
                        this.updateBotList();
                    } else if (count == 0) {
                        count = 13;
                    }
                    daemon();
                    ThreadUtil.sleep(5000);
                } catch (Exception e) {
                    log.error("[客户端守护]: !! 检测到异常 !!", e);
                }
            }
        }
    }
}
