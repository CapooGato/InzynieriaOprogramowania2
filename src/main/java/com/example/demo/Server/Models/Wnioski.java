package com.example.demo.Server.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wnioski")
public class Wnioski {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wniosekId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUri;

    @Column(nullable = false)
    private String status = "NOWY";

    @Column
    private String komentarzDoWniosku;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uzytkownik_id", nullable = false)
    private Uzytkownicy uzytkownik;

    // Dodatkowe pole do przechowywania oryginalnej nazwy pliku
    @Column
    private String originalFileName;

    public Integer getWniosekId() {
        return wniosekId;
    }

    public void setWniosekId(Integer wniosekId) {
        this.wniosekId = wniosekId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKomentarzDoWniosku() {
        return komentarzDoWniosku;
    }

    public void setKomentarzDoWniosku(String komentarzDoWniosku) {
        this.komentarzDoWniosku = komentarzDoWniosku;
    }

    public Uzytkownicy getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownicy uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
