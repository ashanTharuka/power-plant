package au.ventrek.powerplant.api;

import au.ventrek.powerplant.domain.Battery;
import au.ventrek.powerplant.dto.BatteryDto;
import au.ventrek.powerplant.dto.BatteryResponse;
import au.ventrek.powerplant.dto.PowerPlantResponse;
import au.ventrek.powerplant.exception.EPPException;
import au.ventrek.powerplant.service.BatteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/v1/batteries")
public class BatteryController {

    private final BatteryService batteryService;

    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PowerPlantResponse> createBatteries(@Valid @RequestBody BatteryDto batteryDto)
            throws EPPException {
        Battery battery = batteryService.createBattery(batteryDto);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new PowerPlantResponse(HttpStatus.CREATED, battery));
    }

    @GetMapping(value = "/get-by-postcode-range/{postcodeRangeStart}/{postcodeRangeEnd}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PowerPlantResponse> getBatteriesByPostCodeRange
            (@PathVariable("postcodeRangeStart") int postcodeRangeStart,
             @PathVariable("postcodeRangeEnd") int postcodeRangeEnd) throws EPPException {
        BatteryResponse batteryList = batteryService.getBatteriesByPostCodeRange(postcodeRangeStart, postcodeRangeEnd);
        return ResponseEntity.status(HttpStatus.OK).
                body(new PowerPlantResponse(HttpStatus.OK, batteryList));
    }
}
