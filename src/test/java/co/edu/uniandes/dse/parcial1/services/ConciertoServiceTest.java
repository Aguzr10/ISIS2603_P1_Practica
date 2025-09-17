package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.persistence.EntityManager;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ConciertoService.class)
class ConciertoServiceTest {

    @Autowired
    private ConciertoService conciertoService;

    @Autowired
    private EntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private List<ConciertoEntity> conciertoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.createQuery("delete from ConciertoEntity").executeUpdate();
    }

    private void insertData(){
        for (int i = 0; i < 3; i++) {
            ConciertoEntity concierto = factory.manufacturePojo(ConciertoEntity.class);
            concierto.setEstadio(null);
            entityManager.persist(concierto);
    } 
}

    @Test
    void testCrearConcierto() throws IllegalOperationException{
        ConciertoEntity nuevoConcierto = factory.manufacturePojo(ConciertoEntity.class);
        
        nuevoConcierto.setFechaConcierto(LocalDateTime.now().plusDays(1));
        nuevoConcierto.setAforoConcierto(10);
        nuevoConcierto.setPresupuesto((long) 10000);
        
        ConciertoEntity resultado = conciertoService.createConcierto(nuevoConcierto);
        assertNotNull(resultado);

        
        assertEquals(resultado, nuevoConcierto);
        
    }

    @Test
    void testIncorrecto() throws IllegalOperationException{
        assertThrows(IllegalOperationException.class,()->{
        
        ConciertoEntity nuevoConcierto = factory.manufacturePojo(ConciertoEntity.class);
        
        nuevoConcierto.setFechaConcierto(LocalDateTime.now().plusDays(1));
        nuevoConcierto.setAforoConcierto(10);
        nuevoConcierto.setPresupuesto((long) 1);
        
        ConciertoEntity resultado = conciertoService.createConcierto(nuevoConcierto);
        

        
    
        });
        
        
    }


}
