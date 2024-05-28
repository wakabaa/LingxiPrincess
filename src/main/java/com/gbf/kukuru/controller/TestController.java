package com.gbf.kukuru.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * 外部接口调试 控制器
 *
 * @author Ginoko
 * @since 2022-05-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/testController")
public class TestController {
//    private ScheduleService scheduleService;

    /**
     * 测试接口
     */
    @GetMapping("/doTest")
    public void test() {
//        scheduleService.doBilibiliDynamicForward();
    }
}
