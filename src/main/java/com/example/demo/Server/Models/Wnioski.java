package com.example.demo.Server.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wnioski")
@Data
public class Wnioski {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int wniosekId;

    @Column
    private String uri;

    @Column
    private String status;

    @Column
    private String komentarzDoWniosku;

    @ManyToOne
    @JoinColumn(name = "uzytkownik")
    private Uzytkownicy uzytkownik;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getkomentarzDoWniosku() {
        return komentarzDoWniosku;
    }

    public void setkomentarzDoWniosku(String komentarzDoWniosku) {
        this.komentarzDoWniosku = komentarzDoWniosku;
    }

    public Uzytkownicy getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownicy uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
}
