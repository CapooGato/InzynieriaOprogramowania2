package com.example.demo.Server.Models;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PlatnosciGenerator {

    public static final List<Platnosci> wszystkiePlatnosci = new CopyOnWriteArrayList<>();

    // Co 20–30 sekund (losowo) — dynamicznie sterowane wewnątrz metody
    @Scheduled(fixedDelayString = "#{T(java.util.concurrent.ThreadLocalRandom).current().nextInt(20000, 30000)}")
    public void dodajLosowaPlatnosc() {
        Platnosci p = new Platnosci();
        p.setId(System.currentTimeMillis());
        p.setKwota(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(100.0, 1000.0)));
        p.setTermin(LocalDate.now().plusDays(ThreadLocalRandom.current().nextInt(1, 10)));
        p.setStatus("OCZEKUJACA");
        wszystkiePlatnosci.add(p);

        System.out.println("Dodano płatność: " + p.getId());
    }
}