package com.example.demo.Server.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "platnosci")
public class Platnosci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal kwota;
    private LocalDate termin;
    private String status; // np. "OCZEKUJACA", "OPLACONA"

    @ManyToOne()
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownicy uzytkownik;

    @PrePersist
    public void setDefaultStatus() {
        if (status == null) {
            status = "OCZEKUJACA";
        }
    }

    public Platnosci() {
    }

    public Platnosci(Long id, BigDecimal kwota, LocalDate termin, String status, Uzytkownicy uzytkownik) {
        this.id = id;
        this.kwota = kwota;
        this.termin = termin;
        this.status = status;
        this.uzytkownik = uzytkownik;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public LocalDate getTermin() {
        return termin;
    }

    public void setTermin(LocalDate termin) {
        this.termin = termin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Uzytkownicy getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownicy uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
}