package com.erunetimeterror.wai.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erunetimeterror.wai.R;
import com.erunetimeterror.wai.utils.Answers;
import com.erunetimeterror.wai.utils.QuizAdapter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class QuizActivity extends AppCompatActivity implements QuizAdapter.ItemClickListener {
    public Answers getAnswers() {
        return answers;
    }

    Answers answers = new Answers();

    Integer NO_VIEWABLE_ELEMENTS = 4;

    QuizAdapter quizAdapter;
    ArrayList<String> questionText = new ArrayList<>();
    ArrayList<String> questionKey = new ArrayList<>();
    Map<String, Object> questionsDB;

    Button btnNextPage, btnVN, btnN, btnY, btnVY;
    LinearLayout linearLayout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef = db.collection("questions").document("mbti");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // data to populate the RecyclerView with
        loadNodesFromFirebase();

        btnNextPage = findViewById(R.id.btnNextPage);
        btnNextPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                quizAdapter.removeAll();
                if (loadNodesIntoView()) {
                    Log.i("SPEC", "Finished");
                    onBackPressed();
                }
                for (Map.Entry<String, Object> entry : questionsDB.entrySet()) {
                    Log.i("Lista", entry.getValue().toString());
                }
            }
        });

        /*
        btnVN = findViewById(R.id.btnVN);
        btnN = findViewById(R.id.btnN);
        btnY = findViewById(R.id.btnY);
        btnVY = findViewById(R.id.btnVY);
        */

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        quizAdapter = new QuizAdapter(this, questionKey, questionText, this);
        quizAdapter.setClickListener(this);
        recyclerView.setAdapter(quizAdapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + quizAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void loadNodesFromFirebase() {
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            questionsDB = documentSnapshot.getData();
                            if (questionsDB.size() > 0)
                                loadNodesIntoView();
                        } else {
                            Toast.makeText(QuizActivity.this, "Failed loading questions from database", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public boolean loadNodesIntoView()
    {
        boolean allNodesAreNull = true;
        int counter = 0;
        for (Map.Entry<String, Object> entry : questionsDB.entrySet()) {
            allNodesAreNull = true;
            if (!entry.getValue().toString().equals("NULL"))
            {
                allNodesAreNull = false;
                if (NO_VIEWABLE_ELEMENTS != counter) {
                    quizAdapter.addItem(entry.getKey(), entry.getValue().toString(), quizAdapter.getItemCount());
                    entry.setValue("NULL");
                } else
                    break;
                counter++;
            }
        }
        return allNodesAreNull;
    }

    public void saveAnswersToFirebase()
    {

    }
}
