package com.example.demo.Server.Service;

import com.example.demo.Server.Models.Urzedy;
import com.example.demo.Server.Repository.UrzedyRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrzedyService {
    private final UrzedyRepository urzedyRepository;

    @Autowired
    public UrzedyService(UrzedyRepository urzedyRepository) {
        this.urzedyRepository = urzedyRepository;
    }
    public List<Urzedy> getAllUrzedy(){
        return urzedyRepository.findAll();
    }
    public Urzedy addUrzad(Urzedy urzad){
        urzad.setUrzadId(null); // upewnij się że nowy rekord nie ma ID
        return urzedyRepository.save(urzad);
    }

    public Optional<Urzedy> updateUrzad(Long id, Urzedy updateUrzad) {
        return urzedyRepository.findById(id)
                .map(urzad -> {
                    urzad.setMiejscowosc(updateUrzad.getMiejscowosc());
                    return urzedyRepository.save(urzad);
                });
    }
    public Boolean removeUrzadById(Long id ){
        Optional<Urzedy> urzad = urzedyRepository.findById(id);
        if(urzad.isPresent()){
            urzedyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Urzedy> findAll() {
        return urzedyRepository.findAll();
    }

    public void deleteUrzad(int id) {
        urzedyRepository.deleteById(id);
    }
}
