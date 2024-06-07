package worktomeet.travel.spacecraft.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class SpacecraftRequestDTO {
    private String name;
    private String model;
    private LocalDate manufactureDate;
    private BigDecimal weight;

    public SpacecraftRequestDTO(String name,
                                String model,
                                LocalDate manufactureDate,
                                BigDecimal weight) {
        this.name = name;
        this.model = model;
        this.manufactureDate = manufactureDate;
        this.weight = weight;
    }

    public String getModel() {
        return model;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}
