package com.example.demo.Server.Repository;

import com.example.demo.Server.Models.Uzytkownicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UzytkownicyRepository extends JpaRepository<Uzytkownicy,Long> {
    @Query("SELECT u FROM Uzytkownicy u WHERE u.rola = :nazwaRoli")
    List<Uzytkownicy> findAllByRola_Nazwa_roli(String nazwaRoli);
    Optional<Uzytkownicy> findByLogin(String login);
    @Query("SELECT u.rola FROM Uzytkownicy u")
    String findRola();
}
