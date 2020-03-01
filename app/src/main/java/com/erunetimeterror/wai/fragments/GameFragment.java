package com.erunetimeterror.wai.fragments;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.erunetimeterror.wai.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Quest {
    @SerializedName("Question")
    @Expose
    String Question;
    @SerializedName("1")
    @Expose
    String answer1;
    @SerializedName("2")
    @Expose
    String answer2;
    @SerializedName("3")
    @Expose
    String answer3;
    @SerializedName("4")
    @Expose
    String answer4;
}

public class GameFragment extends Fragment {

    Map<String, Object> questionsDB;
    //ArrayList<String> questions = new ArrayList<>();
    ArrayList<Quest> questions = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef = db.collection("questions").document("eng");
    ArrayList<String> keys;

    TextView question;
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;

    public void loadNodesFromFirebase() {
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            questionsDB = documentSnapshot.getData();
                            loadNodesIntoView();

                        } else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void loadNodesIntoView()
    {
        HashMap<String, Object> d = new HashMap<>();
        keys = new ArrayList<>();
        ArrayList<Quest> quests = new ArrayList<>();
        for (Map.Entry<String, Object> entry : questionsDB.entrySet()) {
            Quest quest;
            String json = entry.getValue().toString();
            String[] tmp = json.substring(1,json.length()-1).split(",");
            for(String tmp2 : tmp){
                try {
                    String[] tmp3 = (tmp2.split("="));
                    keys.add(tmp3[1]);
                }catch (IndexOutOfBoundsException e){

                }

            }

            d.put(entry.getKey(),entry.getValue());
//            quest.question = obj.getClass();
//                    questions.add(entry.getValue().toString());
        }
        question.setText(keys.get(4));
//        question.setText(questions.get(0));
       choice1.setText(keys.get(0));
        choice2.setText(keys.get(1));
        choice3.setText(keys.get(2));
        choice4.setText(keys.get(3));

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        question = view.findViewById(R.id.question);

        loadNodesFromFirebase();

       // Log.i("ABC", questions.get(0));
        //question.setText(questions.get(0));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.game_fragment, container, false);
        choice1  = v.findViewById(R.id.option1);
        choice2  = v.findViewById(R.id.option2);
        choice3  = v.findViewById(R.id.option3);
        choice4  = v.findViewById(R.id.option4);
        //question.setText("  FIRBASE QUESTION ");

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
                                question.setText(keys.get(9));
                                choice1.setText(keys.get(5));
                                choice2.setText(keys.get(6));
                                choice3.setText(keys.get(7));
                                choice4.setText(keys.get(8));
                            }else if (counter[0]==2){
                                question.setText(keys.get(14));
                                choice1.setText(keys.get(10));
                                choice2.setText(keys.get(11));
                                choice3.setText(keys.get(12));
                                choice4.setText(keys.get(13));
                            }else if(counter[0]==3) {
                                final Boolean[] firstplay = {true};
                                final Boolean[] levelup = {true};
                                getActivity().onBackPressed();
                                //set move back to activity
                            }
                            if (counter[0]!=3) {
                                fadein.setStartOffset(0);
                                choice1.setVisibility(choice1.VISIBLE);
                                choice2.setVisibility(choice2.VISIBLE);
                                choice3.setVisibility(choice3.VISIBLE);
                                choice4.setVisibility(choice3.VISIBLE);
                                //question.startAnimation(fadein);
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

