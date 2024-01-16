package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private  String name;
    private String age;

    private String email;
//    @JsonIgnore
    private String account;
//    @JsonIgnore
    private String password;
    private String role;
}
