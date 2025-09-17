package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    // Complete
    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;


    @Transactional
    public ConciertoEntity addConciertoEstadio(Long conciertoId, Long estadioId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicio Proceso:");

        Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
        if (conciertoEntity.isEmpty())
            throw new EntityNotFoundException("Concierto no encotrado");

        Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);
        if (estadioEntity.isEmpty())
            throw new EntityNotFoundException("Estadio no encotrado");

        if (estadioEntity.get().getAforoEstadio() < conciertoEntity.get().getAforoConcierto()){
            throw new IllegalOperationException("Capacidades invalidas");
            
        }

        if (estadioEntity.get().getPrecioAlquiler() > conciertoEntity.get().getPresupuesto()) throw new IllegalOperationException("Invalido");

        LocalDateTime fecha = conciertoEntity.get().getFechaConcierto();

        for (ConciertoEntity conciertoExistente : estadioEntity.get().getConciertos()) {
            LocalDateTime fechaExistente = conciertoExistente.getFechaConcierto();
            if (Math.abs(java.time.Duration.between(fecha, fechaExistente).toDays()) < 2) {
                throw new IllegalOperationException("Debe haber al menos 2 días entre conciertos en el mismo estadio");           }
        }

        conciertoEntity.get().setEstadio(estadioEntity.get());
        estadioEntity.get().getConciertos().add(conciertoEntity.get());

        return conciertoRepository.save(conciertoEntity.get());      
    }


}
