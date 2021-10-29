package info.vams.zktecoedu.reception.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.vams.zktecoedu.reception.Adapter.AdditionalVisitorListAdapter;
import info.vams.zktecoedu.reception.Model.VisitorList;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.Utilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AdditionalVisitorList extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv_additionalVisitorList;
    RecyclerView.LayoutManager layoutManager;
    CheckBox sheriffCheckbox,adminCheckbox;
    ArrayList<VisitorList> visitorLists;
    Toolbar toolbar_AdditionalVisitorList;
    Button btnCancelDialog,btnYes;
    TextView tvHeader,tvAdmin,tvSheriff;
    ImageView ivBack_arrow,ivSos;
    Context context;
    static ArrayList<VisitorList> visitorListArrayList;
    static AdditionalVisitorListAdapter additionalVisitorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_visitor_list);
        context = AdditionalVisitorList.this;
        toolbar_AdditionalVisitorList = (Toolbar) findViewById(R.id.toolbar_additionalVitirorList);
        ivBack_arrow = (ImageView) findViewById(R.id.additionalVitirorListBackImage);
        ivBack_arrow.setOnClickListener(this);
        rv_additionalVisitorList = (RecyclerView)findViewById(R.id.rv_additionalVisitirList);
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rv_additionalVisitorList.setLayoutManager(layoutManager);



        bindDataToRecyclerView();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void bindDataToRecyclerView() {

        visitorListArrayList = VisitorCheckInActivity.visitorListsArrayList;
        additionalVisitorListAdapter = new AdditionalVisitorListAdapter(context,visitorListArrayList);
        rv_additionalVisitorList.setAdapter(additionalVisitorListAdapter);

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    public static void deleteVisitor(int position){
        visitorListArrayList.remove(position);
        additionalVisitorListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (additionalVisitorListAdapter != null){
            additionalVisitorListAdapter.notifyDataSetChanged();
        }
    }
}
