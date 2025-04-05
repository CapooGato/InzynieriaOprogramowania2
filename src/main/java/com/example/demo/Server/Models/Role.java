package com.example.demo.Server.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "role")
@Data
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int rolaId;

    @Column
    private String nazwaRoli;

    @OneToMany(mappedBy = "rola")
    @JsonIgnore
    private Collection<Uzytkownicy> uzytkowniciesByRolaId;

    Role(){}

    public Role(int rolaId, String nazwaRoli, Collection<Uzytkownicy> uzytkowniciesByRolaId) {
        this.rolaId = rolaId;
        this.nazwaRoli = nazwaRoli;
        this.uzytkowniciesByRolaId = uzytkowniciesByRolaId;
    }

    public int getRolaId() {
        return rolaId;
    }

    public void setRolaId(int rolaId) {
        this.rolaId = rolaId;
    }

    public String getNazwaRoli() {
        return nazwaRoli;
    }

    public void setNazwaRoli(String nazwaRoli) {
        this.nazwaRoli = nazwaRoli;
    }

    public Collection<Uzytkownicy> getUzytkowniciesByRolaId() {
        return uzytkowniciesByRolaId;
    }

    public void setUzytkowniciesByRolaId(Collection<Uzytkownicy> uzytkowniciesByRolaId) {
        this.uzytkowniciesByRolaId = uzytkowniciesByRolaId;
    }
}
