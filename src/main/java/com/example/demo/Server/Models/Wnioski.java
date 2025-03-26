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
    @Column(nullable = false)
    private int wniosek_id;

    @Column
    private String uri;

    @Column
    private String status;

    @Column
    private String komentarz_do_wniosku;

    @ManyToOne
    @JoinColumn(name = "uzytkownik", nullable = false)
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

    public String getKomentarz_do_wniosku() {
        return komentarz_do_wniosku;
    }

    public void setKomentarz_do_wniosku(String komentarz_do_wniosku) {
        this.komentarz_do_wniosku = komentarz_do_wniosku;
    }

    public Uzytkownicy getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownicy uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
}
