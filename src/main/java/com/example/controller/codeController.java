package com.example.controller;

import com.example.dto.codeDto;
import com.example.note.Log;
import com.example.service.codeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class codeController {
    @Autowired
    codeServiceImpl codeService;
    @Log
    @RequestMapping(value = "/code",method = RequestMethod.GET)
    public codeDto getCode(){
        return codeService.getCode();
    }
}
