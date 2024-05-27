package com.gbf.kukuru.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gbf.kukuru.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
