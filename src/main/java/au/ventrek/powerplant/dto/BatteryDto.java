package au.ventrek.powerplant.dto;

import lombok.Data;

import javax.validation.constraints.*;


@Data
public class BatteryDto {

    @NotBlank(message = "'name' is required")
    private String name;

    @NotBlank(message = "'postcode' is required")
    @Pattern(regexp = "^\\d{4}",message = "'postcode' should be a 4 digit number")
    private String postcode;

    @NotBlank(message = "'capacity' is required")
    @Pattern(regexp = "^[1-9][0-9]*$",message = "'capacity' should be greater than 0")
    private String capacity;
}
