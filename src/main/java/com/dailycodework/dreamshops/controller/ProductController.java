package com.dailycodework.dreamshops.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // Indica que la clase anotada es un controlador, manejando solicitudes HTTP.
// Tambien Devuelve datos directamente al cliente(JSON o XML) en lugar de renderizar vistas. Combina implícitamente la anotación @Controller con @ResponseBody.
// Aplica REST basicamente (No existe un estado para la solicitud ya que todo esta en el Json)
@RequestMapping("${api.prefix}/productos")

// La anotación @RequestParam se utiliza para extraer parámetros de consulta (query parameters) de una solicitud HTTP o enviados con un formulario.
//Además son datos clave-valor en el cuerpo de una solicitud, es decir, los datos aparecen después de un ? y están separados por & 
//en el URL así:   GET  /api/products?category=electronics&sort=price
//(Lo use para guardar, es decir, para un POST) el cual tenia un Multipart(en solicitudes multipart/form-data, el cuerpo contiene partes
// mezcladas (archivos binarios y texto plano)).
//El @RequestBody se utiliza para extraer datos del cuerpo de la solicitud HTTP( Ya sean JSON o XML). Se usa para recibir un OBJETO completo o datos estructurados.
//Con PathVariable el parámetro imageId se extrae de la URL de la solicitud y se utiliza para identificar la imagen que el usuario desea descargar.
//Resource es una interfaz de Spring que representa un recurso externo. Puede ser un archivo, una URL, o incluso contenido en memoria
//El tipo de retorno ResponseEntity<ApiResponse> al inicio de la función indica que el método devolverá 
//una respuesta HTTP (ResponseEntity) que contiene un cuerpo de tipo ApiResponse

public class ProductController {

    private final IProductService productService;

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Productos:", products));
    }
    
    @GetMapping("/producto/{productId}/producto")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Producto encontrado", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            
        } catch (Exception e) {
           
        }
    }

    @GetMapping("/producto/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{category}/productos")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{brand}/productos")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{category}/{brand}/productos")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String category, @PathVariable String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{category}/{name}/productos")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@PathVariable String category, @PathVariable String name){
        try {
            List<Product> products = productService.getProductsByBrandAndName(category, name);
            return ResponseEntity.ok(new ApiResponse("Productos encontrados", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
