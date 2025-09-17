package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    // Complete

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Transactional
    public ConciertoEntity createConcierto(ConciertoEntity concierto) throws IllegalOperationException {
        log.info("Crear Concierto");
    
        if (concierto.getFechaConcierto().isBefore(LocalDateTime.now())){
            throw new IllegalOperationException("Fecha Invalida");
        }

        if (concierto.getAforoConcierto() < 10){
            throw new IllegalOperationException("Aforo Invalido");
        }

        if (concierto.getPresupuesto() < 10000){
            throw new IllegalOperationException("Presupuesto Invalido");
        }    
    
        return conciertoRepository.save(concierto);

    }   

}
