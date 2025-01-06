package com.dailycodework.dreamshops.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // Indica que la clase anotada es un controlador, manejando solicitudes HTTP.
// Tambien Devuelve datos directamente al cliente(JSON o XML) en lugar de renderizar vistas. Combina implícitamente la anotación @Controller con @ResponseBody.
// Aplica REST basicamente (No existe un estado para la solicitud ya que todo esta en el Json)
@RequestMapping("${api.prefix}/images")

// La clase Model se utiliza para transferir objetos del Controller a la vista

public class ImageController {
    
    private final IImageService imageService;

    @PostMapping("/cargar")
    // La anotación @RequestParam se utiliza para extraer parámetros de consulta (query parameters) de una solicitud HTTP o enviados con un formulario.
    //Además son datos clave-valor en el cuerpo de una solicitud, es decir, los datos aparecen después de un ? y están separados por & 
    //en el URL así:   GET  /api/products?category=electronics&sort=price
    //(Lo use para guardar, es decir, para un POST) el cual tenia un Multipart(en solicitudes multipart/form-data, el cuerpo contiene partes
    // mezcladas (archivos binarios y texto plano por lo que RequestBody no sirve)). 
    //El @RequestBody se utiliza para extraer datos del cuerpo de la solicitud HTTP( Ya sean JSON o XML). Se usa para recibir un OBJETO completo o datos estructurados.
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Cargado correctamente", imageDtos));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Cargado correctamente", e.getMessage()));   
        }
    }

    @GetMapping("/image/download/{imageId}")
    //Con PathVariable el parámetro imageId se extrae de la URL de la solicitud y se utiliza para identificar la imagen que el usuario desea descargar.
    //Resource es una interfaz de Spring que representa un recurso externo. Puede ser un archivo, una URL, o incluso contenido en memoria
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException{
        Image image = imageService.getImageById(imageId);
        //El ByteArrayResource es una clase de la biblioteca Spring que se utiliza para representar datos binarios como un recurso en memoria. 
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length())); 
        //Obtiene los bytes desde el indice 1 (el indice comienza en 1) hasta la longitud del objeto y se envuelven en un ByteArrayResource
        //para que se puedan usar como un recurso compatible con Spring.
        //Se devuelve la imagen como respuesta HTTP usando ResponseEntity
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
        // header(... ) Establece un encabezado HTTP para sugerir al cliente cómo manejar el archivo descargado
        // Content-Disposition: Si contiene attachment;, el navegador descargará el archivo en lugar de mostrarlo.
        // El navegador interpretará el encabezado Content-Disposition con el valor attachment.
            .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; nombre del archivo =\"" + image.getFileName() + "\"")
        // body(resource) Establece el cuerpo de la respuesta con los datos binarios de la imagen encapsulados en el ByteArrayResource.
            .body(resource);
    }

    @PutMapping("/image/{imageId}/actualizar")
    //El tipo de retorno ResponseEntity<ApiResponse> al inicio de la función indica que el método devolverá 
    //una respuesta HTTP (ResponseEntity) que contiene un cuerpo de tipo ApiResponse
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Actualización existosa!", null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Actualización faliida!", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/eliminar")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById( imageId);
                return ResponseEntity.ok(new ApiResponse("Eliminado correctamente!", null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Eliminación fallida!", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}