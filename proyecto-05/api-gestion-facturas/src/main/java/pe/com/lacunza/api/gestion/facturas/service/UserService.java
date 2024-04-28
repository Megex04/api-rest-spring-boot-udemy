package pe.com.lacunza.api.gestion.facturas.service;

import org.springframework.http.ResponseEntity;
import pe.com.lacunza.api.gestion.facturas.wrapper.UserWrapper;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<List<UserWrapper>> getAllUsers();
    ResponseEntity<String> updateUser(Map<String, String> requestMap);
    ResponseEntity<String> checkToken();
    ResponseEntity<String> changePassword(Map<String, String> requestMap);
    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}
