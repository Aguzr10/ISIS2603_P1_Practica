package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    // Complete

    @Autowired
    private EstadioRepository estadioRepository;


    @Transactional
    public EstadioEntity createEstadio(EstadioEntity estadio) throws IllegalOperationException {
        log.info("Crear Estadio");

        if (estadio.getNombreCiudadEstadio().length() < 3){
            throw new IllegalOperationException("Nombre Inválido");
            
        }
                        
        if (estadio.getAforoEstadio() < 1000 || estadio.getAforoEstadio() > 1000000){
            throw new IllegalOperationException("Capacidad Erronea");
        }

        if (estadio.getPrecioAlquiler() < 10000){
            throw new IllegalOperationException("Precio Invalido");
        }

        return estadioRepository.save(estadio);
    }

}
