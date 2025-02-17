package ee.bcs.carportal.persistence.fueltypetax;

import ee.bcs.carportal.persistence.fueltype.FuelType;
import ee.bcs.carportal.persistence.taxtype.TaxType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "fuel_type_tax")
public class FuelTypeTax {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fuel_type_tax_id_gen")
    @SequenceGenerator(name = "fuel_type_tax_id_gen", sequenceName = "fuel_type_tax_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fuel_type_id", nullable = false)
    private FuelType fuelType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tax_type_id", nullable = false)
    private TaxType taxType;

    @NotNull
    @Column(name = "adjustment", nullable = false, precision = 4, scale = 1)
    private BigDecimal adjustment;

}