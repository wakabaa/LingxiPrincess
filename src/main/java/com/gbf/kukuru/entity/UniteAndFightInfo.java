package com.gbf.kukuru.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 古战场信息 实体类
 *
 * @author ginoko
 * @since 2022-08-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("unite_and_fight_info")
public class UniteAndFightInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 属性类别(0 风 1 土 2 水 3 火 4 暗 5 光)
     */
    private String elementType;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 预选开始时间
     */
    private Date prepareFightBeginTime;
    /**
     * 预选结束时间
     */
    private Date prepareFightEndTime;
    /**
     * 休息日开始时间
     */
    private Date restDayBeginTime;
    /**
     * 休息日结束时间
     */
    private Date restDayEndTime;
    /**
     * 本战开始时间
     */
    private Date formalFightBeginTime;
    /**
     * 本战结束时间
     */
    private Date formalFightEndTime;
    /**
     * 特殊战开始时间
     */
    private Date specialFightBeginTime;
    /**
     * 特殊战结束时间
     */
    private Date specialFightEndTime;

    /**
     * 获取有利属性
     *
     * @return 属性类别
     */
    public String getAdvantageElementType() {
        switch (this.elementType) {
            case "0":
                return "3";
            case "1":
                return "0";
            case "2":
                return "1";
            case "3":
                return "2";
            case "4":
                return "5";
            case "5":
                return "4";
            default:
                /* 未知属性 */
                return this.elementType;
        }
    }
}
