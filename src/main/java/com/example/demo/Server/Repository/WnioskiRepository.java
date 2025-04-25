package com.example.demo.Server.Repository;

import com.example.demo.Server.Models.Wnioski;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WnioskiRepository extends JpaRepository<Wnioski,Long> {
    List<Wnioski> findAllByUzytkownik_UzytkownikId(Long id);

    List<Wnioski> findAllByUzytkownik_Urzad_UrzadId(Long id);
}
