package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by daile on 2017/6/29.
 */
@Data
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private Integer age;
}
