package au.ventrek.powerplant.service.impl;

import au.ventrek.powerplant.domain.Battery;
import au.ventrek.powerplant.dto.BatteryDto;
import au.ventrek.powerplant.dto.BatteryResponse;
import au.ventrek.powerplant.exception.DCBException;
import au.ventrek.powerplant.repository.BatteryRepository;
import au.ventrek.powerplant.service.BatteryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatteryServiceImpl implements BatteryService {

    final BatteryRepository batteryRepository;

    public BatteryServiceImpl(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }


    @Override
    public Battery createBattery(BatteryDto batteryDto) throws DCBException {
        Optional<Battery> battery = batteryRepository.findBatteryByName(batteryDto.getName());
        if (battery.isPresent()){
            throw new DCBException(HttpStatus.CONFLICT);
        }
        Battery product = new Battery();
        product.setName(batteryDto.getName());
        product.setPostcode(Integer.parseInt(batteryDto.getPostcode()));
        product.setWattCapacity(Integer.parseInt(batteryDto.getCapacity()));
        return batteryRepository.save(product);
    }


    @Override
    public BatteryResponse getBatteriesByPostCodeRange(int postCodeRangeStart, int postCodeRangeEnd) throws DCBException {

        List<Battery> batteryList = batteryRepository.getBatteriesByPostCodeRange(postCodeRangeStart, postCodeRangeEnd);
        if(batteryList.isEmpty()){
            throw new DCBException(HttpStatus.NOT_FOUND);
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


}
