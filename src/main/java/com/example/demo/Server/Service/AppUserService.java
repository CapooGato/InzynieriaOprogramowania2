package com.example.demo.Server.Service;

import com.example.demo.Server.Models.AppUsers;
import com.example.demo.Server.Repository.AppUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUsersRepository appUsersRepository;
    @Autowired
    public AppUserService(AppUsersRepository appUsersRepository){
        this.appUsersRepository = appUsersRepository;
    }

    public void saveUser(AppUsers appUsers){
        appUsersRepository.save(appUsers);
        System.out.println("Zapisano");
    }

}
