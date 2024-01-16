package com.example.Mapper;

import com.example.entity.logo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface logMapper {
    @Insert("insert into log (class_name,method_name,args,op)" +
            " values (#{className},#{methodName},#{args},#{op})")
    void insertLog(logo log);
}
