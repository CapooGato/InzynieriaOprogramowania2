package com.example.demo.Server.Controllers.Api;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Models.Wnioski;
import com.example.demo.Server.Repository.UzytkownicyRepository;
import com.example.demo.Server.Service.FileStorageService;
import com.example.demo.Server.Service.WnioskiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/api/wnioski")
public class WnioskiController {

    private final WnioskiService wnioskiService;
    private final FileStorageService fileStorageService;
    private final UzytkownicyRepository uzytkownicyRepository;

    @Autowired
    public WnioskiController(WnioskiService wnioskiService,
                             FileStorageService fileStorageService,
                             UzytkownicyRepository uzytkownicyRepository) {
        this.wnioskiService = wnioskiService;
        this.fileStorageService = fileStorageService;
        this.uzytkownicyRepository = uzytkownicyRepository;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createWniosek(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uzytkownikId") Long uzytkownikId,
            @RequestParam(value = "komentarz", required = false) String komentarz) {

        try {
            Uzytkownicy uzytkownik = uzytkownicyRepository.findById(uzytkownikId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie istnieje"));

            String fileName = fileStorageService.storeFile(file);

            Wnioski wniosek = new Wnioski();
            wniosek.setOriginalFileName(file.getOriginalFilename());
            wniosek.setFileName(fileName);
            wniosek.setFileUri("/Files/" + fileName);
            wniosek.setUzytkownik(uzytkownik);
            wniosek.setKomentarzDoWniosku(komentarz);

            Wnioski savedWniosek = wnioskiService.addWnioski(wniosek);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedWniosek);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Wnioski> getWnioskiById(@PathVariable Long id){
        Wnioski wnioski = wnioskiService.getWnioskiById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono wniosku o ID: " + id));
        return ResponseEntity.status(HttpStatus.OK).body(wnioski);
    }

    @GetMapping(value = "/uzytkownik/{id}")
    public ResponseEntity<List<Wnioski>> getWnioskiByUzytkownikId(@PathVariable Long id){
        List<Wnioski> wnioskiList = wnioskiService.getWnioskiByUzytkownikId(id);
        return wnioskiList.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(wnioskiList);
    }
    @GetMapping(value = "pliki/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Path file = Paths.get("uploads", "wnioski").resolve(filename).normalize();
        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono zasobu");

        }


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Wnioski> updateWnioskiById(@PathVariable Long id, @Valid @RequestBody Wnioski wniosek){
        Wnioski updatedWniosek = wnioskiService.updateWnioski(id, wniosek);
        return ResponseEntity.status(HttpStatus.OK).body(updatedWniosek);
    }

}
