package com.example.demo.Server.Controllers.Api;

import com.example.demo.Server.Models.Platnosci;

import com.example.demo.Server.Models.PlatnosciGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RestController
@RequestMapping("/api/platnosci")
public class PlatnosciController {

    private final List<Platnosci> wszystkiePlatnosci = new ArrayList<>();
    private long idCounter = 1;


    @GetMapping("/oczekujace")
    public ResponseEntity<List<Platnosci>> getOczekujacePlatnosci() {
        List<Platnosci> oczekujace = PlatnosciGenerator.wszystkiePlatnosci.stream()
                .filter(p -> "OCZEKUJACA".equalsIgnoreCase(p.getStatus()))
                .toList();

        return ResponseEntity.ok(oczekujace);
    }


    @PostMapping("/{id}/oplac")
    public ResponseEntity<?> oplacPlatnosc(@PathVariable Long id, @RequestBody Map<String, String> request) {
        for (Platnosci platnosc : PlatnosciGenerator.wszystkiePlatnosci) {
            if (platnosc.getId().equals(id)) {
                platnosc.setStatus("OPLACONA");
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(404).body("Nie znaleziono płatności");
    }

    private boolean nowePotrzebne() {
        // Można np. dodać nową płatność jeśli ostatnia była więcej niż X sekund temu
        return true; // Wersja testowa: zawsze dodaje
    }

    private Platnosci generujLosowaPlatnosc() {
        Platnosci p = new Platnosci();
        p.setId(System.currentTimeMillis());
        p.setKwota(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(100.0, 1000.0)));
        p.setTermin(LocalDate.now().plusDays(ThreadLocalRandom.current().nextInt(10, 15)));
        p.setStatus("OCZEKUJACA");
        return p;
    }
}