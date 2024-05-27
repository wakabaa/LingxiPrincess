package com.gbf.kukuru;

import com.gbf.kukuru.entity.BilibiliDynamic;
import com.gbf.kukuru.service.bilibili.dynamic.impl.BilibiliDynamicForwardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.gbf.kukuru.exception.KukuruException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Component
class SpringMiraiServerApplicationTests {

    @Resource
    private BilibiliDynamicForwardServiceImpl dynamicForwardService;

    @Test
    void contextLoads() throws KukuruException {
        BilibiliDynamic dynamic = dynamicForwardService.getNextDynamic(770359L, 664399103929614341L);
        System.out.println("check point");
    }

}
