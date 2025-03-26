package com.example.demo.Server.Service;


import com.example.demo.Server.Models.Wnioski;
import com.example.demo.Server.Repository.WnioskiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WnioskiService {
    private final WnioskiRepository repository;

    @Autowired
    public WnioskiService(WnioskiRepository repository) {
        this.repository = repository;
    }
    public void addWnioski(Wnioski wniosek){
        repository.save(wniosek);
    }
    public void updateWnioski(Long id, Wnioski wniosek) {
        Optional<Wnioski> optionalWnioski = repository.findById(id);
        if (optionalWnioski.isPresent()) {
            optionalWnioski.get().setUri(wniosek.getUri());
            optionalWnioski.get().setStatus(wniosek.getStatus());
            optionalWnioski.get().setKomentarz_do_wniosku(wniosek.getKomentarz_do_wniosku());
            repository.save(optionalWnioski.get());
        }
    }
    public Optional<Wnioski> getWnioskiById(Long id){
        return repository.findById(id);
    }
    public Optional<Wnioski> getWnioskiByUzytkownikId(Long id){
        return repository.findAllByUzytkownik_Uzytkownik_id(id);
    }

}
