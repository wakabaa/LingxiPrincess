package com.gbf.kukuru.entity.webJson;

import cn.hutool.json.JSONArray;
import lombok.Data;

import java.io.Serializable;

@Data
public class GbfTeamRaidWebJson implements Serializable {
    private static final long serialVersionUID = 1L;

    private String msg;
    private JSONArray result;
    private String err;
}
