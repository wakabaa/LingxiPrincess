package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("gbf_guild_member")
public class GbfGuildMember implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    private Long id;
    /**
     * 骑空团ID
     */
    private Long guildId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 等级
     */
    private int userRank;
    /**
     * 团内职位
     */
    private String position;
    /**
     * 最后更新的排名
     */
    private int lastRank;
    /**
     * 最后更新的贡献值
     */
    private Long lastPoint;
    /**
     * 贡献更新时间
     */
    private Date updateTime;
    /**
     * 是否隐藏(0 不隐藏 1 隐藏)
     */
    private String isHide;
}
