package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gbf_member_and_qq_group_bind_info")
public class GbfMemberAndQqGroupBindInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long gbfId;
    private Long groupId;
}
