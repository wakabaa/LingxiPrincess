package com.gbf.kukuru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbf.kukuru.entity.SpiritEventEntity;
import com.gbf.kukuru.entity.SpiritShopEntity;
import com.gbf.kukuru.mapper.Spirit.SpiritMapper;

@Service
public class SpiritService {
    @Autowired
    private SpiritMapper spiritMapper;

    public List<SpiritEventEntity> selectSpiritEventByMonth(){
    	List<SpiritEventEntity> selectSpiritEvent = spiritMapper.selectSpiritEvent();
    	return selectSpiritEvent;
    }
    
    public List<SpiritEventEntity> selectSpiritEventByDay(){
    	List<SpiritEventEntity> selectSpiritEvent = spiritMapper.selectSpiritEvent();
    	return selectSpiritEvent;
    }
    
    // 查询所有群友推荐店铺
    public List<SpiritShopEntity> selectSpiritShop(){
    	List<SpiritShopEntity> selectSpiritEvent = spiritMapper.selectSpiritShop();
    	return selectSpiritEvent;
    }
    
    // 添加群友推荐店铺
    public int addSpiritShop(SpiritShopEntity spiritShopEntity){
    	return spiritMapper.addSpiritShop(spiritShopEntity);
    }
    
    // 删除群友推荐店铺
    public int deleteSpiritShop(SpiritShopEntity spiritShopEntity){
    	return spiritMapper.deleteSpiritShop(spiritShopEntity);
    }
}
