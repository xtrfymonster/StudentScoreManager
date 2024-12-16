package com.lc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName CommonController
 * @Deacription TODO
 **/
@Controller
public class CommonController {
    // 使用@RequestMapping注解将logout方法映射到"/logout"这个URL路径上
    // 当用户发送一个对"/logout"路径的请求时，这个方法将会被调用
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 调用HttpSession的invalidate方法使当前会话失效
        // 这会清除会话中存储的所有属性，例如用户登录信息等
        session.invalidate();
        // 返回一个重定向响应，将用户的浏览器重定向到"/index.html"页面
        // 通常这个页面可能是应用的登录页面或者首页
        return "redirect:/index.html";
    }
}