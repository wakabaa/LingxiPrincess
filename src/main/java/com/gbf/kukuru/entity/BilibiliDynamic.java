package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * B站动态信息 实体类
 *
 * @author ginoko
 * @since 2022-05-15
 */
@Data
public class BilibiliDynamic implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 动态ID
     */
    private Long id;
    /**
     * 动态类型
     * <pre>
     * DYNAMIC_TYPE_WORD        文本动态
     * DYNAMIC_TYPE_DRAW        图片动态
     * DYNAMIC_TYPE_AV          视频动态
     * DYNAMIC_TYPE_FORWARD     转发动态
     * DYNAMIC_TYPE_LIVE_RCMD   直播动态
     */
    private String type;
    /**
     * 发布时间 (10位时间戳)
     */
    private Long publishTime;
    /**
     * 作者的B站用户ID
     */
    private Long authorUid;
    /**
     * 作者名称
     */
    private String author;
    /**
     * 作者头像
     */
    private String authorAvatar;
    /**
     * 作者头像挂件
     */
    private String authorPendant;
    /**
     * 富文本列表
     */
    private List<BilibiliDynamicRichTextNode> richTextNodeList;
    /**
     * 视频标题
     */
    private String videoTitle;
    /**
     * 视频链接
     */
    private String videoLink;
    /**
     * 视频封面图片链接
     */
    private String videoCover;
    /**
     * 视频简介
     */
    private String videoDesc;
    /**
     * 图片链接列表
     */
    private List<String> images;
    /**
     * 转发的原动态
     */
    private BilibiliDynamic origin;
    /**
     * 话题文本
     */
    private String topic;
    /**
     * 附加相关内容
     */
    private BilibiliDynamicAdditional additional;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 转发数
     */
    private Integer forwardCount;
    /**
     * 喜爱数
     */
    private Integer likeCount;
}
