package pe.com.lacunza.api.gestion.facturas.service.impl;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.lacunza.api.gestion.facturas.constantes.FacturaConstantes;
import pe.com.lacunza.api.gestion.facturas.dao.UserDAO;
import pe.com.lacunza.api.gestion.facturas.pojo.User;
import pe.com.lacunza.api.gestion.facturas.security.CustomerDetailsService;
import pe.com.lacunza.api.gestion.facturas.security.jwt.JwtFilter;
import pe.com.lacunza.api.gestion.facturas.security.jwt.JwtUtil;
import pe.com.lacunza.api.gestion.facturas.service.UserService;
import pe.com.lacunza.api.gestion.facturas.util.EmailUtils;
import pe.com.lacunza.api.gestion.facturas.util.FacturaUtils;
import pe.com.lacunza.api.gestion.facturas.wrapper.UserWrapper;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerDetailsService customerDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailUtils emailUtils;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Registro interno de usuario: {}", requestMap);
        try {
            if(validateSignUpMap(requestMap)) {
                User user = userDAO.findByEmail(requestMap.get("email"));
                if(Objects.isNull(user)) {
                    // Codificar la contraseña antes de guardarla en la base de datos
                    String encodedPassword = passwordEncoder.encode(requestMap.get("password")); // usando BCrypt (una vez encriptado ya no se puede volver a desencriptar la contraseña)
                    requestMap.put("password", encodedPassword);

                    userDAO.save(getUserFromMap(requestMap));
                    return FacturaUtils.getResponseEntity("Usuario registrado con exito", HttpStatus.CREATED);
                } else {
                    return FacturaUtils.getResponseEntity("El usuario con ese e-mail ya existe", HttpStatus.BAD_REQUEST);
                }
            } else {
                return FacturaUtils.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Dentro del login ...");
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );

            if(authentication.isAuthenticated()) {
                if(customerDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>(
                            "{\"token\":\"" + jwtUtil.generateToken(customerDetailsService.getUserDetails().getEmail(),
                                    customerDetailsService.getUserDetails().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"Espere la aprobacion del administrador\"}", HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception exception) {
            log.error("Error en el login: {}", exception.getMessage());
        }
        return new ResponseEntity<String>("{\"message\":\"Credenciales incorrectas\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if(jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDAO.getAllUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional<User> optionalUser = userDAO.findById(Integer.parseInt(requestMap.get("id")));
                if(!optionalUser.isEmpty()) {
                    userDAO.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendEmailToAdmins(requestMap.get("status"), optionalUser.get().getEmail(), userDAO.getAllAdmins());
                    return FacturaUtils.getResponseEntity("Status de usuario actualizado", HttpStatus.OK);
                } else {
                    return FacturaUtils.getResponseEntity("Este usuario no existe", HttpStatus.NOT_FOUND);
                }
            } else {
                return FacturaUtils.getResponseEntity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return FacturaUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User user = userDAO.findByEmail(jwtFilter.currentUser());
            if(!user.equals(null)) {
                if(passwordEncoder.matches(requestMap.get("oldPassword"), user.getPassword())) { // usando BCrypt (una vez encriptado ya no se puede volver a desencriptar la contraseña)
                    user.setPassword(passwordEncoder.encode(requestMap.get("newPassword"))); // usando BCrypt (una vez encriptado ya no se puede volver a desencriptar la contraseña)
                    userDAO.save(user);
                    return FacturaUtils.getResponseEntity("Contrasena actualizada con éxito!", HttpStatus.OK);
                }
                return FacturaUtils.getResponseEntity("Contrasena incorrecta \uD83E\uDD14", HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDAO.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
                emailUtils.forgotPasswordSendEmail(user.getEmail(), "Credenciales del sistema gestion de facturas", user.getPassword());
            }
            return FacturaUtils.getResponseEntity("Verifica tus credenciales!!", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendEmailToAdmins(String status, String user, List<String> allAdmins){
        allAdmins.remove(jwtFilter.currentUser());
        if(Objects.nonNull(status) && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtFilter.currentUser(), "Cuenta aprobada", "El usuario " + user + "\nes aprobado \npor Admin: " + jwtFilter.currentUser(), allAdmins);
        } else {
            emailUtils.sendSimpleMessage(jwtFilter.currentUser(), "Cuenta desaprobada/rechazada", "El usuario " + user + "\nes desaprobado/rechazado \npor Admin: " + jwtFilter.currentUser(), allAdmins);
        }
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if(requestMap.containsKey("nombre") && requestMap.containsKey("numeroDeContacto") && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }
    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setNombre(requestMap.get("nombre"));
        user.setNumeroDeContacto(requestMap.get("numeroDeContacto"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
