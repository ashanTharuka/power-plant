package au.ventrek.powerplant.util;

public enum Constants {
    BATTERY_ID ("batteryId");

    private  String value;

    Constants(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
