package com.example.demo.Server.Service;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Models.Wnioski;
import com.example.demo.Server.Repository.UzytkownicyRepository;
import com.example.demo.Server.Repository.WnioskiRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class WnioskiService {

    private final WnioskiRepository wnioskiRepository;
    private final UzytkownicyRepository uzytkownicyRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public WnioskiService(WnioskiRepository wnioskiRepository, UzytkownicyRepository uzytkownicyRepository,
                          FileStorageService fileStorageService) {
        this.wnioskiRepository = wnioskiRepository;
        this.uzytkownicyRepository = uzytkownicyRepository;
        this.fileStorageService = fileStorageService;
    }

    public Wnioski addWnioski(Wnioski wniosek) {
        return wnioskiRepository.save(wniosek);
    }

    public Wnioski updateWnioski(Long id, Wnioski wniosek) {
        Optional<Wnioski> optionalWnioski = wnioskiRepository.findById(id);
        if (optionalWnioski.isPresent()) {
            Wnioski existing = optionalWnioski.get();
            existing.setFileUri(wniosek.getFileUri()); // TU BYŁO ŹLE: setUri NIE ISTNIEJE
            existing.setStatus(wniosek.getStatus());
            existing.setKomentarzDoWniosku(wniosek.getKomentarzDoWniosku()); // TU TEŻ BYŁO ZDUPIONE
            return wnioskiRepository.save(existing);
        } else {
            throw new EntityNotFoundException("Wniosek o ID: " + id + " nie został znaleziony");
        }
    }

    public Optional<Wnioski> getWnioskiById(Long id) {
        return wnioskiRepository.findById(id);
    }

    public List<Wnioski> getWnioskiByUzytkownikId(Long id) {
        return wnioskiRepository.findAllByUzytkownik_UzytkownikId(id); // NAZWA METODY W REPO MA ZNACZENIE – Upewnij się, że masz to dobrze
    }

    public List<Wnioski> getWnioskiByUrzadId(Integer urzadId){
        return wnioskiRepository.findAllByUzytkownik_Urzad_UrzadId(Long.valueOf(urzadId));
    }

    @Transactional
    public Wnioski createWniosek(MultipartFile file, Integer uzytkownikId, String komentarz) throws Exception {
        // Walidacja użytkownika
        Uzytkownicy uzytkownik = uzytkownicyRepository.findById(uzytkownikId.longValue())
                .orElseThrow(() -> new Exception("Użytkownik nie znaleziony"));

        // Zapisz plik
        String fileName = fileStorageService.storeFile(file);

        // Utwórz nowy wniosek
        Wnioski wniosek = new Wnioski();
        wniosek.setFileName(fileName);
        wniosek.setFileUri("/Files/" + fileName); // Dostosuj ścieżkę do swojej konfiguracji
        wniosek.setStatus("NOWY");
        wniosek.setKomentarzDoWniosku(komentarz);
        wniosek.setUzytkownik(uzytkownik);
        wniosek.setOriginalFileName(file.getOriginalFilename());

        return wnioskiRepository.save(wniosek);
    }
}
