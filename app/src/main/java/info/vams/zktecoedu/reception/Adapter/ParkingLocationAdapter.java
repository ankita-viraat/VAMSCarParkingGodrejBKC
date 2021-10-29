package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import info.vams.zktecoedu.reception.Model.ParkingLocationModel;
import info.vams.zktecoedu.reception.R;

/**
 * Created by Nithin on 8/8/2018.
 */

public class ParkingLocationAdapter extends ArrayAdapter<ParkingLocationModel> {

    Context context;
    int resource, textViewResourceId;
    ArrayList<ParkingLocationModel> items, tempItems, suggestions;

    public ParkingLocationAdapter(@NonNull Context context, int resource, int textViewResourceId, ArrayList<ParkingLocationModel> employeeLists) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = employeeLists;
        tempItems = (ArrayList<ParkingLocationModel>) employeeLists.clone(); // this makes the difference.
        suggestions = new ArrayList<ParkingLocationModel>();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_auto_txt, parent, false);
        }

//        System.out.println(suggestions.toArray());

        if(!suggestions.isEmpty()){
            ParkingLocationModel details = null;
            try {
                details = suggestions.get(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (details != null) {
                TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
                if (lblName != null){
                    lblName.setText(details.getName());
                }
            }
            view.setTag(details);
        }
        return view;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ParkingLocationModel) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ParkingLocationModel people : tempItems) {
                    String temp = people.getName();
                    if (temp.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        if(suggestions.size() <= 4){
                            suggestions.add(people);
//                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<ParkingLocationModel> filterList = (ArrayList<ParkingLocationModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ParkingLocationModel people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }

    };

}
