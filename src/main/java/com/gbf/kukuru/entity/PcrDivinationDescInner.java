package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * PCR抽签描述 内部实体类
 *
 * @author ginoko
 * @since 2022-08-18
 */
@Data
public class PcrDivinationDescInner implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 运气点数
     */
    private Integer luckPoint;
    /**
     * 抽签内容
     */
    private String content;
}
