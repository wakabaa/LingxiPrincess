package com.gbf.kukuru.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("recommend_shop")
public class SpiritShopEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ShopId;

    private String type;

    private String description;
}
