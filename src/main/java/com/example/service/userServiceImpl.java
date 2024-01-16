package com.example.service;

import com.example.Mapper.userMapper;
import com.example.dto.addUserDTO;
import com.example.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class userServiceImpl implements userService{
    @Autowired
    userMapper  userMapper;
    @Override
    public User findByAccount(String account) {
        return userMapper.findByAccount(account);
    }

    @Override
    public List<User> findAll() {
        return  userMapper.findAll();
    }

    @Override
    public PageInfo<User> findAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        final List<User> users = userMapper.findAll();
        final PageInfo<User> info = new PageInfo<>(users);
        return info;
    }
    @Override
    public User findRole(Integer id) {
        return userMapper.findRole(id);
    }

    @Override
    public void addUser(addUserDTO adduserDTO) {
        userMapper.addUser(adduserDTO);
    }

    @Override
    public List<User> selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public User getMine(Integer id) {
        return userMapper.getMine(id);
    }

    @Override
    public void updateMine(User user, Integer id) {
        if(id.equals(user.getId())){
            userMapper.updateMine(user,id);
        }
    }

    @Override
    public void del(Integer id) {
        userMapper.del(id);
    }


}
