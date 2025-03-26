package com.example.demo.Server.Service;

import com.example.demo.Server.Models.Role;
import com.example.demo.Server.Models.Urzedy;
import com.example.demo.Server.Repository.UrzedyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrzedyService {
    private final UrzedyRepository repository;

    @Autowired
    public UrzedyService(UrzedyRepository repository) {
        this.repository = repository;
    }
    public List<Urzedy> getAllUrzedy(){
        return repository.findAll();
    }
    public void addUrzad(Urzedy urzad){
        repository.save(urzad);
    }

    public void updateUrzad(Long id, Urzedy updateUrzad){
       repository.findById(id).ifPresent(urzad->{
           urzad.setMiejscowosc(updateUrzad.getMiejscowosc());
           repository.save(urzad);
       });
    }
    public void removeUrzadById(long id ){
        repository.deleteById(id);
    }
}
