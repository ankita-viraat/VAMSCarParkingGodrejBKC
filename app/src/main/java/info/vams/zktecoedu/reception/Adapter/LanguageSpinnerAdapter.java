package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import info.vams.zktecoedu.reception.R;

/**
 * Created by Nithin on 6/7/2018.
 */

public class LanguageSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String>languages;
    public Resources res;
    LayoutInflater inflater;

    public LanguageSpinnerAdapter(Context context, ArrayList<String> languages) {
        super(context, R.layout.language_spinner_row, languages);
        context=context;
        languages=languages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.language_spinner_row, parent, false);

        TextView tvCategory = (TextView) row.findViewById(R.id.tvCategory);

        tvCategory.setText(languages.get(position).toString());

        return row;
    }
}
