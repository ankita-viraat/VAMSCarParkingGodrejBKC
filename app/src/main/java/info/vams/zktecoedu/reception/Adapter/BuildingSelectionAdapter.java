package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import info.vams.zktecoedu.reception.Model.SelectedBuilding;
import info.vams.zktecoedu.reception.R;


/**
 * Created by vishal on 05-Apr-19.
 */

public class BuildingSelectionAdapter extends ArrayAdapter<SelectedBuilding> {

    private Context mContext;
    private ArrayList<SelectedBuilding> listState;
    private ArrayList<Integer> selectedBildingIds = new ArrayList<>();
    private BuildingSelectionAdapter buildingSelectionAdapter;
    private boolean isFromView = false;
    private int selectedCount = 0;

    public BuildingSelectionAdapter(Context context, int resource, ArrayList<SelectedBuilding> objects) {
        super(context, resource, objects);
        this.mContext = context;
        if (this.listState != null){
            this.listState.clear();
        }
        if (!this.selectedBildingIds.isEmpty()){
            this.selectedBildingIds.clear();
        }
        this.listState = (ArrayList<SelectedBuilding>) objects;
        for (int i = 1; i < this.listState.size(); i++) {
            if (this.listState.get(i).isSelected()){
                selectedCount++;
                selectedBildingIds.add(this.listState.get(i).getBuildingId());
            }
        }
        this.buildingSelectionAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public ArrayList<SelectedBuilding> getSelectedBuildingList(){
        return this.listState;
    }
    public ArrayList<Integer> getSelectedBildingIds(){
        return this.selectedBildingIds;
    }
    public int getSelectedCount(){
        //return this.selectedBuildings.size();
        return this.selectedCount;
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_building_select_item, null);
            holder = new ViewHolder();
            holder.tv_buildingName = (TextView) convertView
                    .findViewById(R.id.tv_buildingName);
            holder.cb_building = (CheckBox) convertView
                    .findViewById(R.id.cb_building);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_buildingName.setText(listState.get(position).getName());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.cb_building.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.cb_building.setVisibility(View.INVISIBLE);
        } else {
            holder.cb_building.setVisibility(View.VISIBLE);
        }
        holder.cb_building.setTag(position);
        holder.cb_building.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);

                    if (isChecked){
                        selectedCount++;
                        selectedBildingIds.add(listState.get(position).getBuildingId());
                    }else {
                        selectedCount--;
                        selectedBildingIds.remove(listState.get(position).getBuildingId());
                    }
                }

            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_buildingName;
        private CheckBox cb_building;
    }
}
