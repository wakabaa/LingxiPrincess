package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 直播监听&QQ群绑定关系 实体类
 *
 * @author ginoko
 * @since 2022-08-09
 */
@Data
@TableName("live_group_bind")
@EqualsAndHashCode(callSuper = true)
public class LiveGroupBind extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * B站用户ID
     */
    private Long uid;
    /**
     * QQ群ID
     */
    private Long groupId;
}
