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
import info.vams.zktecoedu.reception.Model.StudentList;
import info.vams.zktecoedu.reception.R;


public class StudentAutoCompleteAdapter extends ArrayAdapter<StudentList> {

    Context context;
    int resource, textViewResourceId;
    ArrayList<StudentList> items, tempItems, suggestions;

    public StudentAutoCompleteAdapter(@NonNull Context context, int resource, int textViewResourceId, ArrayList<StudentList> studentLists) {
        super(context, resource, textViewResourceId);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = studentLists;
        tempItems = (ArrayList<StudentList>) studentLists.clone(); // this makes the difference.
        suggestions = new ArrayList<StudentList>();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_auto_txt, parent, false);
        }
        StudentList details = suggestions.get(position);
        if (details != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);

            if (details.getFirstName() == null) {
                details.setFirstName("");
            }
            if (details.getLastName() == null) {
                details.setLastName("");
            }
            if (details.getClassName() == null) {
                details.setClassName("");
            }

            if (lblName != null) {
                lblName.setText(details.getFirstName() + " " + details.getLastName() + " | " + details.getClassName() + " | " + details.getEnrollmentNo());
            }
        }

        view.setTag(details);
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
            String str = ((StudentList) resultValue).getFirstName() + " " + ((StudentList) resultValue).getLastName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();

                for (StudentList people : tempItems) {
                    String temp = people.getFirstName() + " " + people.getLastName();
                    if (temp.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        if(suggestions.size() <= 9){
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
            List<StudentList> filterList = (ArrayList<StudentList>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (StudentList people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };

}
