package com.example.demo.Server.Service;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Repository.UzytkownicyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public List<Uzytkownicy> getAllUzytkownicyByRole(String nazwaRoli){
       return uzytkownicyRepository.findAllByRola_Nazwa_roli(nazwaRoli);
    }

    public List<Uzytkownicy> getAllUzytkownicy(){
        return uzytkownicyRepository.findAll();
    }
    public Boolean findUserByLogin(String login){
        return uzytkownicyRepository.findByLogin(login).isPresent();
    }

    public Uzytkownicy updateUzytkownicy(Long id, Uzytkownicy updateUzytkownik){
        Optional<Uzytkownicy> optionalUzytkownicy = uzytkownicyRepository.findById(id);
        if(optionalUzytkownicy.isPresent()){
            optionalUzytkownicy.get().setLogin(updateUzytkownik.getLogin());
            optionalUzytkownicy.get().setHaslo(updateUzytkownik.getHaslo());
            optionalUzytkownicy.get().setUrzad(updateUzytkownik.getUrzad());
            optionalUzytkownicy.get().setRola(updateUzytkownik.getRola());
            return uzytkownicyRepository.save(optionalUzytkownicy.get());
        }else {
            throw new EntityNotFoundException("Nie znaleziono użytkownika o id: " + id);
        }
    }

    public void removeUzytkownicyById(long id){
        uzytkownicyRepository.deleteById(id);
    }


    public String getViewByRole(Uzytkownicy uzytkownik){
        String rola = uzytkownicyRepository.findRola();
        return rola;
    }

    public Optional<Uzytkownicy> findByLogin(String login) {
        return uzytkownicyRepository.findByLogin(login);
    }

    public List<Uzytkownicy> getAllUsers() {
        return uzytkownicyRepository.findAll();
    }

    public void usunUzytkownikaPoId(Integer id) {
        uzytkownicyRepository.deleteById(Long.valueOf(id));
    }

}
