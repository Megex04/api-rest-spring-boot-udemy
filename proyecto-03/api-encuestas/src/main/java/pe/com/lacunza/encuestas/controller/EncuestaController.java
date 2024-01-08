package pe.com.lacunza.encuestas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pe.com.lacunza.encuestas.model.Encuesta;
import pe.com.lacunza.encuestas.repository.EncuestaRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @GetMapping("/")
    public ResponseEntity<Iterable<Encuesta>> listarTodasLasEncuestas() {
        return new ResponseEntity<>(encuestaRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<Object> crearEncuesta(@RequestBody Encuesta encuesta) {
        Encuesta encuestaCreada = encuestaRepository.save(encuesta);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newEncuestaUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(encuestaCreada.getId()).toUri();
        httpHeaders.setLocation(newEncuestaUri);

        return new ResponseEntity<>(null,httpHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/{encuestaId}")
    public ResponseEntity<Object> obtenerEncuesta(@PathVariable Long encuestaId) {
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuestaId);
        if(encuesta.isPresent()) {
            return new ResponseEntity<>(encuesta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{encuestaId}")
    public ResponseEntity<Object> actualizarEncuestas(@RequestBody Encuesta encuesta, @PathVariable Long encuestaId) {
        encuesta.setId(encuestaId);
        encuestaRepository.save(encuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarEncuesta(@PathVariable long id) {
        encuestaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}