package com.dailycodework.dreamshops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Esta anotacion llama a EnableAutoConfiguration que llama las @entities
// llama a ComponentScan que llama a todos los @Component con sus estereotipos
// llama a SpringBootConfiguration que es el manejo de bytes

//ORM permite que se mapeen objetos java en una base de datos, permitiendo que interactue la app y la DB a traves
// de objtos y metodos sin necesidad de escribir SQL directamente
//UN ORM ES UNA ESPECIFICACIÓN (COMO JPA) Y COMO SE HACE DICHA ESPECIFICACION(MAPEO ENTRE APP Y BD) 
//ES UNA IMPLEMENTACION COMO HIBERNATE
// @Autowired es para inyeccion de dependencias
public class DreamShopsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamShopsApplication.class, args);
	}

}
