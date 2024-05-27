package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 骑空团ID与QQ群ID绑定关系 实体类
 *
 * @author ginoko
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gbf_guild_and_qq_group_bind_info")
public class GbfGuildAndQqGroupBindInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 骑空团ID
     */
    private Long guildId;
    /**
     * QQ群ID
     */
    private Long groupId;
}
