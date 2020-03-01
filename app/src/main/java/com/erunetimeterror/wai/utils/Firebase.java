package com.erunetimeterror.wai.utils;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.erunetimeterror.wai.activities.QuizActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Firebase {

    Map<String, Object> questionsDB;
    private FirebaseFirestore db;
    private DocumentReference docRef;

    public Firebase(FirebaseFirestore db, DocumentReference docRef)
    {
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("questions").document("eng");
    }

    public Map<String, Object>  loadNodesFromFirebase() {
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            questionsDB = documentSnapshot.getData();
                        } else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return questionsDB;
    }
}
