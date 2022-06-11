package au.ventrek.powerplant.service;

import au.ventrek.powerplant.domain.Battery;
import au.ventrek.powerplant.dto.BatteryDto;
import au.ventrek.powerplant.dto.BatteryResponse;
import au.ventrek.powerplant.exception.DCBError;
import au.ventrek.powerplant.exception.DCBException;
import au.ventrek.powerplant.repository.BatteryRepository;
import au.ventrek.powerplant.service.impl.BatteryServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BatteryServiceTest {

    @Mock
    BatteryRepository batteryRepository;

    @InjectMocks
    BatteryServiceImpl batteryService;

    private BatteryDto batteryDto;
    Battery battery;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        batteryDto = new BatteryDto();
        batteryDto.setName("Northgate Mc");
        batteryDto.setPostcode("9464");
        batteryDto.setCapacity("13500");

        battery = new Battery();
        battery.setBatteryId(Long.valueOf(1));
        battery.setName("Northgate Mc");
        battery.setPostcode(9464);
        battery.setWattCapacity(13500);
    }


    @Test
    @DisplayName("create-battery")
    void createBatteryTest() throws DCBException {
        when(batteryRepository.save(any())).thenReturn(battery);
        Battery newB = batteryService.createBattery(batteryDto);
        assertEquals(battery, newB);
    }


    @Test
    @DisplayName("battery-already-created")
    void createSameBatteryTest() {
        DCBException expected = new DCBException(DCBError.CONFLICT);
        when(batteryRepository.exists(any())).thenReturn(true);
        DCBException actual = assertThrows(
                DCBException.class,
                () -> batteryService
                        .createBattery(batteryDto));
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("get-battery-by-postcode-range")
    void getBatteriesByPostCodeRangeTest() throws DCBException {

        List<Battery> batteryList=new ArrayList<>();
        batteryList.add(new Battery("Cannington",1001,1000));
        batteryList.add(new Battery("Hay Street",1005,2000));
        batteryList.add(new Battery("Midland",5000,3000));

        BatteryResponse expected = new BatteryResponse();
        expected.setBatteries(Arrays.asList("Cannington", "Hay Street", "Midland"));
        expected.setAverageWattCapacity(2000);
        expected.setTotalWattCapacity(6000);

        when(batteryRepository.getBatteriesByPostCodeRange(anyInt(),anyInt())).thenReturn(batteryList);
        BatteryResponse actual = batteryService
                .getBatteriesByPostCodeRange(1000, 7000);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("batteries-not-found-by-postcode-range")
    void getBatteriesByOutOfPostCodeRangeTest() {
        DCBException expectedEx = new DCBException(DCBError.NOT_FOUND);
        when(batteryRepository.getBatteriesByPostCodeRange(anyInt(),anyInt())).thenReturn(new ArrayList<>());
        DCBException exception = assertThrows(
                DCBException.class,
                () -> batteryService
                        .getBatteriesByPostCodeRange(10000, 50000));
        Assertions.assertEquals(expectedEx, exception);
    }
}