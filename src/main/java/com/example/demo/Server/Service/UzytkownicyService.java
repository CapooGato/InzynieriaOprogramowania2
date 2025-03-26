package com.example.demo.Server.Service;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Repository.UzytkownicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UzytkownicyService {
    private final UzytkownicyRepository repository;
    @Autowired
    public UzytkownicyService(UzytkownicyRepository repository) {
        this.repository = repository;
    }
    public void addUzytkownicy(Uzytkownicy uzytkownik){
        repository.save(uzytkownik);
    }
    public void removeUzytkownicyById(long id){
        repository.deleteById(id);
    }
    public void updateUzytkownicy(Long id, Uzytkownicy updateUzytkownik){
        Optional<Uzytkownicy> optionalUzytkownicy = repository.findById(id);
        if(optionalUzytkownicy.isPresent()){
            optionalUzytkownicy.get().setLogin(updateUzytkownik.getLogin());
            optionalUzytkownicy.get().setHaslo(updateUzytkownik.getHaslo());
            optionalUzytkownicy.get().setUrzad(updateUzytkownik.getUrzad());
            optionalUzytkownicy.get().setRola(updateUzytkownik.getRola());
            repository.save(optionalUzytkownicy.get());
        };
    }
    public Optional<Uzytkownicy> getAllUzytkownicyByRole(String nazwaRoli){
       return repository.findAllByRola_Nazwa_roli(nazwaRoli);
    }
}
