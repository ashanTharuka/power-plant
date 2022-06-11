package au.ventrek.powerplant.service.impl;

import au.ventrek.powerplant.domain.Battery;
import au.ventrek.powerplant.dto.BatteryDto;
import au.ventrek.powerplant.dto.BatteryResponse;
import au.ventrek.powerplant.exception.DCBError;
import au.ventrek.powerplant.exception.DCBException;
import au.ventrek.powerplant.repository.BatteryRepository;
import au.ventrek.powerplant.service.BatteryService;
import au.ventrek.powerplant.util.Constants;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryServiceImpl implements BatteryService {

    final BatteryRepository batteryRepository;

    public BatteryServiceImpl(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }


    @Override
    public Battery createBattery(BatteryDto batteryDto) throws DCBException {
        Battery battery = new Battery();
        battery.setName(batteryDto.getName());
        battery.setPostcode(Integer.parseInt(batteryDto.getPostcode()));
        battery.setWattCapacity(Integer.parseInt(batteryDto.getCapacity()));

        boolean exists=exists(battery);
        if (exists){
            throw new DCBException(DCBError.CONFLICT);
        }
        return batteryRepository.save(battery);
    }


    @Override
    public BatteryResponse getBatteriesByPostCodeRange(int postCodeRangeStart, int postCodeRangeEnd) throws DCBException {

        List<Battery> batteryList = batteryRepository.getBatteriesByPostCodeRange(postCodeRangeStart, postCodeRangeEnd);
        if(batteryList.isEmpty()){
            throw new DCBException(DCBError.NOT_FOUND);
        }
        int totalWatt = batteryList.stream()
                .mapToInt(Battery::getWattCapacity)
                .sum();
        int averageWatt = totalWatt / batteryList.size();
        BatteryResponse batteryResponse = new BatteryResponse();
        batteryResponse.setBatteryList(batteryList);
        batteryResponse.setAverageWattCapacity(averageWatt);
        batteryResponse.setTotalWattCapacity(totalWatt);
        return batteryResponse;
    }



    @Override
    public boolean exists(Battery battery) {
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withIgnorePaths(Constants.BATTERY_ID.value())
                .withIncludeNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Battery> example = Example.of(battery, matcher);
        return batteryRepository.exists(example);
    }


}
