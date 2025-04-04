package com.example.demo.Server.Controllers.Api;

import com.example.demo.Server.Models.Role;
import com.example.demo.Server.Service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List<Role>> getAllRole(){
        List<Role> rolesList = roleService.getAllRole();
        return rolesList.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(rolesList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id){
        Role role =  roleService.getRoleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Rola o podanym ID: " + id +" nie została znaleziona"));
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @PostMapping()
    public ResponseEntity<Role> saveRole(@Valid @RequestBody Role role){
        Role savedRole = roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }
}
