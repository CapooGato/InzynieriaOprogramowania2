package com.example.demo.Server.Controllers.Api;

import com.example.demo.Server.Models.Urzedy;
import com.example.demo.Server.Service.UrzedyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/urzedy")
public class UrzedyController {

    private final UrzedyService urzedyService;

    @Autowired
    public UrzedyController(UrzedyService urzedyService){
        this.urzedyService = urzedyService;
    }

    @GetMapping()
    public ResponseEntity<List<Urzedy>> getAllUrzedy(){
        List<Urzedy> urzedyList = urzedyService.getAllUrzedy();
        return urzedyList.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(urzedyList);
    }

    @PostMapping()
    public ResponseEntity<Urzedy> addUrzad(@Valid @RequestBody Urzedy urzedy){
        Urzedy savedUrzedy = urzedyService.addUrzad(urzedy);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUrzedy);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Urzedy> updateUrzad(@PathVariable Long id, @Valid @RequestBody Urzedy urzedy){
        Urzedy updatedUrzad = urzedyService.updateUrzad(id, urzedy)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Urzad o ID: " + id +
                                                                                            " nie zostal znaleziony"));
        return ResponseEntity.status(HttpStatus.OK).body(updatedUrzad);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> removeUrzadById(@PathVariable long id){
        Boolean deleted = urzedyService.removeUrzadById(id);
        return deleted
                ? ResponseEntity.status(HttpStatus.OK).body("Usunieto urzad o ID: " + id + ".")
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nie znaleziono urzedu o ID: " + id);
    }
}
