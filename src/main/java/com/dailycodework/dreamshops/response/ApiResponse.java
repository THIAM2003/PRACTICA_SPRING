package com.dailycodework.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor //Crea un constructor con todos los campos, en este caso message, data, etc
@Data
public class ApiResponse {
    private String message;
    private Object data;

}
