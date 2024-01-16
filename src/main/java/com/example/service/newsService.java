package com.example.service;

import com.example.entity.Result;
import com.example.entity.news;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public interface newsService {
    void addNews(Map<String, Object> map);

    List<news> getAllNews();

    String getContentById(Integer id);

    List<news> queryByTitle(String newsTitle);

    void delById(Integer id);

    List<news> queryById(Integer id);

    void updateNew(Map<String, Object> map);

    List<news> release(Map<String, Object> map);
//    Result<String> generator() throws IOException;
}
