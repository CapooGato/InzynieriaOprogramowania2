package com.example.demo.Server.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "role")
@Data
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false)
    private int rola_id;

    @Column
    private String nazwa_roli;

    @OneToMany(mappedBy = "rola")
    private Collection<Uzytkownicy> uzytkowniciesByRolaId;
}
