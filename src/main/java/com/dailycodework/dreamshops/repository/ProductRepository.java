package com.dailycodework.dreamshops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.model.Product;

//Cuando se usa JpaRepository o CrudRepository Spring gestiona la inyeccion de la dependencia sin usar el @Repository
//Además ya contiene funciones basicas creadas como buscar por id y etc
//Spring crea la bd con las entity, luego con el servicio se llama al repository que como vemos se le pasa con el JpaRepository una entidad y su tipo
//Lo cual llama a la BD

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);

}
