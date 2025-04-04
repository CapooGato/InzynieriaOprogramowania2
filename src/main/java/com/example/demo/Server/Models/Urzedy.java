package com.example.demo.Server.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "urzedy")
public class Urzedy {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false, unique = true)
    private int urzadId;

    private String miejscowosc;

    @OneToMany(mappedBy = "urzad", orphanRemoval = true)
    @JsonIgnore
    private Collection<Uzytkownicy> uzytkowniciesByUrzadId;

    public Urzedy(){}

    public Urzedy(int urzadId, String miejscowosc, Collection<Uzytkownicy> uzytkowniciesByUrzadId) {
        this.urzadId = urzadId;
        this.miejscowosc = miejscowosc;
        this.uzytkowniciesByUrzadId = uzytkowniciesByUrzadId;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public int getUrzadId() {
        return urzadId;
    }

    public void setUrzadId(int urzadId) {
        this.urzadId = urzadId;
    }

    public Collection<Uzytkownicy> getUzytkowniciesByUrzadId() {
        return uzytkowniciesByUrzadId;
    }

    public void setUzytkowniciesByUrzadId(Collection<Uzytkownicy> uzytkowniciesByUrzadId) {
        this.uzytkowniciesByUrzadId = uzytkowniciesByUrzadId;
    }
}
