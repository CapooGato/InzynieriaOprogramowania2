package com.example.demo.Server.Service;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Repository.UzytkownicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UzytkownicyService {
    private final UzytkownicyRepository uzytkownicyRepository;
    @Autowired
    public UzytkownicyService(UzytkownicyRepository uzytkownicyRepository) {
        this.uzytkownicyRepository = uzytkownicyRepository;
    }
    public Uzytkownicy addUzytkownicy(Uzytkownicy uzytkownik){
        return uzytkownicyRepository.save(uzytkownik);
    }
    public void removeUzytkownicyById(long id){
        uzytkownicyRepository.deleteById(id);
    }
    public void updateUzytkownicy(Long id, Uzytkownicy updateUzytkownik){
        Optional<Uzytkownicy> optionalUzytkownicy = uzytkownicyRepository.findById(id);
        if(optionalUzytkownicy.isPresent()){
            optionalUzytkownicy.get().setLogin(updateUzytkownik.getLogin());
            optionalUzytkownicy.get().setHaslo(updateUzytkownik.getHaslo());
            optionalUzytkownicy.get().setUrzad(updateUzytkownik.getUrzad());
            optionalUzytkownicy.get().setRola(updateUzytkownik.getRola());
            uzytkownicyRepository.save(optionalUzytkownicy.get());
        };
    }
    public Optional<Uzytkownicy> getAllUzytkownicyByRole(String nazwaRoli){
       return uzytkownicyRepository.findAllByRola_Nazwa_roli(nazwaRoli);
    }

    public Boolean findUserByLogin(String login){
        return uzytkownicyRepository.findByLogin(login).isPresent();
    }

    /**
     * Zwraca ścieżke do odpowiedniego html po roli.
     * np: Rola: ADMIN, zwróci "/admin/admin"
     * */
    public String getViewByRole(Uzytkownicy uzytkownik){
        String rola = uzytkownik.getRola().getNazwaRoli();
        return "/" + rola.toLowerCase() + "/" + rola.toLowerCase();
    }
}
