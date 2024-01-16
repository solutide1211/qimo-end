package com.example.service;

import com.example.dto.addUserDTO;
import com.example.entity.Result;
import com.example.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface userService {
    User findByAccount(String account);

    List<User> findAll();

    PageInfo<User> findAllUser(int pageNum, int pageSize);

    User findRole(Integer id);

    void addUser(addUserDTO adduserDTO);

    List<User> selectUserByName(String name);

    void updateUser(User user);

    User getMine(Integer id);

    void updateMine(User user, Integer id);

    void del(Integer id);

}
