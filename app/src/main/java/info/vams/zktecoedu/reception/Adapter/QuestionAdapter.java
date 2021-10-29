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
import info.vams.zktecoedu.reception.Model.Question;
import info.vams.zktecoedu.reception.Model.VisitorQuestion;
import info.vams.zktecoedu.reception.R;

/**
 * Created by RahulK on 7/9/2018.
 */

public class QuestionAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Question> questionArrayList;
    ArrayList<VisitorQuestion> visitorQuestionArrayList = new ArrayList<VisitorQuestion>();
    //TextView et_answer;


    public QuestionAdapter(Context context, ArrayList<Question> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, final int position) {
        final MyViewHolder holder = (MyViewHolder) mainHolder;
        final Question question = questionArrayList.get(position);
        holder.tv_question.setText(question.getDescription());
        final VisitorQuestion visitorQuestion = new VisitorQuestion();

        visitorQuestion.setDescription(question.getDescription());
        visitorQuestion.setVisitorQuestionId(Long.valueOf(question.getQuestionId()));
        visitorQuestion.setRequired(question.getRequired());
        if (question.getRequired()) {
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
                //questionArrayList.get(position).set(s.toString());
                visitorQuestion.setAnswer(s.toString());

            }
        });
        //ErrorEdit((EditText)holder.et_answer,visitorQuestionArrayList);
        visitorQuestionArrayList.add(visitorQuestion);
    }

    /*@Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyViewHolder holder, int position) {
        Question question = questionArrayList.get(position);
        holder.tv_question.setText(question.getDescription());
               *//* VisitorQuestion visitorQuestion=new VisitorQuestion();
                visitorQuestion.setAnswer(holder.et_answer.getText().toString());
                VisitorEntryActivityTwo.setAnswerValue(visitorQuestion);*//*
        ArrayList<VisitorQuestion> visitorQuestionArrayList = new ArrayList<VisitorQuestion>();
        VisitorQuestion visitorQuestion = new VisitorQuestion();
        visitorQuestion.setAnswer(holder.et_answer.getText().toString());
        VisitorEntryActivityTwo.getAnswerList(visitorQuestionArrayList);

    }*/

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView et_answer,tv_question, tvStar;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);
            et_answer = (TextView) itemView.findViewById(R.id.et_answer);
            tvStar = (TextView) itemView.findViewById(R.id.tvQuestionStar);

        }
    }

    public ArrayList getFilledQuestions() {

        return this.visitorQuestionArrayList;
    }

  /*  public void ErrorEdit(EditText editText,ArrayList<VisitorQuestion> visitorQuestions) {


        for (VisitorQuestion visitorQuestion : visitorQuestions) {
            if (visitorQuestion.isRequired() && visitorQuestion.getAnswer() == null) {

                editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_error));

            } else if (visitorQuestion.isRequired() && visitorQuestion.getAnswer() != null && visitorQuestion.getAnswer().isEmpty()) {

                editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_error));

            } else {
                editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_focude_effect));
            }
        }
    }*/


}
