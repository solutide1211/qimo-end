package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/*
验证码封装
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class codeDto {
    private String uuid;
    private String base64;
}
