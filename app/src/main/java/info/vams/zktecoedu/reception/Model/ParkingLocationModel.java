package info.vams.zktecoedu.reception.Model;

/**
 * Created by RahulK on 6/29/2018.
 */

public class ParkingLocationModel {
    int parkingLocationId;
    String name;
    int buildingId;

    public int getParkingLocationId() {
        return parkingLocationId;
    }

    public void setParkingLocationId(int parkingLocationId) {
        this.parkingLocationId = parkingLocationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
}
