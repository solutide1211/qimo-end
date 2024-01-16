package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class addUserDTO {
    private  String name;
    private String age;

    private String email;
    //    @JsonIgnore
    private String account;
    //    @JsonIgnore
    private String password;
    private String role;


}
