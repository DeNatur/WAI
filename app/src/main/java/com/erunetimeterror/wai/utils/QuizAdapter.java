package com.erunetimeterror.wai.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.erunetimeterror.wai.R;
import com.erunetimeterror.wai.activities.QuizActivity;

import java.util.List;
import java.util.Map;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> implements View.OnClickListener{

    private List<String> mData;
    private List<String> mKey;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    QuizActivity quizActivity;
    Map<String, Integer> lastSelected; // position_key, selection

    // data is passed into the constructor
    public QuizAdapter(Context context, List<String> key, List<String> data, QuizActivity quizActivity) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mKey = key;
        this.quizActivity = quizActivity;
    }

    public void addItem(String key, String data, int index){
        mKey.add(key);
        mData.add(data);
        notifyItemInserted(index);
    }

    public void removeItem(int index) {
        mKey.remove(index);
        mData.remove(index);
        notifyItemRemoved(index);
    }

    public List<String> getData()
    {
        return mData;
    }

    public List<String> getKey()
    {
        return mKey;
    }

    public void removeAll()
    {
        if (mData.size() > 0) {
            int size = mData.size();
            for (int i = 0; i < size; i++) {
                this.removeItem(mData.size() - 1);
            }
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_quiz_question, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String question = mData.get(position);
        holder.myTextView.setText(question);

        // TUTAJ USTAWIĆ PRZYPISYWANIE PUNKTÓW NA POSDTAWIE POSITION I TEGO JAKIE JEST PYTANIE

        holder.radioGroupAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {

                int radioBtnID = rGroup.getCheckedRadioButtonId();
                View radioB = rGroup.findViewById(radioBtnID);
                int selection = holder.radioGroupAnswer.indexOfChild(radioB);

                String key = mKey.get(position);
                String key_short = key.substring(0, key.length() - 1);

                int point = 0;

                if(key != null && lastSelected.containsKey(key)) {
                    int k = lastSelected.get(key);
                    if (k == 0 || k == 3) point = 2;
                    if (k == 1 || k == 2) point = 1;
                    //quizActivity.getAnswers().removeAnswer(key_short, point);
                }

                lastSelected.put(key, selection); // position_key, selection


                if (selection == 0 || selection == 3) point = 2;
                if (selection == 1 || selection == 2) point = 1;
                quizActivity.getAnswers().setAnswer(key_short, point, selection);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {

    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
       // Button btnVN, btnN, btnY, btnVY;
        RadioGroup radioGroupAnswer;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvSingleQuestion);
            //btnVN = itemView.findViewById(R.id.btnVN);
            radioGroupAnswer = itemView.findViewById(R.id.rgAns);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}