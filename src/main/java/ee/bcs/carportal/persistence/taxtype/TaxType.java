package ee.bcs.carportal.persistence.taxtype;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tax_type")
public class TaxType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_type_id_gen")
    @SequenceGenerator(name = "tax_type_id_gen", sequenceName = "tax_type_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
    private String code;

    @NotNull
    @Column(name = "base_value", nullable = false, precision = 4, scale = 1)
    private BigDecimal baseValue;

    @NotNull
    @Column(name = "emissions_adjustment", nullable = false, precision = 4, scale = 1)
    private BigDecimal emissionsAdjustment;

    @NotNull
    @Column(name = "model_year_multiplier", nullable = false, precision = 4, scale = 1)
    private BigDecimal modelYearMultiplier;

}