package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import info.vams.zktecoedu.reception.Model.EmployeeList;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.Utils;

/**
 * Created by Nithin on 7/26/2018.
 */

public class PersonToMeetAutoComplete extends ArrayAdapter<EmployeeList> {

    Context context;
    int resource, textViewResourceId;
    ArrayList<EmployeeList> items, tempItems, suggestions;

    public PersonToMeetAutoComplete(@NonNull Context context, int resource, int textViewResourceId, ArrayList<EmployeeList> employeeLists) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = employeeLists;
        tempItems = (ArrayList<EmployeeList>) employeeLists.clone(); // this makes the difference.
        suggestions = new ArrayList<EmployeeList>();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.custom_auto_txt, parent, false);
            }
            EmployeeList details = suggestions.get(position);
            if (details != null) {
                TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
                String personToMeetString = "";

                if (!Utils.isEmpty(details.getFirstName())) {
                    personToMeetString = details.getFirstName();
                }

                if (!Utils.isEmpty(details.getLastName())) {
                    personToMeetString = personToMeetString + " " + details.getLastName();
                }

                if (!Utils.isEmpty(details.getEmailPrimary()) && details.getEmailPrimary() != null) {
                    personToMeetString = personToMeetString + " | " + details.getEmailPrimary();
                }

                if (!Utils.isEmpty(details.getDepartmentName()) && details.getDepartmentName() != null) {
                    personToMeetString = personToMeetString + " | " + details.getDepartmentName();
                }

                lblName.setText(personToMeetString);


            }

            view.setTag(details);

        } catch (Exception e) {
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
            String str = ((EmployeeList) resultValue).getFirstName() + " " + ((EmployeeList) resultValue).getLastName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (EmployeeList people : tempItems) {
                    String temp = people.getFirstName() + " " + people.getLastName();
                    if (temp.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        if (suggestions.size() <= 9) {
                            suggestions.add(people);
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
            List<EmployeeList> filterList = (ArrayList<EmployeeList>) results.values;
            try {
                if (results != null && results.count > 0) {
                    clear();
                    for (EmployeeList people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
