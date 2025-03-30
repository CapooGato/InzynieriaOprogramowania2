package com.example.demo.Server.Repository;

import com.example.demo.Server.Models.Wnioski;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WnioskiRepository extends JpaRepository<Wnioski,Long> {
    @Query("SELECT u FROM Uzytkownicy u WHERE u.uzytkownikId = :uzytkownikId")
    Optional<Wnioski> findAllByUzytkownik_Uzytkownik_id(Long uzytkownikId);
}
