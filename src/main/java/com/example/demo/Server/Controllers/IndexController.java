package com.example.demo.Server.Controllers;

import com.example.demo.Server.Models.AppUsers;
import com.example.demo.Server.Service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    private final AppUserService appUserService;

    @Autowired
    public IndexController(AppUserService appUserService){
        this.appUserService = appUserService;
    }
    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<AppUsers> addUser(@RequestBody AppUsers appUsers){
        appUserService.saveUser(appUsers);
        return ResponseEntity.ok(appUsers);
    }
}
