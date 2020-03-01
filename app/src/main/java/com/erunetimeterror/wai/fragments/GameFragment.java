package com.erunetimeterror.wai.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.erunetimeterror.wai.R;
public class GameFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.game_fragment, container, false);

        final TextView question = v.findViewById(R.id.question);

        final Button choice1 = v.findViewById(R.id.option1);
        final Button choice2 = v.findViewById(R.id.option2);
        final Button choice3 = v.findViewById(R.id.option3);
        final Button choice4 = v.findViewById(R.id.option4);
        question.setText("  FIRBASE QUESTION ");
        choice1.setText("  FIREBASE A  ");
        choice2.setText("  FIREBASE B ");
        choice3.setText("  FIREBASE C ");
        choice4.setText("  FIREBASE D ");
        final Animation frombot = AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        final Animation fromtop = AnimationUtils.loadAnimation(getActivity(),R.anim.fromtop);
        final Animation fadein = AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        final Animation fadeout = AnimationUtils.loadAnimation(getActivity(),R.anim.fadeout);

        Integer level = 0;


        final Boolean[] firstplay = {true};
        final Boolean[] levelup = {false};
        final int[] counter = {0};
        final  Boolean[] clicked = {false};

        View.OnClickListener AnswerListener = new View.OnClickListener() {

            @Override
            public void onClick(View c) {
                if(!clicked[0]){

                    clicked[0] = true;
                    counter[0]++;
                    int countint = counter[0];
                    Log.d("COUNTER", String.valueOf(countint));

                    Button b = (Button) c;
                    if (choice1 == b) {
                        choice2.startAnimation(fadeout);
                        choice3.startAnimation(fadeout);
                        choice4.startAnimation(fadeout);
                        choice2.setVisibility(choice2.GONE);
                        choice3.setVisibility(choice3.GONE);
                        choice4.setVisibility(choice4.GONE);
                    } else if (choice2 == b) {
                        choice1.startAnimation(fadeout);
                        choice3.startAnimation(fadeout);
                        choice4.startAnimation(fadeout);
                        choice1.setVisibility(choice1.GONE);
                        choice3.setVisibility(choice3.GONE);
                        choice4.setVisibility(choice4.GONE);

                    } else if (choice3 == b) {
                        choice1.startAnimation(fadeout);
                        choice2.startAnimation(fadeout);
                        choice4.startAnimation(fadeout);
                        choice1.setVisibility(choice1.GONE);
                        choice2.setVisibility(choice2.GONE);
                        choice4.setVisibility(choice4.GONE);


                    } else {
                        choice1.startAnimation(fadeout);
                        choice2.startAnimation(fadeout);
                        choice3.startAnimation(fadeout);
                        choice1.setVisibility(choice1.GONE);
                        choice2.setVisibility(choice2.GONE);
                        choice3.setVisibility(choice3.GONE);

                    }
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (counter[0]!=3) {
                                question.startAnimation(fadeout);
                                choice1.startAnimation(fadeout);
                                choice2.startAnimation(fadeout);
                                choice3.startAnimation(fadeout);
                                choice4.startAnimation(fadeout);
                            }
                            if (counter[0]==1){
                                question.setText("  FIRBASE QUESTION ");
                                choice1.setText("  FIREBASE A  ");
                                choice2.setText("  FIREBASE B ");
                                choice3.setText("  FIREBASE C ");
                                choice4.setText("  FIREBASE D ");
                            }else if (counter[0]==2){
                                question.setText("  FIRBASE QUESTION ");
                                choice1.setText("  FIREBASE A  ");
                                choice2.setText("  FIREBASE B ");
                                choice3.setText("  FIREBASE C ");
                                choice4.setText("  FIREBASE D ");
                            }else if(counter[0]==3) {
                                final Boolean[] firstplay = {true};
                                final Boolean[] levelup = {true};
                                //set move back to activity
                            }
                            if (counter[0]!=3) {
                                fadein.setStartOffset(0);
                                choice1.setVisibility(choice1.VISIBLE);
                                choice2.setVisibility(choice2.VISIBLE);
                                choice3.setVisibility(choice3.VISIBLE);
                                choice4.setVisibility(choice3.VISIBLE);
                                question.startAnimation(fadein);
                                choice1.startAnimation(fadein);
                                choice2.startAnimation(fadein);
                                choice3.startAnimation(fadein);
                                choice4.startAnimation(fadein);
                            }

                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clicked[0] = false;
                        }
                    }, 1000);
                }
            }
        };
        choice1.setOnClickListener(AnswerListener);
        choice2.setOnClickListener(AnswerListener);
        choice3.setOnClickListener(AnswerListener);
        choice4.setOnClickListener(AnswerListener);

        return v;
    }}

