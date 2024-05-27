package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * B站动态历史信息 实体类
 *
 * @author ginoko
 * @since 2022-05-11
 */
@Data
@TableName("bilibili_dynamic_history_info")
@EqualsAndHashCode(callSuper = true)
public class BilibiliDynamicHistoryInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * B站用户ID
     */
    private Long uid;
    /**
     * 最新动态ID
     */
    private Long latestDynamicId;
}
