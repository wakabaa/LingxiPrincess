package com.gbf.kukuru.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * gbf个排贡献 数据传输类
 *
 * @author ginoko
 * @since 2022-06-24
 */
@Data
public class GbfSoloPointDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * QQ群ID或账号ID
     */
    private Long Id;
    /**
     * 贡献结果
     */
    private String pointResult;
}
