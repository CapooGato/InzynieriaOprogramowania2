package com.example.demo.Server.Controllers.Api;

import com.example.demo.Server.Models.Wnioski;
import com.example.demo.Server.Service.WnioskiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/wnioski")
public class WnioskiController {

    private final WnioskiService wnioskiService;

    @Autowired
    public WnioskiController(WnioskiService wnioskiService){
        this.wnioskiService = wnioskiService;
    }

    @PostMapping()
    public ResponseEntity<Wnioski> addWnioski(@Valid @RequestBody Wnioski wniosek){
        Wnioski savedWniosek = wnioskiService.addWnioski(wniosek);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWniosek);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Wnioski> getWnioskiById(@PathVariable Long id){
        Wnioski wnioski = wnioskiService.getWnioskiById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono wniosku o ID: " + id));
        return ResponseEntity.status(HttpStatus.OK).body(wnioski);
    }

    @GetMapping(value = "/podatnik/{id}")
    public ResponseEntity<List<Wnioski>> getWnioskiByUzytkownikId(@PathVariable Long id){
        List<Wnioski> wnioskiList = wnioskiService.getWnioskiByUzytkownikId(id);
        return wnioskiList.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(wnioskiList);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Wnioski> updateWnioskiById(@PathVariable Long id, @Valid @RequestBody Wnioski wniosek){
        Wnioski updatedWniosek = wnioskiService.updateWnioski(id, wniosek);
        return ResponseEntity.status(HttpStatus.OK).body(updatedWniosek);
    }

}
