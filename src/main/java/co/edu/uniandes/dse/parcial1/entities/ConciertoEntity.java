package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;

    private String nombreArtista;
    private String fechaConcierto;
    private int aforoConcierto;

    @PodamExclude
    @ManyToOne
    private EstadioEntity categoria;
}
