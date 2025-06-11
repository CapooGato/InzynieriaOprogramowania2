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
    private Integer urzadId;

    private String miejscowosc;

    @OneToMany(mappedBy = "urzad",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Collection<Uzytkownicy> uzytkowniciesByUrzadId;

    public Urzedy(){}

    public Urzedy(Integer urzadId, String miejscowosc, Collection<Uzytkownicy> uzytkowniciesByUrzadId) {
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

    public Integer getUrzadId() {
        return urzadId;
    }

    public void setUrzadId(Integer urzadId) {
        this.urzadId = urzadId;
    }

    public Collection<Uzytkownicy> getUzytkowniciesByUrzadId() {
        return uzytkowniciesByUrzadId;
    }

    public void setUzytkowniciesByUrzadId(Collection<Uzytkownicy> uzytkowniciesByUrzadId) {
        this.uzytkowniciesByUrzadId = uzytkowniciesByUrzadId;
    }
}
