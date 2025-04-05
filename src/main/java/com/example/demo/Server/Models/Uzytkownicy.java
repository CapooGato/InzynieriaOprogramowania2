package com.example.demo.Server.Models;

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

    @ManyToOne
    @JoinColumn(name = "urzad")
    private Urzedy urzad;

    @ManyToOne
    @JoinColumn(name = "rola")
    private Role rola;

    @OneToMany(mappedBy = "uzytkownik")
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

    public Role getRola() {
        return rola;
    }

    public void setRola(Role rola) {
        this.rola = rola;
    }
}
