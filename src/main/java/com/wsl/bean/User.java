package com.wsl.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author tan
 * @version 1.0
 * @date 2020/9/24 14:47
 */
@Data
@AllArgsConstructor
public class User {

    private String id;
    private String userName;
    private String password;

    private Set<Role> roles;

}
