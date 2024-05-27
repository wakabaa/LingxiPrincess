package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * QQ群黑名单 实体类
 *
 * @author ginoko
 * @since 2022-08-17
 */
@Data
@TableName("block_qq_group")
public class BlockQqGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 群ID
     */
    @TableId(value = "group_id", type = IdType.INPUT)
    private Long groupId;
}
