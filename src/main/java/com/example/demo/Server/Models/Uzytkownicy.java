package com.example.demo.Server.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "uzytkownicy")

public class Uzytkownicy {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int uzytkownikId;

    @Column(unique = true)
    private String login;

    @Column
    private String haslo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "urzad")
    private Urzedy urzad;

    @Column(name = "rola")
    private String rola;

    @OneToMany(mappedBy = "uzytkownik", fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Wnioski> wnioskisByUzytkownikId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public Urzedy getUrzad() {
        return urzad;
    }

    public void setUrzad(Urzedy urzad) {
        this.urzad = urzad;
    }

    public int getUzytkownikId() {
        return uzytkownikId;
    }

    public void setUzytkownikId(int uzytkownikId) {
        this.uzytkownikId = uzytkownikId;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public Collection<Wnioski> getWnioskisByUzytkownikId() {
        return wnioskisByUzytkownikId;
    }

    public void setWnioskisByUzytkownikId(Collection<Wnioski> wnioskisByUzytkownikId) {
        this.wnioskisByUzytkownikId = wnioskisByUzytkownikId;
    }
}
