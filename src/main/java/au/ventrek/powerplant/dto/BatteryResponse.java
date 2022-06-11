package au.ventrek.powerplant.dto;

import au.ventrek.powerplant.domain.Battery;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BatteryResponse {
    private List<String> batteries;
    private int averageWattCapacity;
    private int totalWattCapacity;

    public void setBatteryList(List<Battery> batteries) {
        if (!batteries.isEmpty()){
            this.batteries=batteries.stream()
                    .map(Battery::getName)
                    .collect(Collectors.toList());
        }
    }
}
