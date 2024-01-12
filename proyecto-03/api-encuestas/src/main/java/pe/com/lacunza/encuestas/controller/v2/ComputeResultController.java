package pe.com.lacunza.encuestas.controller.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.com.lacunza.encuestas.dto.OpcionCount;
import pe.com.lacunza.encuestas.dto.VotoResult;
import pe.com.lacunza.encuestas.model.Voto;
import pe.com.lacunza.encuestas.repository.VotoRepository;

import java.util.HashMap;
import java.util.Map;

@RestController("ComputeResultControllerV2")
@RequestMapping("/v2")
public class ComputeResultController {

    @Autowired
    private VotoRepository votoRepository;

    @GetMapping("/api/calcular/resultado")
    public ResponseEntity<Object> calcularResultado(@RequestParam Long encuestaId) {
        VotoResult votoResult = new VotoResult();

        Iterable<Voto> votos = votoRepository.findByEncuesta(encuestaId);

        // algoritmo para contar votos
        int totalVotos = 0;
        Map<Long, OpcionCount> tempMap = new HashMap<>();

        for(Voto v : votos) {
            totalVotos++;

            // obtenemos la OpcionCount  correspondiente  a esta Opcion
            OpcionCount opcionCount = tempMap.get(v.getOpcion().getId());
            if(opcionCount == null) {
                opcionCount = new OpcionCount();
                opcionCount.setOpcionId(v.getOpcion().getId());
                tempMap.put(v.getOpcion().getId(), opcionCount);
            }
            opcionCount.setCount(opcionCount.getCount() + 1);
        }
        votoResult.setTotalVotos(totalVotos);
        votoResult.setResults(tempMap.values());

        return new ResponseEntity<>(votoResult, HttpStatus.OK);
    }
}