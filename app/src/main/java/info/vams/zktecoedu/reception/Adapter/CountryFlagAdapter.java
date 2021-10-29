package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import info.vams.zktecoedu.reception.Model.CountryForISD;
import info.vams.zktecoedu.reception.R;

public class CountryFlagAdapter extends ArrayAdapter<CountryForISD> {
    Context context;
    int resource, textViewResourceId;
    ArrayList<CountryForISD> items, tempItems, suggestions;

    public CountryFlagAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<CountryForISD> objects) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = objects;
        tempItems = (ArrayList<CountryForISD>) objects.clone();// this makes the difference.
        suggestions = new ArrayList<CountryForISD>();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.country_code, parent, false);
            }
            CountryForISD country = suggestions.get(position);
            if (country != null) {
                TextView tvName = (TextView) view.findViewById(R.id.country_name_tv);
                String name = country.getDialCode();
                tvName.setText(name);
                TextView tvCode = (TextView) view.findViewById(R.id.code_tv);
                tvCode.setText(country.getCode());
                ImageView imvFlag = (ImageView) view.findViewById(R.id.flag_imv);
                if (country.getFlag() != -1){
                    imvFlag.setImageResource(country.getFlag());
                }
            }

            view.setTag(country);

        }catch (Exception e){
            e.printStackTrace();
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
            String str = ((CountryForISD) resultValue).getDialCode();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (CountryForISD country : tempItems) {
                    String temp = country.getDialCode();
                    if (country.getDialCode().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        if(suggestions.size() <= 9){
                            suggestions.add(country);
                        }
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
            List<CountryForISD> filterList = (ArrayList<CountryForISD>) results.values;
            try {
                if (results != null && results.count > 0) {
                    clear();
                    for (CountryForISD country : filterList) {
                        add(country);
                        notifyDataSetChanged();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
