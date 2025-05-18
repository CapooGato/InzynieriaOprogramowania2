package com.example.demo.Server.Repository;

import com.example.demo.Server.Models.Platnosci;
import com.example.demo.Server.Models.Uzytkownicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatnosciRepository extends JpaRepository<Platnosci, Long> {
    List<Platnosci> findByUzytkownikAndStatus(Uzytkownicy uzytkownik, String status);
}