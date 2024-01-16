package com.example.service;


import com.example.Mapper.newsMapper;
import com.example.entity.Result;
import com.example.entity.news;

import jdk.nashorn.internal.runtime.Context;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class newsServiceImpl implements newsService{
    @Autowired
    newsMapper newsMapper;

    @Override
    public void addNews(Map<String, Object> map) {
        newsMapper.addNews(map);
    }

    @Override
    public List<news> getAllNews() {
        return newsMapper.getAllNews();
    }

    @Override
    public String getContentById(Integer id) {
        final String content = newsMapper.getContent(id);
        System.out.println(content);
        return content;

    }

    @Override
    public List<news> queryByTitle(String newsTitle) {
        return newsMapper.queryByTitle(newsTitle);
    }

    @Override
    public void delById(Integer id) {
        newsMapper.delById(id);
    }

    @Override
    public List<news> queryById(Integer id) {
        return newsMapper.queryById(id);
    }

    @Override
    public void updateNew(Map<String, Object> map) {
        newsMapper.updateNews(map);
    }

    @Override
    public List<news> release(Map<String, Object> map) {
        return newsMapper.queryById((Integer) map.get("new_id"));
    }

//    @Override
//    public Result<String> generator() throws IOException {
//        //创建模板解析器
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("templates/");
//        resolver.setSuffix(".html");
//
//        //创建模板引擎
//        TemplateEngine engine = new TemplateEngine();
//        engine.setTemplateResolver(resolver);
//
//        // 创建上下文对象
//        Context context = new Context();
//        LambdaQueryWrapper<news> wrapper = new LambdaQueryWrapper<news>().eq(news::getStatus,NewsConstant.PUBLISH);
//        List<news> newsList = newsMapper.selectList(wrapper);
//        context.setVariable("newsList", newsList);
//
//        //文件输出的路径及文件名
//        FileWriter writer = new FileWriter("src/main/resources/static/views.html");
//
//        // 生成HTML代码，参数：模板，数据，文件输出流
//        engine.process("index", context, writer);
//        //关闭文件
//        writer.close();
//
//        return Result.success("页面成功生成");
//    }

}
