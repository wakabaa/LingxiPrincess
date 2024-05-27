package com.gbf.kukuru.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
public class BotResponse {
    private String result;//API返回码
    private String content;//API返回数据
}
