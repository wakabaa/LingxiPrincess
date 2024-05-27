package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * B站动态富文本节点 实体类
 *
 * @author ginoko
 * @since 2022-06-18
 */
@Data
public class BilibiliDynamicRichTextNode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 富文本类型
     * <pre>
     * RICH_TEXT_NODE_TYPE_TEXT     纯文本
     * RICH_TEXT_NODE_TYPE_WEB      网页链接
     * RICH_TEXT_NODE_TYPE_AT       at用户
     * RICH_TEXT_NODE_TYPE_BV       视频链接
     * RICH_TEXT_NODE_TYPE_TOPIC    话题
     * RICH_TEXT_NODE_TYPE_LOTTERY  抽奖
     * RICH_TEXT_NODE_TYPE_VOTE     投票
     * RICH_TEXT_NODE_TYPE_EMOJI    表情
     */
    private String type;
    /**
     * 原始文本
     */
    private String origText;
    /**
     * 文本
     */
    private String text;
    /**
     * 表情链接
     */
    private String emoji;
    /**
     * 关联ID
     */
    private String rid;
}
