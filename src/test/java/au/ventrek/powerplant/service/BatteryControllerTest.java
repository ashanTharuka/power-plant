package au.ventrek.powerplant.service;

import au.ventrek.powerplant.domain.Battery;
import au.ventrek.powerplant.dto.BatteryDto;
import au.ventrek.powerplant.dto.BatteryResponse;
import au.ventrek.powerplant.exception.DCBException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BatteryControllerTest {

    @Autowired(required = true)
    public BatteryService batteryService;

    private BatteryDto batteryDto;

    @BeforeAll
    public void initialize() {
        batteryDto = new BatteryDto();
        batteryDto.setName("Northgate Mc");
        batteryDto.setPostcode("9464");
        batteryDto.setCapacity("13500");
    }

    @Test
    @DisplayName("create Battery")
    void createBatteryTest() throws DCBException {
        Battery battery = batteryService.createBattery(batteryDto);
        Assertions.assertEquals("Northgate Mc", battery.getName());
        Assertions.assertEquals("9464", battery.getPostcode());
        Assertions.assertEquals("G13500", battery.getWattCapacity());
    }

    @Test
    @DisplayName("already created")
    void createSameBatteryTest() {
        DCBException exception = assertThrows(
                DCBException.class,
                () -> batteryService.createBattery(batteryDto));
        Assertions.assertEquals("Conflict", exception.getMessage());
    }

    @Test
    @DisplayName("batteries by postcode range")
    void getBatteriesByPostCodeRangeTest() throws DCBException {
        BatteryResponse expected = new BatteryResponse();
        expected.setBatteries(Arrays.asList("Cannington", "Hay Street", "Midland"));
        expected.setAverageWattCapacity(28833);
        expected.setTotalWattCapacity(86500);
        BatteryResponse response = batteryService
                .getBatteriesByPostCodeRange(1000, 7000);
        Assertions.assertEquals(expected, response);
    }

    @Test
    @DisplayName("battery not found")
    void getBatteriesByOutOfPostCodeRangeTest() throws DCBException {
        DCBException expectedEx = new DCBException(HttpStatus.NOT_FOUND);
        DCBException exception = assertThrows(
                DCBException.class,
                () -> batteryService
                        .getBatteriesByPostCodeRange(10000, 50000));
        Assertions.assertEquals(expectedEx, exception);
    }
}
