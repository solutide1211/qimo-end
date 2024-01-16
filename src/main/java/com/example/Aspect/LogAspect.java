package com.example.Aspect;

import com.example.Mapper.logMapper;
import com.example.entity.logo;
import com.example.note.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 定义@Before增强，拦截带有@Log注解的方法，并记录操作日志
     */
    @Autowired
    logMapper logMapper;
    @Before("@annotation(com.example.note.Log)")
    public void before(JoinPoint joinPoint) throws Exception{
        final logo logo =  new logo();
        //获取目标方法所在类
        String className = joinPoint.getClass().getName();
        //获取目标方法名
        String methodName = joinPoint.getSignature().getName();
        //获取目标方法参数
        Object[] args = joinPoint.getArgs();
        //获取Log注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        //记录操作日志，存入数据库中
        logo.setClassName(className);
        logo.setMethodName(methodName);
        logo.setArgs(Arrays.toString(args));
        logo.setOp(String.valueOf(logAnnotation));
        logMapper.insertLog(logo);

    }
}
