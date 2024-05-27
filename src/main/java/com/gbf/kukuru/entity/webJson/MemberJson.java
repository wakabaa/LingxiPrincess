package com.gbf.kukuru.entity.webJson;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberJson implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    Long userid;
    /**
     * 昵称
     */
    String name;
    /**
     * 角色等级
     */
    int level;
}
