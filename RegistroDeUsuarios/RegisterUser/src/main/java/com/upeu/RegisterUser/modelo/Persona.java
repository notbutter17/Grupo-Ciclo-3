package com.upeu.RegisterUser.modelo;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name ="personas")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String celular;
    private int edad;


    @Version
    private int version; // Control de versión para concurrencia optimista
}