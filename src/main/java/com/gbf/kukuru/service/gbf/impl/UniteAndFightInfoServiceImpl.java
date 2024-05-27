package com.gbf.kukuru.service.gbf.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gbf.kukuru.entity.UniteAndFightInfo;
import com.gbf.kukuru.mapper.gbf.UniteAndFightInfoMapper;
import com.gbf.kukuru.service.gbf.IUniteAndFightInfoService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 古战场信息 服务实现类
 *
 * @author ginoko
 * @since 2022-08-06
 */
@Component
public class UniteAndFightInfoServiceImpl implements IUniteAndFightInfoService {
    @Resource
    private UniteAndFightInfoMapper uniteAndFightInfoMapper;

    @Override
    public UniteAndFightInfo getCurrentOrNext() {
        return uniteAndFightInfoMapper.selectCurrentOrNext();
    }

    @Override
    public UniteAndFightInfo getPrevious() {
        List<UniteAndFightInfo> infoList = uniteAndFightInfoMapper.selectList(
                Wrappers.<UniteAndFightInfo>lambdaQuery()
                        .orderByDesc(UniteAndFightInfo::getId)
        );
        if (CollectionUtils.isEmpty(infoList)) {
            return null;
        }
        else if (infoList.size() == 1) {
            return infoList.get(0);
        }
        else {
            return infoList.get(1);
        }
    }
}
