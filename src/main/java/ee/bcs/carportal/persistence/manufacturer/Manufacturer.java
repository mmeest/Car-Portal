package ee.bcs.carportal.persistence.manufacturer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "manufacturer")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manufacturer_id_gen")
    @SequenceGenerator(name = "manufacturer_id_gen", sequenceName = "manufacturer_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    // **Vaikimisi konstruktor (nõutud Hibernate'i poolt)**
    public Manufacturer() {
    }

    // **Konstruktor, mida MapStruct kasutab**
    public Manufacturer(Integer id) {
        this.id = id;
    }
}