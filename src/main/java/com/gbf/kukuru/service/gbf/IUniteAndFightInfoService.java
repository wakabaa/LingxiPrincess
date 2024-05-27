package com.gbf.kukuru.service.gbf;

import com.gbf.kukuru.entity.UniteAndFightInfo;

/**
 * 古战场信息 服务接口
 *
 * @author ginoko
 * @since 2022-08-06
 */
public interface IUniteAndFightInfoService {

    /**
     * 找到当前正在进行的古战场信息, 若没有, 则获取下一次古战场的信息
     *
     * @return 古战场信息实体
     */
    UniteAndFightInfo getCurrentOrNext();

    /**
     * 获取上一个古战场信息
     *
     * @return 古战场信息实体
     */
    UniteAndFightInfo getPrevious();
}
