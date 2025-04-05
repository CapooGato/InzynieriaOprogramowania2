package com.example.demo.Server.Service;


import com.example.demo.Server.Models.Wnioski;
import com.example.demo.Server.Repository.WnioskiRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WnioskiService {
    private final WnioskiRepository repository;

    @Autowired
    public WnioskiService(WnioskiRepository repository) {
        this.repository = repository;
    }
    public Wnioski addWnioski(Wnioski wniosek){
        return repository.save(wniosek);
    }
    public Wnioski updateWnioski(Long id, Wnioski wniosek) {
        Optional<Wnioski> optionalWnioski = repository.findById(id);
        if (optionalWnioski.isPresent()) {
            optionalWnioski.get().setUri(wniosek.getUri());
            optionalWnioski.get().setStatus(wniosek.getStatus());
            optionalWnioski.get().setkomentarzDoWniosku(wniosek.getkomentarzDoWniosku());
            return repository.save(optionalWnioski.get());
        }else {
            throw new EntityNotFoundException("Wniosek o ID: " + id + " nie zostal znaleziony");
        }
    }
    public Optional<Wnioski> getWnioskiById(Long id){
        return repository.findById(id);
    }
    public List<Wnioski> getWnioskiByUzytkownikId(Long id){
        return repository.findAllByUzytkownik_Uzytkownik_id(id);
    }

}
