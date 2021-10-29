package info.vams.zktecoedu.reception.Model;

import java.io.Serializable;

/**
 * Created by vishal on 05-Apr-19.
 */

public class SelectedBuilding implements Serializable {
    private Integer buildingId;

    private Integer complexId;

    private String name;

    private boolean selected = false;

    public SelectedBuilding(Integer buildingId, Integer complexId, String name) {
        this.buildingId = buildingId;
        this.complexId = complexId;
        this.name = name;
        this.selected = false;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }



}
