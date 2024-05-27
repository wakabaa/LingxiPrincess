package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * B站动态附加相关内容 实体类
 */
@Data
public class BilibiliDynamicAdditional implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     * <pre>
     * ADDITIONAL_TYPE_COMMON 通用
     * ADDITIONAL_TYPE_RESERVE 预约
     */
    private String type;
    /**
     * 封面
     */
    private String cover;
    /**
     * 头
     */
    private String head;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述1
     */
    private String desc1;
    /**
     * 描述2
     */
    private String desc2;
}
