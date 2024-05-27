package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
public class QQGroupMember {
    //QQ号
    private String id;
    //昵称
    @TableField(value="nickName")
    private String nickName;
    //好感对
    private String favorability;
    //对应GBF账号id
    private String gameid;
}
