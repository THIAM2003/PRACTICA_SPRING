package com.dailycodework.dreamshops.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartItemService;
import com.dailycodework.dreamshops.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")

// La anotación @RequestParam se utiliza para extraer parámetros de consulta (query parameters) de una solicitud HTTP o enviados con un formulario.
//Además son datos clave-valor en el cuerpo de una solicitud, es decir, los datos aparecen después de un ? y están separados por & 
//en el URL así:   GET  /api/products?category=electronics&sort=price o así: /products/by/brand-and-name?brand=Nike&name=Sportswear
//El @RequestBody se utiliza para extraer datos del cuerpo de la solicitud HTTP( Ya sean JSON o XML). Se usa para recibir un OBJETO completo o datos estructurados.
//Con PathVariable el parámetro imageId se extrae de la URL de la solicitud y se utiliza para identificar la imagen que el usuario desea descargar.
//Resource es una interfaz de Spring que representa un recurso externo. Puede ser un archivo, una URL, o incluso contenido en memoria
//El tipo de retorno ResponseEntity<ApiResponse> al inicio de la función indica que el método devolverá 
//una respuesta HTTP (ResponseEntity) que contiene un cuerpo de tipo ApiResponse

public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping("/item/agregar")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer quantity){
        try {
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item agregado exitosamente", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    public ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){

    }
}
