package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import info.vams.zktecoedu.reception.Model.VisitorQuestion;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.Utilities;

/**
 * Created by RahulK on 7/13/2018.
 */

public class VisitorQuestionAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<VisitorQuestion> questionArrayList;


    public VisitorQuestionAdapter(Context context, ArrayList<VisitorQuestion> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, final int position) {

        MyViewHolder holder = (MyViewHolder) mainHolder;

        VisitorQuestion question = questionArrayList.get(position);
        holder.tv_question.setText(question.getDescription());
        holder.et_answer.setText(question.getAnswer());

        if (question.isRequired()) {
            holder.tvStar.setVisibility(View.VISIBLE);
        } else {
            holder.tvStar.setVisibility(View.GONE);
        }

        holder.et_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                questionArrayList.get(position).setAnswer(s.toString());
            }
        });
        /*Utilities.disableInput((EditText) holder.et_answer);*/
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_question, et_answer, tvStar;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);
            et_answer = (TextView) itemView.findViewById(R.id.et_answer);
            tvStar = (TextView) itemView.findViewById(R.id.tvQuestionStar);


        }
    }

    public ArrayList getFilledQuestions() {
        return this.questionArrayList;
    }

}
