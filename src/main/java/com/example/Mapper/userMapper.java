package com.example.Mapper;

import com.example.dto.addUserDTO;
import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface userMapper {
    @Select("select * from user where account=#{account}")
    User findByAccount(String account);
    //用来数据导出
    @Select("select * from user")
    List<User> findAll();
    @Select("select role from user where id=#{id}")
    User findRole(@Param("id") Integer id);
    @Insert("insert into user (name, age, email, account, password, role) values" +
            " (#{name}, #{age}, #{email}, #{account}, #{password}, #{role})")
    void addUser(addUserDTO adduserDTO);
    @Select("select * from user where name LIKE CONCAT('%',#{name},'%')")
    List<User> selectUserByName(String name);
    @Update("update user set name=#{name}, age=#{age}, email=#{email}, " +
            "account=#{account}, password=#{password}, role=#{role} where id=#{id}")
    void updateUser(User user);
    @Select("select * from user where id=#{id}")
    User getMine(Integer id);
//    @Update("update user set name=#{name}, age=#{age}, email=#{email}, " +
//            "account=#{account}, password=#{password}, role=#{role} where id=#{id}")
//    void updateMine(User user, Integer id);
//}
    @Update("update user set name=#{user.name}, age=#{user.age}, email=#{user.email}, " +
            "account=#{user.account}, password=#{user.password}, role=#{user.role} where id=#{id}")
    void updateMine(@Param("user") User user, @Param("id") Integer id);

    @Delete("delete from user where id=#{id}")
    void del(Integer id);
}
