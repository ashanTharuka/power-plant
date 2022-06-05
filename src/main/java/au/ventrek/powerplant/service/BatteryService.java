package au.ventrek.powerplant.service;

import au.ventrek.powerplant.domain.Battery;
import au.ventrek.powerplant.dto.BatteryDto;
import au.ventrek.powerplant.dto.BatteryResponse;
import au.ventrek.powerplant.exception.DCBException;


public interface BatteryService {
    Battery createBattery(BatteryDto batteryDto) throws DCBException;

    BatteryResponse getBatteriesByPostCodeRange(int postCodeRangeStart, int postCodeRangeEnd) throws DCBException;


}
