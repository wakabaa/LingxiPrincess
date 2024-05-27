package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 塔罗牌算法 实体类
 *
 * @author ginoko
 * @since 2022-08-11
 */
@Data
public class TarotAlgorithm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 牌位
     */
    private String position;
    /**
     * 牌位含义
     */
    private String meaning;
    /**
     * 是否支持逆位(true 支持; false 不支持)
     */
    private boolean supportReverse;
}
