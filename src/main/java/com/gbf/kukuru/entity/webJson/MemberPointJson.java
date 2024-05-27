package com.gbf.kukuru.entity.webJson;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberPointJson implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前总贡献
     */
    Long point;
    /**
     * 数据更新时间
     */
    Long updatetime;
    /**
     * 个人排名
     */
    int rank;
}
