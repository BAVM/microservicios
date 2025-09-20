package com.example.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public UserServiceClient(RestTemplate restTemplate,
                             @Value("${user.service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public boolean userExists(Long userId) {
        try {
            String url = userServiceUrl + "/api/users/" + userId;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            // Usuario no encontrado (404)
            return false;
        } catch (Exception e) {
            // Error de conexión o otro error
            System.err.println("Error al conectar con User Service: " + e.getMessage());
            return false;
        }
    }

    public String getUserEmail(Long userId) {
        try {
            String url = userServiceUrl + "/api/users/" + userId;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // simulamos el email
                return "user" + userId + "@example.com";
            }
            return "unknown@example.com";
        } catch (Exception e) {
            System.err.println("Error al obtener email del usuario: " + e.getMessage());
            return "error@example.com";
        }
    }
}