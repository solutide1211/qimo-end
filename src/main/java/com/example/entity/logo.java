package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.connection.RedisServer;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class logo {
    /**
     * className 获取目标方法所在类
     * methodName 获取目标方法名
     * args 获取目标方法参数
     * op 获取所进行的操作
     */
    private String className;
    private String methodName;
    private String args;
    private String op;
}
