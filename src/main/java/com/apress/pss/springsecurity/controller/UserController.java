package com.apress.pss.springsecurity.controller;

import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {


    @GetMapping("/")
    public String homePage(ModelMap model){
        System.out.println("/ welcome");
        return "welcome";
    }

    //-----------------------------------
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/movies")
    public String movies(){
        return "movies";
    }

    @GetMapping("/showmovie")
    @ResponseBody
    public String showMovie(){
        return "movie x";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    //-----------------------------------
    @GetMapping("/welcome")
    public String welcomePage(ModelMap model){
        return "welcome";
    }

    @GetMapping("authenticated")
    public String adminPage(ModelMap  model){
        model.addAttribute("user",getPrincipal());
        return "authenticated";
    }

    @GetMapping("logout")
    public String logoutPage(HttpServletRequest request
            , HttpServletResponse response){
        Authentication auth= SecurityContextHolder
                .getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "welcome";
    }

    private String getPrincipal(){
        String userName=null;
        Object principal=SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            userName=((UserDetails)principal).getUsername();
        }else{
            userName=principal.toString();
        }
        return userName;
    }
}
