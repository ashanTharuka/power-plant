package au.ventrek.powerplant.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class BatteryDto {
    @NotEmpty(message = "Battery name is required")
    private String name;

    @NotEmpty(message = "postcode is required")
    private String postcode;

    @NotNull(message = "capacity is required")
    @Size(min = 2,message = "capacity capacity should be more than 2 digits")
    private String capacity;
}
