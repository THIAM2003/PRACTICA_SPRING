package com.dailycodework.dreamshops.model;

import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

    //Esto genera la relación en la tabla muchos a muchos de user-role con la clave foranea
    // pero no crea nuevos campos en la tabla role
    // mappedBy Se utiliza en la entidad inversa (Aquí) para indicar la propiedad que posee la clave foránea 
    // en la entidad propietaria.
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();
}
