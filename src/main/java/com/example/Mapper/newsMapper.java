package com.example.Mapper;

import com.example.entity.news;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface newsMapper {
    @Insert("insert into news(new_title,new_content,create_time,create_user,news_file) values " +
            "(#{new_title},#{new_content},#{create_time},#{create_user},#{news_file})")
    void addNews(Map<String, Object> map);
    @Select("select * from news")
    List<news> getAllNews();
    @Select("select new_content from news where new_id=#{id}")
    String getContent(Integer id);
    @Select("select * from news where new_title LIKE CONCAT('%',#{newsTitle},'%')")
    List<news> queryByTitle(String newsTitle);
    @Delete("delete from news where new_id=#{id}")
    void delById(Integer id);
    @Select("select * from news where new_id=#{id}")
    List<news> queryById(Integer id);
    @Update("update news set new_title=#{new_title},new_content=#{news_content}," +
            "update_time=#{updateTime}" +
            " where new_id=#{new_id}")
    void updateNews(Map<String, Object> map);

}
