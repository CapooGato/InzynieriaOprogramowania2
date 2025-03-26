package com.example.demo.Server.Models;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "urzedy")
public class Urzedy {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false)
    private int urzad_id;

    private String miejscowosc;

    @OneToMany(mappedBy = "urzad")
    private Collection<Uzytkownicy> uzytkowniciesByUrzadId;

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }
}
