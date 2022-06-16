package au.ventrek.powerplant.service;

import au.ventrek.powerplant.domain.Battery;
import au.ventrek.powerplant.dto.BatteryDto;
import au.ventrek.powerplant.dto.BatteryRange;
import au.ventrek.powerplant.exception.DCBError;
import au.ventrek.powerplant.exception.EPPException;
import au.ventrek.powerplant.repository.BatteryRepository;
import au.ventrek.powerplant.service.impl.BatteryServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
    void createBatteryTest() throws EPPException {
        when(batteryRepository.save(any())).thenReturn(battery);
        Battery newB = batteryService.createBattery(batteryDto);
        assertEquals(battery, newB);
    }


    @Test
    @DisplayName("battery-already-created")
    void createSameBatteryTest() {
        EPPException expected = new EPPException(DCBError.CONFLICT);
        when(batteryRepository.exists(any())).thenReturn(true);
        EPPException actual = assertThrows(
                EPPException.class,
                () -> batteryService
                        .createBattery(batteryDto));
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("get-battery-by-postcode-range")
    void getBatteriesByPostCodeRangeTest() throws EPPException {

        List<Battery> batteryList=new ArrayList<>();
        batteryList.add(new Battery("Cannington",1001,1000));
        batteryList.add(new Battery("Hay Street",1005,2000));
        batteryList.add(new Battery("Midland",5000,3000));

        BatteryRange expected = new BatteryRange();
        expected.setBatteryList(batteryList);
        expected.assignBatteryNames();
        expected.calculateTotalWatt();
        expected.calculateAvgWattCapacity();

        when(batteryRepository.getBatteriesByPostCodeRange(anyInt(),anyInt())).thenReturn(batteryList);
        BatteryRange actual = batteryService
                .getBatteriesByPostCodeRange(1000, 7000);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("batteries-not-found-by-postcode-range")
    void getBatteriesByOutOfPostCodeRangeTest() {
        EPPException expectedEx = new EPPException(DCBError.NOT_FOUND);
        when(batteryRepository.getBatteriesByPostCodeRange(anyInt(),anyInt())).thenReturn(new ArrayList<>());
        EPPException exception = assertThrows(
                EPPException.class,
                () -> batteryService
                        .getBatteriesByPostCodeRange(10000, 50000));
        Assertions.assertEquals(expectedEx, exception);
    }
}