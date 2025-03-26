package com.example.demo.Server.Service;

import com.example.demo.Server.Models.Role;
import com.example.demo.Server.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getAllRole(){
       return repository.findAll();
    }

    public Optional<Role> getRoleById(long id){
        return repository.findById(id);
    }

}
