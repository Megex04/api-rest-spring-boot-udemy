package pe.com.lacunza.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HelloController {

    @GetMapping(value = "/hello")
    public String holaMundo() {
        return "Hello mundito de perros putos de mierda!!!";
    }
}
