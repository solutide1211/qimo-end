package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class news {
    private Integer newId;
    private String newTitle;
    private String newContent;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String newsFile;
}
