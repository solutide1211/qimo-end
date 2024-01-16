package com.example.controller;

import cn.hutool.core.lang.Range;
import cn.hutool.core.lang.copier.SrcToDestCopier;
import com.example.entity.Result;
import com.example.entity.news;
import com.example.note.Log;
import com.example.service.newsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.ResponseWrapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class newsController {
    @Autowired
    newsService newsService;
    private static final String IMG_PATH = System.getProperty("user.dir") + "/news/";
    @Log
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "文件为空";
        }
        try{
            String fileName = file.getOriginalFilename();
            if(fileName == null){
                System.out.println("原始文件名为空");
                return "上传失败";
            }
            byte[] bytes = file.getBytes();
            final Path path = Paths.get(IMG_PATH + file.getOriginalFilename());
            System.out.println(path);
            Files.write(path,bytes);
            System.out.println(System.currentTimeMillis());
            return "/news/" + file.getOriginalFilename();
        }catch (Exception e){
            e.printStackTrace();
            return "上传失败";
        }
    }
    //上传附件
    @Log
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "文件为空";
        }
        try{
            String fileName = file.getOriginalFilename();
            if(fileName == null){
                System.out.println("原始文件名为空");
                return "文件上传失败";
            }
            byte[] bytes = file.getBytes();
            final Path path = Paths.get(IMG_PATH + file.getOriginalFilename());
            System.out.println(path);
            Files.write(path,bytes);
//            System.out.println(System.currentTimeMillis());
            return "/news/" + file.getOriginalFilename();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "文件上传失败";
        }
    }
    @Log
    @RequestMapping(value = "/addNews",method = RequestMethod.POST)
    //添加新闻
    public Result addNews(@RequestBody Map<String,Object> map){
        newsService.addNews(map);
        return Result.success("添加成功");
    }
    //查询所有新闻数据，传给前端
    @Log
    @RequestMapping(value = "/allNews",method = RequestMethod.GET)
    public List<news> getAllNews(){
        return newsService.getAllNews();
    }
    //回显功能  预览
    @Log
    @RequestMapping(value = "/content",method = RequestMethod.POST)
    public String see(@RequestBody Map<String,Object> map){
        final Integer id = (Integer) map.get("newId");
        return newsService.getContentById(id);
    }
    //标题查询新闻
    @Log
    @RequestMapping(value = "/title",method = RequestMethod.POST)
    public List<news> query(@RequestBody Map<String,Object> map){
        final String newsTitle = (String) map.get("news_title");
        return newsService.queryByTitle(newsTitle);
    }
    //删除新闻
    @Log
    @RequestMapping(value = "delNew",method = RequestMethod.POST)
    public Result delete(@RequestBody Map<String,Object> map){
        final Integer id = (Integer) map.get("newId");
        newsService.delById(id);
        return Result.success("删除新闻成功");
    }
    //通过newId获取该新闻信息
    @Log
    @RequestMapping(value = "queryId",method = RequestMethod.POST)
    public List<news> queryByID(@RequestBody Map<String,Object> map){
        final Integer id = (Integer) map.get("newId");
        return newsService.queryById(id);
    }

    //修改更新新闻
    @Log
    @RequestMapping(value = "/updateNews",method = RequestMethod.POST)
    public Result updateNews(@RequestBody Map<String,Object> map){
        final Integer id = (Integer) map.get("new_id");
        System.out.println(id);
        newsService.updateNew(map);
        return Result.success("更新新闻成功");
    }
    //发布
    @Log
    @RequestMapping(value = "release",method = RequestMethod.POST)
    public List<news> release(@RequestBody Map<String,Object> map){
//        final Integer id = (Integer) map.get("new_id");
        return newsService.queryById((Integer) map.get("new_id"));
    }


}
