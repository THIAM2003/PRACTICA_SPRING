package com.dailycodework.dreamshops.model;

import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//LOMBOK es una biblioteca de Java que se utiliza principalmente para reducir el código repetitivo
@Getter //Getter y Setter manejan los atributos de una clase PERO NO a nivel de BD, funcionan de forma que controlan 
@Setter // la accesibilidad de los atributos y también su lógica de creación
@AllArgsConstructor //Crea un constructor con todos los campos, en este caso ID, NOMBRE, etc
@NoArgsConstructor // Crea un constructor sin campos, es decir, "no" toma ID y NOMBRE como "campos" para Image
@Entity //Viene de JPA ya que trabaja con la BD

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob // Large object-JPA
    private Blob image; //Binary large object
    private String downloadUrl;

    @ManyToOne //ESTO VIENE DE JPA YA QUE ES UN ORM
    @JoinColumn(name = "product_id") 
    private Product product;

}
