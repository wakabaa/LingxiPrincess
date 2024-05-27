package com.gbf.kukuru.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * PCR抽签描述 实体类
 *
 * @author ginoko
 * @since 2022-08-18
 */
@Data
public class PcrDivinationDesc implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID列表
     */
    private List<Integer> charaIds;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 抽签类型列表
     */
    private List<PcrDivinationDescInner> types;
}
