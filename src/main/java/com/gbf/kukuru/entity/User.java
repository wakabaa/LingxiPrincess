package com.gbf.kukuru.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
@Data
public class User implements Serializable {
    //用户id
    private String user_id;
    //用户名
    private String username;
    //用户名
    private String password;
    //用户名
    private String salt;
    //用户名
    private String avatar;
    //用户名
    private String locked;
    //用户名
    private String ctime;
}

