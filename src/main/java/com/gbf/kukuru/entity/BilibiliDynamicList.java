package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * B站动态列表 实体类
 *
 * @author ginoko
 * @since 2022-05-14
 */
@Data
public class BilibiliDynamicList implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否还有分页
     */
    private boolean hasMore;
    /**
     * 分页偏移量
     */
    private Long offset;
    /**
     * 动态作者信息
     */
    private BilibiliUser author;
    /**
     * 动态列表
     */
    private List<BilibiliDynamic> items;
}
