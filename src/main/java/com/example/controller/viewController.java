package com.example.controller;

import com.example.entity.news;
import com.example.note.Log;
import com.example.service.newsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class viewController {
    @Autowired
    newsService newsService;
//    @RequestMapping(value = "test",method = RequestMethod.GET)
//    public String demo(){
//        return "daily";
//    }
    @Log
    @RequestMapping(value = "/daily",method = RequestMethod.GET)
    public String dailyNews(Model model, @RequestParam("new_id") Integer newId){
        final List<news> newsList = newsService.queryById(newId);
        model.addAttribute("newsList",newsList);
        return "daily";
    }
}
