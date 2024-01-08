package pe.com.lacunza.encuestas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pe.com.lacunza.encuestas.model.Voto;
import pe.com.lacunza.encuestas.repository.VotoRepository;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoRepository votoRepository;

    @PostMapping("/encuesta")
    public ResponseEntity<Object> crearVoto(@RequestBody Voto voto) {
        voto = votoRepository.save(voto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(voto.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/{encuestaId}/encuesta")
    public Iterable<Voto> listarTodosLosVotos(@PathVariable Long encuestaId) {
        return votoRepository.findByEncuesta(encuestaId);
    }
}
