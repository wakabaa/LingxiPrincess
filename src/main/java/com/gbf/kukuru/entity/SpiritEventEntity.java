package com.gbf.kukuru.entity;

import java.io.Serializable;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("activities")
public class SpiritEventEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private int type;

    private String name;

    private LocalTime startTime;

    private LocalTime endTime;

    private String dayOfWeek;

    private Integer weekOfMonth;

    private String description;
}
