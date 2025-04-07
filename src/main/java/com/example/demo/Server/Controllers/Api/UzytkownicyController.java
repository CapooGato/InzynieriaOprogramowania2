package com.example.demo.Server.Controllers.Api;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Service.UzytkownicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/uzytkownicy")
public class UzytkownicyController {
    private final UzytkownicyService uzytkownicyService;

    @Autowired
    public UzytkownicyController(UzytkownicyService uzytkownicyService) {
        this.uzytkownicyService = uzytkownicyService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Uzytkownicy> createUzytkownik(@Valid @RequestBody Uzytkownicy uzytkownik){
        return new ResponseEntity<>(uzytkownicyService.addUzytkownicy(uzytkownik), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Uzytkownicy>> getUzytkownicy(){
        return new ResponseEntity<>(uzytkownicyService.getAllUzytkownicy(), HttpStatus.OK);
    }

    @GetMapping(value = "/{role}")
    public ResponseEntity<List<Uzytkownicy>> getUzytkownicyByRole(@PathVariable("role") String rola){
        return new ResponseEntity<>(uzytkownicyService.getAllUzytkownicyByRole(rola), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Uzytkownicy> updateUzytkownicy(@PathVariable("id") long id,
                                                         @Valid @RequestBody Uzytkownicy updatedUzytkownik){
        return new ResponseEntity<>(uzytkownicyService.updateUzytkownicy(id, updatedUzytkownik), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<String> deleteUzytkownikById(@PathVariable("id") long id){
        uzytkownicyService.removeUzytkownicyById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Uzytkowni z ID: " + id + "usuniety");
    }

}
