package com.example.demo;

import com.example.demo.Server.Models.Urzedy;
import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Models.Wnioski;
import com.example.demo.Server.Repository.UrzedyRepository;
import com.example.demo.Server.Repository.UzytkownicyRepository;
import com.example.demo.Server.Repository.WnioskiRepository;
import com.example.demo.Server.Service.FileStorageService;
import com.example.demo.Server.Service.UrzedyService;
import com.example.demo.Server.Service.UzytkownicyService;
import com.example.demo.Server.Service.WnioskiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestyJednostkowe {

    @Mock
    private WnioskiRepository wnioskiRepository;

    @Mock
    private UzytkownicyRepository uzytkownicyRepository;

    @Mock
    private UrzedyRepository urzedyRepository;

    @InjectMocks
    private WnioskiService wnioskiService;

    @InjectMocks
    private UzytkownicyService uzytkownicyService;

    @InjectMocks
    private UrzedyService urzedyService;

    @TempDir
    Path tempDir;

    private FileStorageService fileStorageService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        fileStorageService = new FileStorageService(tempDir.toString());
        wnioskiService = new WnioskiService(wnioskiRepository, uzytkownicyRepository, fileStorageService);
    }

    // ----- WnioskiService Tests -----

    @Test
    void addWnioski_shouldSaveAndReturnWniosek() {
        Wnioski wniosek = new Wnioski();
        when(wnioskiRepository.save(wniosek)).thenReturn(wniosek);

        Wnioski result = wnioskiService.addWnioski(wniosek);
        assertEquals(wniosek, result);
        verify(wnioskiRepository).save(wniosek);
    }

    @Test
    void updateWnioski_shouldUpdateExistingWniosek() {
        Wnioski wniosek = new Wnioski();
        wniosek.setFileUri("uri");
        wniosek.setStatus("NOWY");
        wniosek.setKomentarzDoWniosku("komentarz");

        Wnioski existing = new Wnioski();
        when(wnioskiRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(wnioskiRepository.save(existing)).thenReturn(existing);

        Wnioski result = wnioskiService.updateWnioski(1L, wniosek);

        assertEquals("uri", result.getFileUri());
        assertEquals("NOWY", result.getStatus());
        assertEquals("komentarz", result.getKomentarzDoWniosku());
    }

    @Test
    void updateWnioski_shouldThrowIfNotFound() {
        when(wnioskiRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> wnioskiService.updateWnioski(1L, new Wnioski()));
    }

    @Test
    void createWniosek_shouldStoreFileAndSaveWniosek() throws Exception {
        MultipartFile file = new MockMultipartFile("plik", "test.pdf", "application/pdf", "abc".getBytes());
        Uzytkownicy u = new Uzytkownicy();
        when(uzytkownicyRepository.findById(1L)).thenReturn(Optional.of(u));

        Wnioski saved = new Wnioski();
        when(wnioskiRepository.save(any(Wnioski.class))).thenReturn(saved);

        Wnioski result = wnioskiService.createWniosek(file, 1, "komentarz");

        assertEquals(saved, result);
        verify(wnioskiRepository).save(any(Wnioski.class));
    }

    @Test
    void createWniosek_shouldThrowIfUserNotFound() {
        MultipartFile file = new MockMultipartFile("plik", "test.pdf", "application/pdf", "abc".getBytes());
        when(uzytkownicyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wnioskiService.createWniosek(file, 1, "komentarz"));
    }

    // ----- FileStorageService Tests -----

    @Test
    void storeFile_shouldStorePdfSuccessfully() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile(
                "file", "plik.pdf", "application/pdf", "zawartosc".getBytes()
        );

        String storedFileName = fileStorageService.storeFile(multipartFile);
        Path storedFilePath = tempDir.resolve(storedFileName);
        assertTrue(Files.exists(storedFilePath));
    }

    @Test
    void storeFile_shouldThrowExceptionForNonPdf() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file", "plik.txt", "text/plain", "zawartosc".getBytes()
        );

        Exception exception = assertThrows(Exception.class, () -> {
            fileStorageService.storeFile(multipartFile);
        });

        assertEquals("Dozwolone są tylko pliki PDF", exception.getMessage());
    }

    // ----- UrzedyService Tests -----

    @Test
    void addUrzad_shouldSaveAndReturnUrzad() {
        Urzedy urzad = new Urzedy();
        when(urzedyRepository.save(urzad)).thenReturn(urzad);

        Urzedy result = urzedyService.addUrzad(urzad);
        assertEquals(urzad, result);
        verify(urzedyRepository).save(urzad);
    }

    @Test
    void updateUrzad_shouldUpdateAndReturnUrzad() {
        Urzedy urzad = new Urzedy();
        urzad.setMiejscowosc("Test");
        Urzedy existing = new Urzedy();
        when(urzedyRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(urzedyRepository.save(existing)).thenReturn(existing);

        Urzedy result = urzedyService.updateUrzad(1L, urzad).get();

        assertEquals("Test", result.getMiejscowosc());
        verify(urzedyRepository).save(existing);
    }

    @Test
    void updateUrzad_shouldThrowIfNotFound() {
        when(urzedyRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> urzedyService.updateUrzad(1L, new Urzedy()));

        assertEquals("Urzad not found", exception.getMessage());
    }


    @Test
    void removeUrzadById_shouldReturnTrueIfFound() {
        Long id = 1L;
        Urzedy urzad = new Urzedy();
        when(urzedyRepository.findById(id)).thenReturn(Optional.of(urzad)); // Mockowanie zwrócenia Urzadu

        Boolean result = urzedyService.removeUrzadById(id);

        verify(urzedyRepository).deleteById(id);

        assertTrue(result);
    }


    @Test
    void removeUrzadById_shouldReturnFalseIfNotFound() {
        when(urzedyRepository.findById(1L)).thenReturn(Optional.empty());

        Boolean result = urzedyService.removeUrzadById(1L);
        assertFalse(result);
        verify(urzedyRepository, never()).deleteById(anyLong());
    }

    // ----- UzytkownicyService Tests -----

    @Test
    void addUzytkownik_shouldSaveAndReturnUzytkownik() {
        Uzytkownicy uzytkownik = new Uzytkownicy();
        when(uzytkownicyRepository.save(uzytkownik)).thenReturn(uzytkownik);

        Uzytkownicy result = uzytkownicyService.addUzytkownicy(uzytkownik);
        assertEquals(uzytkownik, result);
        verify(uzytkownicyRepository).save(uzytkownik);
    }

    @Test
    void updateUzytkownik_shouldUpdateAndReturnUzytkownik() {
        Uzytkownicy uzytkownik = new Uzytkownicy();
        uzytkownik.setLogin("newLogin");
        Uzytkownicy existing = new Uzytkownicy();
        when(uzytkownicyRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(uzytkownicyRepository.save(existing)).thenReturn(existing);

        Uzytkownicy result = uzytkownicyService.updateUzytkownicy(1L, uzytkownik);

        assertEquals("newLogin", result.getLogin());
        verify(uzytkownicyRepository).save(existing);
    }

    @Test
    void updateUzytkownik_shouldThrowIfNotFound() {
        when(uzytkownicyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> uzytkownicyService.updateUzytkownicy(1L, new Uzytkownicy()));
    }
}
