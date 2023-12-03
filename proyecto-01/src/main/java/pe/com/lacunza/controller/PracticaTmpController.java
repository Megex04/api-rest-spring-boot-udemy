package pe.com.lacunza.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/math")
public class PracticaTmpController {

    @GetMapping(value = "/")
    public String messageWelcome() {
        return "Hola a los perros de mrd, esta API REST realiza operaciones matematicas del infierno de Minecraft";
    }

    @GetMapping(value = "/add/{num1}/{num2}")
    public Integer addition(@PathVariable("num1") int num1, @PathVariable("num2") int num2) {
        return num1 + num2;
    }

    @GetMapping(value = "/subtraction/{num1}")
    public Integer subtraction(@PathVariable("num1") int num1, @RequestParam("num2") int num2) {
        return num1 - num2;
    }

    @GetMapping(value = "/multi")
    public Double multipication(@RequestParam("num1") double num1, @RequestParam("num2") double num2) {
        return num1 * num2;
    }

    @GetMapping(value = "/div/{dividendo}")
    public Double division(@PathVariable("dividendo") int div, @RequestParam("divisor") double divisor) {
        double result = div / divisor;
        if(result == Double.POSITIVE_INFINITY) {
            return null;
        } else {
            return Math.round(result * 100.0) / 100.0;
        }
    }

    @GetMapping(value = "/calculate/{num1}/{num2}")
    public String calculate(@PathVariable("num1") int num1, @PathVariable("num2") int num2) {
        if(num1 > num2) {
            return String.format("El numero %d es mayor a %d", num1 , num2);
        } else if (num1 < num2) {
            return String.format("El numero %d es menor a %d", num1 , num2);
        } else {
            return "Los numeros son iguales !!!";
        }
    }
}
