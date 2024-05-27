package com.gbf.kukuru.service;

import com.gbf.kukuru.entity.QQGroupMember;
import com.gbf.kukuru.entity.User;
import com.gbf.kukuru.mapper.QQGroupMemberMapper;
import com.gbf.kukuru.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QQGroupMemberMapper qqGroupMemberMapper;

    public String updateById(String userid){
        User user1 = new User();
        user1.setUser_id("123");
        List<User> user = userMapper.selectList(null);
        return user.get(0).getUser_id();
    }
    public String checkhasNickName(long userid){
        QQGroupMember qqGroupMember = qqGroupMemberMapper.selectById(userid);
        if(qqGroupMember != null){
            return qqGroupMember.getNickName();
        }
        return null;
    }
}
