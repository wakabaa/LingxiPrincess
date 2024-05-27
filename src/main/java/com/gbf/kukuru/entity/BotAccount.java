package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 机器人账号 实体类
 *
 * @author ginoko
 * @since 2022-07-01
 */
@Data
@TableName("bot_account")
public class BotAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * QQ号
     */
    private Long qqAccount;
    /**
     * QQ号的密码
     */
    private String qqPassword;
    /**
     * 是否被禁用(0 启用 1 禁用)
     */
    private String isDisabled;
}
