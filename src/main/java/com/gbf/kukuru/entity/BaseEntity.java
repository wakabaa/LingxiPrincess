package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
