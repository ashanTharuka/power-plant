package au.ventrek.powerplant.dto;

import au.ventrek.powerplant.domain.Battery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BatteryRange {

    private List<String> batteriesNames;

    @JsonIgnore
    private List<Battery> batteryList;

    private int averageWattCapacity;

    private int totalWattCapacity;

    public void assignBatteryNames() {
        this.batteriesNames = this.batteryList.stream()
                .map(Battery::getName)
                .collect(Collectors.toList());

    }

    public void calculateTotalWatt() {
        this.totalWattCapacity = this.batteryList.stream()
                .mapToInt(Battery::getWattCapacity)
                .sum();
    }

    public void calculateAvgWattCapacity() {
        this.averageWattCapacity = totalWattCapacity / batteryList.size();
    }

}
