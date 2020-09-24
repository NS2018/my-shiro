package com.wsl.controller;

import com.wsl.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tan
 * @version 1.0
 * @date 2020/9/24 15:09
 */
@RestController
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(User user){

        if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())){
            return "请输入用户名和密码";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword());

        try{
            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException e){
            log.error("用户名不存在");
            return "用户名不存在";
        }catch (AuthenticationException e){
            log.error("账号或密码错误");
            return "账号或密码错误";
        }catch (AuthorizationException e){
            log.error("没有权限");
            return "没有权限";
        }

        return "login success";
    }

    @RequiresPermissions("admin")
    @GetMapping("/admin")
    public String admin(){
        return "admin success";
    }

    @RequiresPermissions("add")
    @GetMapping("/add")
    public String add(){
        return "add success";
    }

    @RequiresPermissions(("query"))
    @GetMapping("/index")
    public String index(){
        return "index success";
    }
}
