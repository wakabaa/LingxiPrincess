package com.gbf.kukuru.dto;

import com.gbf.kukuru.entity.BotAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机器人账号 数据传输类
 *
 * @author ginoko
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BotAccountDTO extends BotAccount {
    private static final long serialVersionUID = 1L;

    /**
     * 客户端端口号
     */
    private Integer port;
    /**
     * 失败计数
     */
    private Integer failCount;
}
