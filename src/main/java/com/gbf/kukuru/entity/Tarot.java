package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 塔罗牌 实体类
 *
 * @author ginoko
 * @since 2022-08-11
 */
@Data
public class Tarot implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 正位含义
     */
    private String meaning;
    /**
     * 逆位含义
     */
    private String reverseMeaning;
}
