package com.example.demo.Server.Controllers.Api;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Service.UzytkownicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/")
public class UzytkownicyController {
    private final UzytkownicyService uzytkownicyService;

    @Autowired
    public UzytkownicyController(UzytkownicyService uzytkownicyService) {
        this.uzytkownicyService = uzytkownicyService;
    }

    @PostMapping(value = "uzytkownicy/create")
    public ResponseEntity<Uzytkownicy> createUzytkownik(@Valid @RequestBody Uzytkownicy uzytkownik){
        return new ResponseEntity<>(uzytkownicyService.addUzytkownicy(uzytkownik), HttpStatus.CREATED);
    }

    @GetMapping(value = "uzytkownicy")
    public ResponseEntity<List<Uzytkownicy>> getUzytkownicy(){
        return new ResponseEntity<>(uzytkownicyService.getAllUzytkownicy(), HttpStatus.OK);
    }

    @GetMapping(value = "uzytkownicy/{role}")
    public ResponseEntity<List<Uzytkownicy>> getUzytkownicyByRole(@PathVariable("role") String rola){
        return new ResponseEntity<>(uzytkownicyService.getAllUzytkownicyByRole(rola), HttpStatus.OK);
    }

    @GetMapping("/api/uzytkownicy")
    @ResponseBody
    public List<Uzytkownicy> getAllUsers() {
        return uzytkownicyService.getAllUsers();
    }


    @PutMapping(value = "uzytkownicy/{id}/update")
    public ResponseEntity<Uzytkownicy> updateUzytkownicy(@PathVariable("id") long id,
                                                         @Valid @RequestBody Uzytkownicy updatedUzytkownik){
        return new ResponseEntity<>(uzytkownicyService.updateUzytkownicy(id, updatedUzytkownik), HttpStatus.OK);
    }

    @DeleteMapping(value = "uzytkownicy/{id}/delete")
    public ResponseEntity<String> deleteUzytkownikById(@PathVariable("id") long id){
        uzytkownicyService.removeUzytkownicyById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Uzytkowni z ID: " + id + "usuniety");
    }

}
