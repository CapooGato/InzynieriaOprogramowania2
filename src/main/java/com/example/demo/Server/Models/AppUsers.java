package com.example.demo.Server.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AppUsers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("lastName")
    private String lastName;

    @NotNull
    @JsonProperty("role")
    private String role;
}
