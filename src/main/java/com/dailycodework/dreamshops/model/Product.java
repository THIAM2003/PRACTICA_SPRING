package com.dailycodework.dreamshops.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

// cascade and orphanremoval sirve para que si se elimina un producto las imagenes asociadas también lo hagan
// No es recomendable usar @Data en entidades ya que trae más funciones implicitas y es "inseguro"
@Getter //Getter y Setter manejan los atributos de una clase PERO NO a nivel de BD, funcionan de forma que controlan 
@Setter // la accesibilidad de los atributos y también su lógica de creación
// @AllArgsConstructor Crea un constructor con todos los campos, en este caso ID, NOMBRE, etc
@NoArgsConstructor // Crea un constructor sin campos, es decir, "no" toma ID y NOMBRE como "campos" para Image
@Entity // Viene de JPA ya que trabaja con la BD

public class Product {

    //Aqui se crea la entidad a "nivel bd"
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Image> images;

    //Este constructor es para cuando se agregue o cree un producto, de forma que no tiene image
    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category){
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }

}
