package com.gbf.kukuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbf.kukuru.entity.SpiritEventEntity;
import com.gbf.kukuru.mapper.Spirit.SpiritMapper;

@Service
public class SpiritService {
    @Autowired
    private SpiritMapper spiritMapper;

    public List<SpiritEventEntity> selectSpiritEventByMonth(){
    	List<SpiritEventEntity> selectSpiritEvent = spiritMapper.selectSpiritEvent();
    	return selectSpiritEvent;
    }
}
