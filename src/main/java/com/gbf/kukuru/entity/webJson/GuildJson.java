package com.gbf.kukuru.entity.webJson;

import lombok.Data;

import java.io.Serializable;

@Data
public class GuildJson implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 骑空团ID
     */
    private String guildid;
    /**
     * 骑空团名称
     */
    private String name;
    /**
     * 预选总贡献
     */
    private Long point;
    /**
     * 预选排名
     */
    private String rank;
    /**
     * 是否是种子团
     */
    private String seed;
}
